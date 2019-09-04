package online.nwen.server.service.api;

import online.nwen.server.bo.*;
import online.nwen.server.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IArticleService {
    CreateArticleResponseBo create(CreateArticleRequestBo createArticleRequestBo);

    DeleteArticlesResponseBo deleteAll(DeleteArticlesRequestBo deleteArticlesRequestBo);

    Page<ArticleSummaryBo> getArticleSummariesOfAnthology(Long anthologyId, Pageable pageable);

    Page<ArticleSummaryBo> getArticleSummariesWithLabels(Set<String> labels, Pageable pageable);

    ArticleSummaryBo convertToSummary(Article article);

    ArticleDetailBo getArticleDetail(Long articleId);

    UpdateArticleResponseBo update(UpdateArticleRequestBo updateArticleRequestBo);
}
