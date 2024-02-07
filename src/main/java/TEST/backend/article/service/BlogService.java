package TEST.backend.article.service;

import TEST.backend.article.domain.entity.Article;
import TEST.backend.article.repository.BlogRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public Long save(Article article) {
        log.info("Saving article: {}", article);
        return blogRepository.save(article);
    }

    public List<Article> findAll() {
        List<Article> articles = blogRepository.findAll();
        return articles;
    }

    public Article findById(Long id) {
        return blogRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        Article article = blogRepository.findById(id);
        blogRepository.delete(article);
    }

    @Transactional
    public Long update(Long id, Article article) {
        Article savedArticle = blogRepository.findById(id);
        savedArticle.update(article);
        return blogRepository.save(savedArticle);
    }
}
