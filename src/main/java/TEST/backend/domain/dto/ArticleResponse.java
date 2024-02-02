package TEST.backend.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleResponse {

    @NotNull
    private final String title;

    @NotNull
    private final String content;

}
