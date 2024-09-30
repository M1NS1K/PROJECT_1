package rat2race.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import rat2race.security.entity.Token;
import rat2race.security.repository.TokenRepository;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void saveOrUpdateToken(String refreshToken, String accessToken) {
        Token token = tokenRepository.findByAccessToken(accessToken);

        if(refreshToken == null) {
            throw new IllegalArgumentException("잘못된 REFRESH TOKEN 입니다.");
        }

        if(token == null) {
            Token newToken = Token.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            tokenRepository.save(newToken);
        } else {
            token.updateRefreshToken(refreshToken);
        }
    }

    public boolean validRefreshToken(String accessToken, String refreshToken) {
        String storedRefreshToken = tokenRepository.findByAccessToken(accessToken).getRefreshToken();

        if(!storedRefreshToken.equals(refreshToken)) {
            return false;
        }

        return true;
    }

    public Token findByAccessToken(String accessToken) {
        return tokenRepository.findByAccessToken(accessToken);
    }
}
