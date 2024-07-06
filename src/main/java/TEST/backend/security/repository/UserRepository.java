package TEST.backend.security.repository;

import TEST.backend.security.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    Optional<User> findByLoginId(String loginId);
    User findByEmail(String email);

		Integer countByUsername(String username);
		User findByUsername(String username);
}
