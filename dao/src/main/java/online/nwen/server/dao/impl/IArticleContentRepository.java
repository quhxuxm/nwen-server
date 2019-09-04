package online.nwen.server.dao.impl;

import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticleContent;
import online.nwen.server.domain.ArticleContentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface IArticleContentRepository extends JpaRepository<ArticleContent, ArticleContentId> {
    @Query("select max(ac.id.version) from ArticleContent ac where ac.id.article=:article")
    Long findArticleContentLastVersion(@Param("article") Article article);

    @Query("select ac from ArticleContent ac where ac.id.article=:article order by ac.id.version desc")
    List<ArticleContent> findByArticleOrderByVersionDesc(@Param("article") Article article);
}
