package TEST.backend.chat.domain.dto;

import TEST.backend.chat.domain.entity.Reply;
import lombok.Data;

@Data
public class ReplyDTO {
    private Long username;
    private String content;

    public Reply toEntity() {
        return Reply.builder()
                .username(username)
                .content(content)
                .build();
    }
}
