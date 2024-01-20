package TEST.backend.dto;

import TEST.backend.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequest {
    private Long memberId;
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .memberId(memberId)
                .content(content)
                .build();
    }
}
