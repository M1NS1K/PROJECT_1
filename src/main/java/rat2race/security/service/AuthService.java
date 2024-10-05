package rat2race.security.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveRefreshToken(String refreshToken, String userId) {
        redisTemplate.opsForValue().set(refreshToken, userId, Duration.ofDays(3));
    }

    public void deleteRefreshToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }

    public void setAccessTokenBlackList(String accessToken) {
        redisTemplate.opsForValue().set(accessToken, "blacklist", Duration.ofHours(1));
    }

    public String getUserIdFromRefreshToken(String refreshToken) {
        return redisTemplate.opsForValue().get(refreshToken);
    }

    public ResponseCookie createHttpOnlyCookie(
            String refreshToken) {
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(1))
                .build();
        return responseCookie;
    }

}
