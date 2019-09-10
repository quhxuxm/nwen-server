package online.nwen.server.service.api;

import online.nwen.server.bo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IArticleCommentService {
    CreateArticleCommentResponseBo create(CreateArticleCommentRequestBo createArticleCommentRequestBo);

    UpdateArticleCommentResponseBo update(Long commentId, UpdateArticleCommentRequestBo updateArticleCommentRequestBo);

    Page<ArticleCommentBo> getComments(Long articleId, Pageable pageable);

    Page<ArticleCommentBo> getReplyToComments(Long commentId, Pageable pageable);
}
