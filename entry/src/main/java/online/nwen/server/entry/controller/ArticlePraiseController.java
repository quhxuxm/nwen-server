package online.nwen.server.entry.controller;

import online.nwen.server.bo.CreateArticlePraiseResponseBo;
import online.nwen.server.service.api.IArticlePraiseService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ArticlePraiseController {
    private IArticlePraiseService articlePraiseService;

    public ArticlePraiseController(IArticlePraiseService articlePraiseService) {
        this.articlePraiseService = articlePraiseService;
    }

    @PatchMapping("/security/praise/{articleId}")
    CreateArticlePraiseResponseBo bookmark(@PathVariable("articleId") Long articleId) {
        return this.articlePraiseService.praise(articleId);
    }
}
