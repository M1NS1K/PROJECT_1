package TEST.backend.article.repository;

import TEST.backend.article.domain.entity.Article;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BlogRepository {

    private final EntityManager em;

    public Long save(Article article) {
        em.persist(article);
        return article.getId();
    }

    public void delete(Article article) {
        em.remove(article);
    }

    public Article findById(Long id) {
        Article article = em.find(Article.class, id);
        if(article == null) {
            new IllegalArgumentException("값이 없다");
        }
        return article;
    }

    public List<Article> findAll() {
        List<Article> selectAFromArticleA = em.createQuery("select a from Article a").getResultList();
        return selectAFromArticleA;
    }
}

