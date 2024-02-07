package TEST.backend.article.controller;

import TEST.backend.article.domain.entity.Article;
import TEST.backend.article.service.BlogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String newArticle(Model model) {
        model.addAttribute("article", new Article());
        return "addArticle";
    }
}

