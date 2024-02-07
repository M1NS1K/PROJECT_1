package TEST.backend.chat.service;

import TEST.backend.chat.domain.dto.ReplyDTO;
import TEST.backend.chat.domain.entity.Reply;
import TEST.backend.chat.repository.ReplyRepository;
import jakarta.validation.Valid;
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
        Reply entity = request.toEntity();
        if(entity == null) throw new IllegalArgumentException("not find");
        return entity;
    }

    public List<Reply> findAll() {
        return repository.findAll();
    }

    public Reply findById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found" + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
