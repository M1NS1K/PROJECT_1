package TEST.backend.chat.controller;

import TEST.backend.chat.domain.dto.ReplyDTO;
import TEST.backend.chat.domain.entity.Reply;
import TEST.backend.chat.repository.ReplyRepository;
import TEST.backend.chat.service.ReplyService;
import java.lang.module.ModuleDescriptor.Requires;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyApiController {

    private final ReplyService service;

    @PostMapping("/articles/{id}")
    public ResponseEntity<Reply> addReply(@PathVariable Long id, ReplyDTO request) {
        Reply reply = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reply);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<List<Reply>> findAllReply(@PathVariable Long id) {
        List<Reply> replyList = service.findAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(replyList);
    }

    @DeleteMapping("/articles/{id}/{userId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long id, @PathVariable Long userId) {
        service.delete(userId);
        return ResponseEntity.ok().build();
    }

}
