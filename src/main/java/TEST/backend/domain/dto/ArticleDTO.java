package TEST.backend.domain.dto;

import TEST.backend.domain.entity.Article;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleDTO {
    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}
