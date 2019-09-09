package online.nwen.server.dao.api;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticlePraise;
import online.nwen.server.domain.User;

public interface IArticlePraiseDao {
    ArticlePraise save(ArticlePraise articlePraise);

    Long countTotalPraiseOfAnthology(Anthology anthology);

    Long countTotalPraiseOfArticle(Article article);

    ArticlePraise getByArticleAndUser(Article article, User user);
}
