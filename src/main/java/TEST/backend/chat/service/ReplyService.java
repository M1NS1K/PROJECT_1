package TEST.backend.chat.service;

import TEST.backend.chat.domain.dto.ReplyDTO;
import TEST.backend.chat.domain.entity.Reply;
import TEST.backend.chat.repository.ReplyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final ReplyRepository repository;

    public Reply save(ReplyDTO request) {
        return repository.save(request.toEntity());
    }

    public List<Reply> findAll() {
        return repository.findAll();
    }

    public void deleteByUserId(Long id) {
        repository.deleteById(id);
    }
}
