package online.nwen.server.entry.controller.security;

import online.nwen.server.bo.CreateArticleCommentRequestBo;
import online.nwen.server.bo.CreateArticleCommentResponseBo;
import online.nwen.server.bo.UpdateArticleCommentRequestBo;
import online.nwen.server.bo.UpdateArticleCommentResponseBo;
import online.nwen.server.entry.controller.SecurityApi;
import online.nwen.server.service.api.IArticleCommentService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SecurityApi
class SecurityCommentController {
    private IArticleCommentService articleCommentService;

    SecurityCommentController(IArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    @PostMapping(path = "/comment")
    CreateArticleCommentResponseBo create(@RequestBody CreateArticleCommentRequestBo createArticleCommentRequestBo) {
        return this.articleCommentService.create(createArticleCommentRequestBo);
    }

    @PatchMapping(path = "/comment/{commentId}")
    UpdateArticleCommentResponseBo update(@PathVariable(name = "commentId") Long commentId, @RequestBody UpdateArticleCommentRequestBo updateArticleCommentRequestBo) {
        return this.articleCommentService.update(commentId, updateArticleCommentRequestBo);
    }
}
