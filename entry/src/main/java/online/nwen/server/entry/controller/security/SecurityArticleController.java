package online.nwen.server.entry.controller.security;

import online.nwen.server.bo.*;
import online.nwen.server.entry.controller.SecurityApiController;
import online.nwen.server.service.api.IArticleContentService;
import online.nwen.server.service.api.IArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@SecurityApiController
class SecurityArticleController {
    private IArticleService articleService;
    private IArticleContentService articleContentService;

    public SecurityArticleController(IArticleService articleService, IArticleContentService articleContentService) {
        this.articleService = articleService;
        this.articleContentService = articleContentService;
    }

    @PostMapping(path = "/article")
    CreateArticleResponseBo create(@RequestBody CreateArticleRequestBo createArticleRequestBo) {
        return this.articleService.create(createArticleRequestBo);
    }

    @PatchMapping(path = {"/article/{articleId}/{version}", "/security/article/{articleId}"})
    UpdateArticleResponseBo update(@PathVariable("articleId") Long articleId, @PathVariable(value = "version", required = false) Long version,
                                   @RequestBody UpdateArticleRequestBo updateArticleRequestBo) {
        updateArticleRequestBo.setArticleId(articleId);
        updateArticleRequestBo.setVersion(version);
        return this.articleService.update(updateArticleRequestBo);
    }

    @GetMapping(path = {"/article/{articleId}/content/histories"})
    Page<ArticleContentBo> getArticleContentHistories(@PathVariable("articleId") Long articleId, Pageable pageable) {
        return this.articleContentService.getArticleContentHistories(articleId, pageable);
    }

    @DeleteMapping(path = "/article/deleteAll")
    DeleteArticlesResponseBo deleteAll(@RequestBody DeleteArticlesRequestBo deleteArticlesRequestBo) {
        return this.articleService.deleteAll(deleteArticlesRequestBo);
    }
}
