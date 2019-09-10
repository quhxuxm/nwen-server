package online.nwen.server.service.api;

import online.nwen.server.bo.ArticleCommentBo;
import online.nwen.server.bo.CreateArticleCommentRequestBo;
import online.nwen.server.bo.CreateArticleCommentResponseBo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IArticleCommentService {
    CreateArticleCommentResponseBo create(Long articleId, CreateArticleCommentRequestBo createArticleCommentRequestBo);

    Page<ArticleCommentBo> getComments(Long articleId, Pageable pageable);

    Page<ArticleCommentBo> getReplyToComments(Long commentId, Pageable pageable);
}
