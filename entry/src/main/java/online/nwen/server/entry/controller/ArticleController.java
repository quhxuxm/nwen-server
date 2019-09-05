package online.nwen.server.entry.controller;

import online.nwen.server.bo.*;
import online.nwen.server.service.api.IArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PostMapping(value = {"/security/article/update/{articleId}/{version}", "/security/article/update/{articleId}" })
    UpdateArticleResponseBo update(@PathVariable("articleId") Long articleId, @PathVariable(value = "version", required = false) Long version,
                                   @RequestBody UpdateArticleRequestBo updateArticleRequestBo) {
        updateArticleRequestBo.setArticleId(articleId);
        updateArticleRequestBo.setVersion(version);
        return this.articleService.update(updateArticleRequestBo);
    }

    @GetMapping(value = {"/security/article/{articleId}/content/histories" })
    Page<ArticleContentBo> getArticleContentHistories(@PathVariable("articleId") Long articleId, Pageable pageable) {
        return this.articleService.getArticleContentHistories(articleId, pageable);
    }

    @DeleteMapping("/security/article/deleteAll")
    DeleteArticlesResponseBo deleteAll(@RequestBody DeleteArticlesRequestBo deleteArticlesRequestBo) {
        return this.articleService.deleteAll(deleteArticlesRequestBo);
    }

    @GetMapping("/article/summaries/anthology/{anthologyId}")
    Page<ArticleSummaryBo> getArticleSummariesOfAnthology(@PathVariable("anthologyId") Long anthologyId, Pageable pageable) {
        return this.articleService.getArticleSummariesOfAnthology(anthologyId, pageable);
    }

    @GetMapping("/article/detail/{articleId}")
    ArticleDetailBo getArticleDetail(@PathVariable("articleId") Long articleId) {
        return this.articleService.getArticleDetail(articleId);
    }
}
