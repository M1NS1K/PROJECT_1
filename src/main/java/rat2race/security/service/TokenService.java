package rat2race.security.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rat2race.security.entity.RefreshToken;
import rat2race.security.repository.TokenRepository;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    /**
     * RT 있으면 update 아니면 save
     * @param userId
     * @param newRefreshToken
     */
    public void saveOrUpdateRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken token = findByUserId(userId);

        if(token == null) {
            tokenRepository.save(new RefreshToken(userId, newRefreshToken));
        } else {
            token.updateRefreshToken(newRefreshToken);
        }
    }

    /**
     * UserId에 맞는 RT가 없을 때 null return
     * @param userId
     * @return
     */
    public RefreshToken findByUserId(Long userId) {
        Optional<RefreshToken> refreshToken = tokenRepository.findById(userId);

        if(refreshToken != null) {
            return refreshToken.get();
        } else {
            return null;
        }
    }

}
