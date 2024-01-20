package TEST.backend.service;

import TEST.backend.domain.Comment;
import TEST.backend.dto.AddCommentRequest;
import TEST.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment save(AddCommentRequest request) {
        return commentRepository.save(request.toEntity());
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
