package rat2race.security.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 14440)
public class Token {

    @Id
    private Long user_id;
    private String refreshToken;

    public Token(Long user_id, String refreshToken) {
        this.user_id = user_id;
        this.refreshToken = refreshToken;
    }
}
