package TEST.backend.article.controller;

import TEST.backend.article.domain.entity.Article;
import TEST.backend.article.repository.BlogRepository;
import TEST.backend.article.service.BlogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping()
    public String getArticles(Model model) {
        List<Article> articles = blogService.findAll();
        model.addAttribute("articles", articles);
        return "articleList";
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", article);
        return "article";
    }

    @GetMapping("/add")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new Article()); // 새 게시글 작성을 위한 빈 Article 객체 추가
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", article); // 기존 게시글 편집을 위한 Article 객체 추가
        }
        return "addArticle";
    }
}

