package online.nwen.server.entry.controller;

import online.nwen.server.bo.ArticleCommentBo;
import online.nwen.server.bo.CreateArticleCommentRequestBo;
import online.nwen.server.bo.CreateArticleCommentResponseBo;
import online.nwen.server.service.api.IArticleCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class ArticleCommentController {
    private IArticleCommentService articleCommentService;

    ArticleCommentController(IArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    @PostMapping("/security/comment/{articleId)")
    CreateArticleCommentResponseBo create(@PathVariable("articleId") Long articleId, @RequestBody CreateArticleCommentRequestBo createArticleCommentRequestBo) {
        return this.articleCommentService.create(articleId, createArticleCommentRequestBo);
    }

    @GetMapping("/comment/article/{articleId}")
    Page<ArticleCommentBo> getComments(@PathVariable("articleId") Long articleId, Pageable pageable) {
        return this.articleCommentService.getComments(articleId, pageable);
    }

    @GetMapping("/comment/reply/{commentId}")
    Page<ArticleCommentBo> getReplyToComments(@PathVariable("commentId") Long commentId, Pageable pageable) {
        return this.articleCommentService.getReplyToComments(commentId, pageable);
    }
}
