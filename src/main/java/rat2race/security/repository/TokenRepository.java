package rat2race.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rat2race.security.entity.RefreshToken;

@Repository
public interface TokenRepository extends CrudRepository<RefreshToken, Long> {
}
