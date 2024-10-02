package rat2race.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rat2race.security.entity.UserDetailsImpl;
import rat2race.security.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDetailsImpl findByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT FOUND USER"));
    }
}
