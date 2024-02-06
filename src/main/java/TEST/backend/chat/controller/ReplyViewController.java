package TEST.backend.chat.controller;

import TEST.backend.chat.domain.entity.Reply;
import TEST.backend.chat.service.ReplyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/articles/reply")
public class ReplyViewController {

    private final ReplyService service;

    @GetMapping("/{id}")
    public String getReplies(@PathVariable Long id, Model model) {
        List<Reply> ReplyList = service.findAll();
        model.addAttribute("ReplyList", ReplyList);
        return "article";
    }

}
