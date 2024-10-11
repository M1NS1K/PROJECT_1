package rat2race.login.global.auth.service;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rat2race.login.domain.user.entity.User;
import rat2race.login.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public User getUserOrThrow(String userKey) {
        return userRepository.findByUserKey(userKey)
                .orElseThrow(() -> new AuthException())
    }

}
