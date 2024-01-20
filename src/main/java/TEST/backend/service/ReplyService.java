package TEST.backend.service;

import TEST.backend.domain.Article;
import TEST.backend.domain.Reply;
import TEST.backend.dto.AddReplyRequest;
import TEST.backend.dto.UpdateReplyRequest;
import TEST.backend.repository.ReplyRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository repository;
    private Article article;

    //Create
    @Transactional
    public Reply save(Long id, AddReplyRequest request) {
        return repository.save(request.toEntity(id));
    }

    //Read
    @Transactional(readOnly = true)
    public List<Reply> findAll() {
        return repository.findAll();
    }

    //Read
    @Transactional(readOnly = true)
    public Reply findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    //Delete
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    //Update
    @Transactional
    public Reply update(Long id, UpdateReplyRequest request) {
        Reply reply = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        return reply;
    }

}
