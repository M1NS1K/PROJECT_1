package TEST.backend.controller;

import TEST.backend.domain.Article;
import TEST.backend.domain.Reply;
import TEST.backend.dto.AddArticleRequest;
import TEST.backend.dto.AddReplyRequest;
import TEST.backend.dto.ArticleResponse;
import TEST.backend.dto.ReplyResponse;
import TEST.backend.dto.UpdateArticleRequest;
import TEST.backend.service.ReplyService;
import jakarta.validation.Valid;
import java.util.List;
import TEST.backend.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BlogApiController {

    private final BlogService blogService;
    private final ReplyService replyService;

    @PostMapping("/articles")
    public ResponseEntity<Article> addArticle(@Valid @RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @Valid @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }

    @PostMapping("/articles/{id}/reply")
    public ResponseEntity<Reply> addReply(@PathVariable Long id, @Valid @RequestBody AddReplyRequest request) {
        Reply savedReply = replyService.save(id, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedReply);
    }

    @GetMapping("/articles/{id}/reply/{number}")
    public ResponseEntity<ReplyResponse> findReply(@PathVariable Long id, @PathVariable Long number) {
        Reply reply = replyService.findById(number);
        return ResponseEntity.ok()
                .body(new ReplyResponse(reply));
    }


}
