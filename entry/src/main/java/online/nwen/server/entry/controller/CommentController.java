package online.nwen.server.entry.controller;

import online.nwen.server.bo.*;
import online.nwen.server.service.api.IArticleCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Api
class CommentController {
    private IArticleCommentService articleCommentService;

    CommentController(IArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    @PostMapping(path = "/security/comment")
    CreateArticleCommentResponseBo create(@RequestBody CreateArticleCommentRequestBo createArticleCommentRequestBo) {
        return this.articleCommentService.create(createArticleCommentRequestBo);
    }

    @PatchMapping(path = "/security/comment/{commentId)")
    UpdateArticleCommentResponseBo update(@PathVariable(name = "commentId") Long commentId, @RequestBody UpdateArticleCommentRequestBo updateArticleCommentRequestBo) {
        return this.articleCommentService.update(commentId, updateArticleCommentRequestBo);
    }

    @GetMapping(path = "/comment/article/{articleId}")
    Page<ArticleCommentBo> getCommentsOfArticle(@PathVariable("articleId") Long articleId, Pageable pageable) {
        return this.articleCommentService.getComments(articleId, pageable);
    }

    @GetMapping(path = "/comment/replyTo/{commentId}")
    Page<ArticleCommentBo> getCommentsOfReplyTo(@PathVariable("commentId") Long commentId, Pageable pageable) {
        return this.articleCommentService.getReplyToComments(commentId, pageable);
    }
}
