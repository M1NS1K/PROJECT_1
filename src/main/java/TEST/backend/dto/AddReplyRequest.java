package TEST.backend.dto;

import TEST.backend.domain.entity.Reply;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddReplyRequest {

    @NotNull
    private String content;

    @NotNull
    private Long recommend;

    public Reply toEntity(Long id) {
        return Reply.builder()
                .memberId(id)
                .content(content)
                .recommend(recommend)
                .build();
    }
}
