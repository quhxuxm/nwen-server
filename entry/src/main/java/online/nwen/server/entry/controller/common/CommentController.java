package online.nwen.server.entry.controller.common;

import online.nwen.server.bo.ArticleCommentBo;
import online.nwen.server.entry.controller.CommonApi;
import online.nwen.server.service.api.IArticleCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@CommonApi
class CommentController {
    private IArticleCommentService articleCommentService;

    CommentController(IArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    @GetMapping(path = "/comment/article/{articleId}")
    Page<ArticleCommentBo> getCommentsOfArticle(@PathVariable("articleId") Long articleId, @RequestParam(value = "loadReply", required = false) boolean loadReply,
                                                Pageable pageable) {
        return this.articleCommentService.getComments(articleId, loadReply, pageable);
    }

    @GetMapping(path = "/comment/replyTo/{commentId}")
    Page<ArticleCommentBo> getCommentsOfReplyTo(@PathVariable("commentId") Long commentId, @RequestParam(value = "loadReply", required = false) boolean loadReply,
                                                Pageable pageable) {
        return this.articleCommentService.getReplyToComments(commentId, loadReply, pageable);
    }
}
