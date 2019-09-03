package online.nwen.server.entry.controller;

import online.nwen.server.bo.CreateArticleRequestBo;
import online.nwen.server.bo.CreateArticleResponseBo;
import online.nwen.server.bo.DeleteArticlesRequestBo;
import online.nwen.server.bo.DeleteArticlesResponseBo;
import online.nwen.server.service.api.IArticleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ArticleController {
    private IArticleService articleService;

    public ArticleController(IArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/security/article/create")
    CreateArticleResponseBo create(@RequestBody CreateArticleRequestBo createArticleRequestBo) {
        return this.articleService.create(createArticleRequestBo);
    }

    @DeleteMapping("/security/article/deleteAll")
    DeleteArticlesResponseBo deleteAll(@RequestBody DeleteArticlesRequestBo deleteArticlesRequestBo) {
        return this.articleService.deleteAll(deleteArticlesRequestBo);
    }
}
