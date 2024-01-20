package TEST.backend.controller;

import TEST.backend.domain.Article;
import TEST.backend.domain.Comment;
import TEST.backend.dto.AddArticleRequest;
import TEST.backend.dto.AddCommentRequest;
import TEST.backend.dto.ArticleResponse;
import TEST.backend.dto.UpdateArticleRequest;
import TEST.backend.service.CommentService;
import java.util.List;
import TEST.backend.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;
    private final CommentService commentService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }

    @PostMapping("/api/comment")
    public ResponseEntity<Comment> addComment(@RequestBody AddCommentRequest request) {
        Comment savedComment = commentService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedComment);
    }
}
