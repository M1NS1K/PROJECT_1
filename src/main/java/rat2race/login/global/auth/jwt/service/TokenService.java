package rat2race.login.global.auth.jwt.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<Long, String> redisTemplate;

    /**
     * RT save 3day
     *
     * @param userId
     * @param newRefreshToken
     */
    public void saveOrUpdateRefreshToken(Long userId, String newRefreshToken) {
        redisTemplate.opsForValue().set(userId, newRefreshToken, Duration.ofDays(3));
    }

    /**
     * @param userId
     * @return
     */
    public String findRefreshTokenByUserId(Long userId) {
        return redisTemplate.opsForValue().get(userId);
    }

    /**
     * delete refreshToken
     *
     * @param userId
     */
    private void deleteRefreshToken(Long userId) {
        redisTemplate.delete(userId);
    }

    public void setUserBlackList(Long userId) {
        deleteRefreshToken(userId);
        redisTemplate.opsForValue().set(userId, "blacklist", Duration.ofHours(1));
    }

    public ResponseCookie createHttpOnlyCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(1))
                .build();
    }

}
