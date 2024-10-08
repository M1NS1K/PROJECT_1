package rat2race.login.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rat2race.login.domain.user.entity.UserDetailsImpl;

@Repository
public interface UserRepository extends JpaRepository<, Long> {
}
