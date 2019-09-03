package online.nwen.server.entry.controller;

import online.nwen.server.bo.CreateArticleRequestBo;
import online.nwen.server.bo.CreateArticleResponseBo;
import online.nwen.server.service.api.IArticleService;
import online.nwen.server.service.api.ISecurityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ArticleController {
    private IArticleService articleService;
    private ISecurityService securityService;

    public ArticleController(IArticleService articleService, ISecurityService securityService) {
        this.articleService = articleService;
        this.securityService = securityService;
    }

    @PostMapping("/security/article/create")
    CreateArticleResponseBo create(@RequestBody CreateArticleRequestBo createArticleRequestBo) {
        return this.articleService.create(this.securityService.getSecurityContextFromCurrentThread(), createArticleRequestBo);
    }
}
