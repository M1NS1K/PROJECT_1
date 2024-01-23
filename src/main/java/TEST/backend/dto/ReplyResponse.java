package TEST.backend.dto;

import TEST.backend.domain.Article;
import TEST.backend.domain.Reply;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReplyResponse {

    @NotNull
    private Long memberId;

    @NotNull
    private String content;

    @NotNull
    private Long recommend;

    public ReplyResponse(Reply reply) {
        this.memberId = reply.getMemberId();
        this.content = reply.getContent();
        this.recommend = reply.getRecommend();
    }
}
