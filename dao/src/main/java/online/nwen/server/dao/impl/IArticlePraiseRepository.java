package online.nwen.server.dao.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticlePraise;
import online.nwen.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IArticlePraiseRepository extends JpaRepository<ArticlePraise, Long> {
    Long countByArticle_Anthology(Anthology anthology);

    Long countByArticle(Article article);

    ArticlePraise findByArticleAndUser(Article article, User user);
}
