package TEST.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArticleResponse {
    private final String title;
    private final String content;
}
