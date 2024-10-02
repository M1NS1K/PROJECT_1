package rat2race.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rat2race.security.entity.UserDetailsImpl;

@Repository
public interface UserRepository extends JpaRepository<UserDetailsImpl, Long> {
}
