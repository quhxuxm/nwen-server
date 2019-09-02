package online.nwen.server.dao.impl;

import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface IArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
    Page<ArticleComment> findByArticleAndReplyToIsNull(Article article, Pageable pageable);

    Page<ArticleComment> findByReplyTo(ArticleComment replyTo, Pageable pageable);

    @Query("select ac.id from ArticleComment  ac where ac.replyTo=:replyTo")
    Page<Long> findIdsByReplyTo(@Param("replyTo") ArticleComment replyTo, Pageable pageable);

    @Query("select ac.id from ArticleComment  ac where ac.replyTo is null and ac.article=:article")
    Page<Long> findIdsByArticleAndReplyToIsNull(@Param("article") Article article, Pageable pageable);
}
