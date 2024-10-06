package rat2race.login.global.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rat2race.login.domain.user.entity.RefreshToken;

@Repository
public interface TokenRepository extends CrudRepository<RefreshToken, Long> {
}
