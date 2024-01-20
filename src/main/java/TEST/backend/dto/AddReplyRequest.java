package TEST.backend.dto;

import TEST.backend.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddReplyRequest {
    private String content;
    private Long recommend;

    public Reply toEntity(Long id) {
        return Reply.builder()
                .memberId(id)
                .content(content)
                .recommend(recommend)
                .build();
    }
}
