package TEST.backend.article.utils;

import TEST.backend.article.domain.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenUtils {

    private static final String jwtSecretKey = "thisIsASecretKeyUsedForJwtTokenGenerationAndItIsLongEnoughToMeetTheRequirementOf256Bits";
    private static final Key key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    private static final String JWT_TYPE = "JWT";
    private static final String ALGORITHM = "HS256";
    private static final String LOGIN_ID = "loginId";
    private static final String USERNAME = "username";

    public static String generateJwtToken(UserDto userDto) {

        /**
         * 헤더(createHeader 메서드): JWT의 타입과 사용된 알고리즘을 정의한다. 여기서는 "JWT" 타입과 "HS256" 알고리즘이 사용된다.
         * 클레임(createClaims 메서드): 사용자의 정보를 담는다. 이 경우, 사용자의 loginId와 username이 저장된다.
         * 서명: 생성된 토큰의 무결성을 보장하기 위해 HMAC-SHA256 알고리즘을 사용한 서명이 추가된다.
         * 만료시간(createExpiredDate 메서드): 토큰의 만료시간을 설정한다. 이 코드에서는 8시간 후로 설정되어 있다.
         */

        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader()) //header
                .setClaims(createClaims(userDto)) //claim
                .setSubject(String.valueOf(userDto.loginId())) //loginId
                .setIssuer("profile")
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(createExpiredDate());

        return builder.compact();
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);

            log.info("expireTime : " + claims.getExpiration());
            log.info("loginId : " + claims.get(LOGIN_ID));
            log.info("username : " + claims.get(USERNAME));

            return true;
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("Token Expired", expiredJwtException);
            return false;
        } catch (JwtException jwtException) {
            log.error("Token Tampered", jwtException);
            return false;
        } catch (NullPointerException npe) {
            log.error("Token is null", npe);
            return false;
        }
    }

    private static Date createExpiredDate() {
        // 토큰의 만료기간은 8시간으로 지정
        Instant now = Instant.now();
        Instant expiryDate = now.plus(Duration.ofHours(8));
        return Date.from(expiryDate);
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", JWT_TYPE);
        header.put("alg", ALGORITHM);
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private static Map<String, Object> createClaims(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>();

        log.info("loginId : " + userDto.loginId());
        log.info("username : " + userDto.username());

        claims.put(LOGIN_ID, userDto.loginId());
        claims.put(USERNAME, userDto.username());
        return claims;
    }

    private static Claims getClaimsFormToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token).getBody();
    }

    public static String getUserIdFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return claims.get(LOGIN_ID).toString();
    }

}
