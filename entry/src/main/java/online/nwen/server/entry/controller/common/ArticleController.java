package online.nwen.server.entry.controller.common;

import online.nwen.server.bo.ArticleDetailBo;
import online.nwen.server.bo.ArticleSummaryBo;
import online.nwen.server.entry.controller.CommonApi;
import online.nwen.server.service.api.IArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@CommonApi
class ArticleController {
    private IArticleService articleService;

    public ArticleController(IArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(path = "/article/summaries/anthology/{anthologyId}")
    Page<ArticleSummaryBo> getArticleSummariesOfAnthology(@PathVariable("anthologyId") Long anthologyId, Pageable pageable) {
        return this.articleService.getArticleSummariesOfAnthology(anthologyId, pageable);
    }

    @GetMapping(path = "/article/summaries/labels/{labels}")
    Page<ArticleSummaryBo> getArticleSummariesWithLabels(@PathVariable("labels") Set<String> labels, Pageable pageable) {
        return this.articleService.getArticleSummariesWithLabels(labels, pageable);
    }

    @GetMapping(path = "/article/summaries/category/{categoryId}")
    Page<ArticleSummaryBo> getArticleSummariesWithCategory(@PathVariable("categoryId") Long categoryId, Pageable pageable) {
        return this.articleService.getArticleSummariesWithCategory(categoryId, pageable);
    }

    @GetMapping(path = "/article/detail/{articleId}")
    ArticleDetailBo getArticleDetail(@PathVariable("articleId") Long articleId) {
        return this.articleService.getArticleDetail(articleId);
    }
}
