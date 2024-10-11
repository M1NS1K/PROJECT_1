package rat2race.login.global.auth.jwt.service;

import static rat2race.login.global.common.exception.ErrorCode.INVALID_TOKEN;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.util.StringUtils;
import rat2race.login.domain.user.entity.Role;
import rat2race.login.global.auth.dto.model.CustomOAuth2User;
import rat2race.login.global.common.exception.CustomException;
import rat2race.login.global.common.exception.TokenException;


@Component
@RequiredArgsConstructor
public class TokenProvider {

    private SecretKey secretKey;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String USER_ID = "userId";
    private static final String USER_ROLE = "role";
    private final TokenService tokenService;

    @PostConstruct
    public void setSecretKey(@Value("${spring.jwt.secret}") String secretKey) {
        this.secretKey = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8), SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String generateAccessToken(Long userId, Role userRole) {
        return generateJwt(userId, userRole, ACCESS_TOKEN, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generateRefreshToken(Long userId, Role userRole) {
        return generateJwt(userId, userRole, REFRESH_TOKEN, REFRESH_TOKEN_EXPIRE_TIME);
    }

    /**
     * token이 만료되면 false return 만료되지 않으면 true return
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        if(StringUtils.hasText(token)) {
            Claims claims = parseClaims(token);
            return claims.getExpiration().after(new Date());
        }

        throw new TokenException(INVALID_TOKEN);
    }

    public String reissueAccessToken(String token) {

        Long userId = getUserIdByToken(token);
        Role userRole = getUserRoleByToken(token);
        String refreshToken = tokenService.findRefreshTokenByUserId(userId);

        if(userId == null || userRole == null) {
            throw new TokenException(INVALID_TOKEN);
        }

        if (refreshToken != null) {
            String reissueAccessToken = generateAccessToken(userId, userRole);

            /**
             * RT가 만료되면 update refreshToken
             */
            if (!validateToken(refreshToken)) {
                String newRefreshToken = generateRefreshToken(userId, userRole);
                tokenService.saveOrUpdateRefreshToken(userId, newRefreshToken);
            }

            return reissueAccessToken;
        }

        return null;
    }

    private String generateJwt(Long userId, Role userRole, String tokenType, Long expiredTime) {

        Claims claims = Jwts.claims()
                .subject(tokenType)
                .add(USER_ID, userId)
                .add(USER_ROLE, userRole.getKey())
                .build();

        Date currentTime = new Date();

        Date expiredDate = new Date(currentTime.getTime() + expiredTime);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(currentTime)
                .expiration(expiredDate)
                .signWith(secretKey)
                .compact();
    }

    public Claims parseClaims(String signedToken) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(signedToken)
                    .getPayload();
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
                 | IllegalStateException e) {
            throw new TokenException(INVALID_TOKEN);
        }
    }

    public Authentication getAuthentication(String token) {
        Long userId = getUserIdByToken(token);
        Role userRole = getUserRoleByToken(token);

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(userRole.getKey()));

        return new UsernamePasswordAuthenticationToken(userId, null, authorities);
    }

    private Long getUserIdByToken(String token) {
        return parseClaims(token).get(USER_ID, Long.class);
    }

    private Role getUserRoleByToken(String token) {
        return parseClaims(token).get(USER_ROLE, Role.class);
    }

}
