package online.nwen.server.dao.api;

import online.nwen.server.domain.ArticleContent;

public interface IArticleContentDao {
    ArticleContent save(ArticleContent content);

    ArticleContent getById(Long id);
}
