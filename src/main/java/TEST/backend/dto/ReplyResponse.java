package TEST.backend.dto;

import TEST.backend.domain.Article;
import TEST.backend.domain.Reply;
import lombok.Getter;

@Getter
public class ReplyResponse {
    private Long memberId;
    private String content;
    private Long recommend;

    public ReplyResponse(Reply reply) {
        this.memberId = reply.getMemberId();
        this.content = reply.getContent();
        this.recommend = reply.getRecommend();
    }
}
