package TEST.backend.article.controller;

import TEST.backend.article.domain.dto.ArticleDTO;
import TEST.backend.article.domain.entity.Article;
import TEST.backend.article.service.BlogService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class BlogApiController {

    private final BlogService blogService;

    //request form make
    @PostMapping("/add")
    public ResponseEntity<Article> newArticle(@Valid @RequestBody ArticleDTO request) {
        Article article = blogService.save(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

    //request give article List
    @GetMapping()
    public ResponseEntity<List<Article>> findAllArticles() {
        List<Article> articles = blogService.findAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> findArticle(@PathVariable Long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDTO request) {
        Article updatedArticle = blogService.update(id, request.toEntity());

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
