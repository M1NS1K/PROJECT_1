package rat2race.login.global.auth.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.util.StringUtils;
import rat2race.login.domain.user.entity.UserDetailsImpl;
import rat2race.login.domain.user.entity.UserPrincipal;
import rat2race.login.global.auth.service.UserService;
import rat2race.login.global.common.exception.CustomException;


@Component
@RequiredArgsConstructor
public class TokenProvider {

    private SecretKey secretKey;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String USER_ID = "userId";
    private final TokenService tokenService;
    private final UserService userService;

    @PostConstruct
    public void setSecretKey(@Value("${spring.jwt.secret}") String secretKey) {
        this.secretKey = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8), SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String generateAccessToken(Long userId) {
        return generateJwt(userId, ACCESS_TOKEN,  ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generateRefreshToken(Long userId) {
        return generateJwt(userId, REFRESH_TOKEN, REFRESH_TOKEN_EXPIRE_TIME);
    }

    /**
     * token이 만료되면 false return
     * 만료되지 않으면 true return
     * @param token
     * @return
     */
	public boolean validateToken(String token) {
		if (!StringUtils.hasText(token)) {
			return false;
		}

		Claims claims = parseClaims(token);
		return claims.getExpiration().after(new Date());
	}

	public String reissueAccessToken(String token) {

        Long userId = getUserIdByToken(token);
        String refreshToken = parseClaims(token).get(REFRESH_TOKEN, String.class);

        if(refreshToken != null) {
            String reissueAccessToken = generateAccessToken(userId);

            /**
             * RT가 만료되면 update refreshToken
             */
            if(!validateToken(refreshToken)) {
                String newRefreshToken = generateRefreshToken(userId);
                tokenService.saveOrUpdateRefreshToken(userId, newRefreshToken);
            }

            return reissueAccessToken;
        }

        return null;
	}

    private String generateJwt(Long userId, String tokenType, Long expiredTime) {

        Claims claims = Jwts.claims()
                .subject(tokenType)
                .add(USER_ID, userId)
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
            throw new CustomException(HttpStatus.BAD_REQUEST, "Token Decode 과정에서 에러가 생겼습니다.");
        }
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Long userId = getUserIdByToken(token);
        UserDetailsImpl userDetails = userService.findByUserId(userId);
        UserPrincipal userPrincipal = new UserPrincipal(userDetails);
        return new UsernamePasswordAuthenticationToken(userPrincipal, token, userDetails.getAuthorities());
    }


    private Long getUserIdByToken(String token) {
        return parseClaims(token).get(USER_ID, Long.class);
    }

}
