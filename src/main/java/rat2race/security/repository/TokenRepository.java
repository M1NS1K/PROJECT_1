package rat2race.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rat2race.security.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    @Query("select t from Token t where t.accessToken = :at")
    Token findByAccessToken(@Param("at") String accessToken);


}
