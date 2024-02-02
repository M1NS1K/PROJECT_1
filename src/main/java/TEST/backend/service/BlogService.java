package TEST.backend.service;

import TEST.backend.domain.entity.Article;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import TEST.backend.repository.BlogRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(Article article) {
        log.info("Saving article: {}", article);
        return blogRepository.save(article);
    }

    @Transactional(readOnly = true)
    public List<Article> findAll() {
        List<Article> articles = blogRepository.findAll();
        return articles;
    }

    @Transactional(readOnly = true)
    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    public void update(Long id, Article article) {
        Article findArticle = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        findArticle.update(article);
        blogRepository.save(findArticle);
    }
}
