package online.nwen.server.entry.controller.security;

import online.nwen.server.bo.CreateArticlePraiseResponseBo;
import online.nwen.server.entry.controller.SecurityApi;
import online.nwen.server.service.api.IArticlePraiseService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@SecurityApi
class SecurityArticlePraiseController {
    private IArticlePraiseService articlePraiseService;

    public SecurityArticlePraiseController(IArticlePraiseService articlePraiseService) {
        this.articlePraiseService = articlePraiseService;
    }

    @PatchMapping(path = "/praise/{articleId}")
    CreateArticlePraiseResponseBo bookmark(@PathVariable("articleId") Long articleId) {
        return this.articlePraiseService.praise(articleId);
    }
}
