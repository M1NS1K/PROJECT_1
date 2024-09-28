package rat2race.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.util.StringUtils;
import rat2race.security.entity.Token;
import rat2race.security.service.TokenService;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private SecretKey secretKey;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;
    private static final String KEY_ROLE = "role";
    private final TokenService tokenService;

    @PostConstruct
    public void setSecretKey(@Value("${spring.jwt.secret}") String secretKey) {
        this.secretKey = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8), SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String generateAccessToken(Authentication authentication) {
        return generateJwt(authentication, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public void generateRefreshToken(Authentication authentication, String accessToken) {
        String refreshToken = generateJwt(authentication, REFRESH_TOKEN_EXPIRE_TIME);
        tokenService.saveOrUpdateToken(refreshToken, accessToken);

    }

    /**
     * 사용자 인증 정보 추출
     * @param signedToken
     * @return
     */
    public Authentication getAuthentication(String signedToken) {
		Claims claims = parseClaims(signedToken);
		List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

		User principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, signedToken, authorities);
	}

    /**
     * 토큰 만료 확인
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

    /**
     * 토큰 재발급
     * @param accessToken
     * @return
     */
	public String reissueAccessToken(String accessToken) {
        if(StringUtils.hasText(accessToken)) {
            Token token = tokenService.findByAccessToken(accessToken);
            String refreshToken = token.getRefreshToken();

            if(validateToken(refreshToken)) {
                String reissueAccessToken = generateAccessToken(getAuthentication(refreshToken));
                tokenService.updateToken(reissueAccessToken, token);
                return reissueAccessToken;
            }
        }

        return null;
	}

    /**
     * 토큰 생성
     * @param authentication
     * @param expiredTime
     * @return
     */
    private String generateJwt(Authentication authentication, Long expiredTime) {

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expiredTime);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        return Jwts.builder()
                .subject(authentication.getName())
                .claim(KEY_ROLE, authorities)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(secretKey)
                .compact();
    }


    /**
     * Claim 파서
     * @param signedToken
     * @return
     */
    private Claims parseClaims(String signedToken) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(signedToken)
                    .getPayload();
        } catch (ExpiredJwtException e) {
			return e.getClaims();
		} catch (MalformedJwtException e) {
			throw new IllegalArgumentException("invalid token");
		} catch (SecurityException e) {
			throw new IllegalArgumentException("invalid jwt signature");
		}
    }

    /**
     * 클레임에서 권한 GET
     * @param claims
     * @return
     */
	private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
		return Collections.singletonList(new SimpleGrantedAuthority(
				claims.get(KEY_ROLE).toString()));
	}

}
