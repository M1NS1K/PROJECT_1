package TEST.backend.controller;

import TEST.backend.domain.dto.ArticleRequest;
import TEST.backend.domain.entity.Article;
import jakarta.validation.Valid;
import java.util.List;
import TEST.backend.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogApiController {

    private final BlogService blogService;

    //request form make
    @PostMapping("/new-form")
    public ResponseEntity<Article> newArticle(@Valid @RequestBody ArticleRequest request) {
        Article article = blogService.save(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

    //request give article List
    @GetMapping("/articles")
    public ResponseEntity<List<Article>> findAllArticles() {
        List<Article> articles = blogService.findAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(articles);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> findArticle(@PathVariable Long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleRequest request) {
        Article updatedArticle = blogService.update(id, request.toEntity());

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
