package online.nwen.server.entry.controller;

import online.nwen.server.bo.CreateArticleRequestBo;
import online.nwen.server.bo.CreateArticleResponseBo;
import online.nwen.server.bo.DeleteArticlesRequestBo;
import online.nwen.server.bo.DeleteArticlesResponseBo;
import online.nwen.server.service.api.IArticleService;
import online.nwen.server.service.api.ISecurityService;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/security/article/deleteAll")
    DeleteArticlesResponseBo deleteAll(@RequestBody DeleteArticlesRequestBo deleteArticlesRequestBo) {
        return this.articleService.deleteAll(this.securityService.getSecurityContextFromCurrentThread(), deleteArticlesRequestBo);
    }
}
