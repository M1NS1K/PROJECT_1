package TEST.backend.service;

import TEST.backend.domain.entity.Article;
import TEST.backend.dto.AddArticleRequest;
import TEST.backend.dto.UpdateArticleRequest;
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
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return blogRepository.findAll();
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
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        return article;
    }
}
