package online.nwen.server.dao.api;

import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IArticleCommentDao {
    ArticleComment save(ArticleComment articleComment);

    ArticleComment getById(Long id);

    Page<ArticleComment> getByReplyTo(ArticleComment replyTo, Pageable pageable);

    Page<ArticleComment> getByArticle(Article article, Pageable pageable);

    Page<Long> getIdsByReplyTo(ArticleComment replyTo, Pageable pageable);

    Page<Long> getIdsByArticle(Article article, Pageable pageable);
}
