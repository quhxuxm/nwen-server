package online.nwen.server.dao.api;

import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticleContent;
import online.nwen.server.domain.ArticleContentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IArticleContentDao {
    ArticleContent save(ArticleContent content);

    ArticleContent getById(ArticleContentId id);

    Long getArticleContentLastVersion(Article article);

    Page<ArticleContent> getByArticle(Article article, Pageable pageable);
}
