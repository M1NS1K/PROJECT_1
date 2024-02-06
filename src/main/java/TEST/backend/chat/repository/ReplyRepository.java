package TEST.backend.chat.repository;

import TEST.backend.chat.domain.entity.Reply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyRepository {

    @PersistenceContext
    EntityManager em;

    public Long save(Reply reply) {
        em.persist(reply);
        return reply.getId();
    }
}
