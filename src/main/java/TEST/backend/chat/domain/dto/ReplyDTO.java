package TEST.backend.chat.domain.dto;

import TEST.backend.chat.domain.entity.Reply;
import lombok.Data;

@Data
public class ReplyDTO {
    private Long userId;
    private String content;

    public Reply toEntity() {
        return Reply.builder()
                .userId(userId)
                .content(content)
                .build();
    }
}
