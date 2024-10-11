package rat2race.login.global.auth.service;

import static rat2race.login.global.common.exception.ErrorCode.NO_ACCESS;
import static rat2race.login.global.common.exception.ErrorCode.USER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rat2race.login.domain.user.entity.User;
import rat2race.login.domain.user.repository.UserRepository;
import rat2race.login.global.common.exception.AuthException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public User getUserOrThrow(String userKey) {
        return userRepository.findByUserKey(userKey)
                .orElseThrow(() -> new AuthException(USER_NOT_FOUND));
    }

    public void checkAccess(String userKey, User user) {
        if(!user.getUserKey().equals(userKey)) {
            throw new AuthException(NO_ACCESS);
        }
    }

}
