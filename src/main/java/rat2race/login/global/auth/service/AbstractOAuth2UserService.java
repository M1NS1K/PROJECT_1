package rat2race.login.global.auth.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rat2race.login.domain.user.repository.UserRepository;

@Service
@Getter
@RequiredArgsConstructor
public class AbstractOAuth2UserService {
    private UserService userService;
    private UserRepository userRepository;
    
}
