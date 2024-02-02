package TEST.backend.dto;

import TEST.backend.domain.entity.Article;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ArticleResponse {

    @NotNull
    private final String title;

    @NotNull
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
