package rat2race.login.global.auth.jwt.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import rat2race.login.domain.user.entity.RefreshToken;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<Long, String> redisTemplate;



    /**
     * RT save 3day
     * @param userId
     * @param newRefreshToken
     */
    public void saveOrUpdateRefreshToken(Long userId, String newRefreshToken) {
        redisTemplate.opsForValue().set(userId, newRefreshToken, Duration.ofDays(3));
    }

    /**
     *
     * @param userId
     * @return
     */
    public RefreshToken findByUserId(Long userId) {
        redisTemplate.opsForValue().get(userId);
    }

    /**
     * delete refreshToken
     * @param userId
     */
    public void deleteRefreshToken(Long userId) {
        redisTemplate.delete(String.valueOf(userId));
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
