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
