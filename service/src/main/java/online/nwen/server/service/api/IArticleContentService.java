package online.nwen.server.service.api;

import online.nwen.server.bo.ArticleContentBo;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticleContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IArticleContentService {
    ArticleContent save(Article article, Long version, String content);

    ArticleContentBo getLastVersion(Article article);

    Page<ArticleContentBo> getArticleContentHistories(Long articleId, Pageable pageable);
}
