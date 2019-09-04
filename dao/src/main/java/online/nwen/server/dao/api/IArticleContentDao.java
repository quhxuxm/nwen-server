package online.nwen.server.dao.api;

import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticleContent;
import online.nwen.server.domain.ArticleContentId;

import java.util.List;

public interface IArticleContentDao {
    ArticleContent save(ArticleContent content);

    ArticleContent getById(ArticleContentId id);

    Long getArticleContentLastVersion(Article article);

    List<ArticleContent> getByArticleOrderByVersionDesc(Article article);
}
