package TEST.backend.service;

import TEST.backend.domain.dto.ArticleRequest;
import TEST.backend.domain.entity.Article;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import TEST.backend.repository.BlogRepository;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public Article save(Article article) {
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

    @Transactional
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Article article) {
        Article findArticle = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        findArticle.update(article);
    }
}
