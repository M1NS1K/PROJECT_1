package TEST.backend.controller;

import TEST.backend.domain.dto.ArticleRequest;
import TEST.backend.domain.entity.Article;
import TEST.backend.domain.entity.Reply;
import TEST.backend.dto.AddReplyRequest;
import TEST.backend.domain.dto.ArticleResponse;
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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogApiController {

    private final BlogService blogService;
    private final ReplyService replyService;

    //request form make
    @PostMapping("/new-form")
    public ResponseEntity<ArticleResponse> newArticle(@Valid @RequestBody ArticleRequest request) {
        blogService.save(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body();
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

        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleRequest request) {
        Article updatedArticle = blogService.update(request);

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
