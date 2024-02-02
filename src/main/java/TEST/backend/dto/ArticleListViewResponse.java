package TEST.backend.dto;

import TEST.backend.domain.entity.Article;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ArticleListViewResponse {

    @NotNull
    private final Long id;

    @NotNull
    private final String title;

    @NotNull
    private final String content;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
