package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.Author;
import online.nwen.server.domain.Comment;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.CreateCommentRequestPayload;
import online.nwen.server.executor.api.payload.CreateCommentResponsePayload;
import online.nwen.server.service.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CreateCommentExecutor implements IExecutor<CreateCommentResponsePayload, CreateCommentRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(CreateCommentExecutor.class);
    private IArticleService articleService;
    private IAnthologyService anthologyService;
    private IAuthorService authorService;
    private ICommentService commentService;

    public CreateCommentExecutor(IArticleService articleService,
                                 IAnthologyService anthologyService,
                                 IAuthorService authorService,
                                 ICommentService commentService) {
        this.articleService = articleService;
        this.anthologyService = anthologyService;
        this.authorService = authorService;
        this.commentService = commentService;
    }

    @Override
    public void exec(IExecutorRequest<CreateCommentRequestPayload> request,
                     IExecutorResponse<CreateCommentResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        CreateCommentRequestPayload requestPayload = request.getPayload();
        Author currentAuthor = this.authorService.findById(securityContext.getAuthorId());
        if (currentAuthor == null) {
            logger.error("Can not create comment because of author not exist, author id: {}",
                    securityContext.getAuthorId());
            throw new ExecutorException(ExecutorException.Code.AUTHOR_NOT_EXIST);
        }
        Comment comment = new Comment();
        comment.setAuthorId(currentAuthor.getId());
        comment.setContent(requestPayload.getContent());
        comment.setCreateDate(new Date());
        Comment.Type commentType = null;
        try {
            commentType = Comment.Type.valueOf(requestPayload.getType());
        } catch (Exception e) {
            throw new ExecutorException(ExecutorException.Code.INPUT_ERROR);
        }
        comment.setType(commentType);
        comment.setUpdateDate(new Date());
        comment.setRefDocumentId(requestPayload.getRefDocumentId());
        switch (commentType) {
            case ARTICLE:
                Article article = this.findArticleByRefDocumentId(requestPayload.getRefDocumentId());
                article.setCommentNumber(article.getCommentNumber());
                this.articleService.save(article);
                break;
            case COMMENT:
                Comment parentComment = this.findCommentByRefDocumentId(requestPayload.getRefDocumentId());
                parentComment.setCommentNumber(parentComment.getCommentNumber() + 1);
                this.commentService.save(parentComment);
                break;
            case ANTHOLOGY:
                Anthology anthology = this.findAnthologyByRefDocumentId(requestPayload.getRefDocumentId());
                anthology.setCommentsNumber(anthology.getCommentsNumber() + 1);
                this.anthologyService.save(anthology);
                break;
            default:
                throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        this.commentService.save(comment);
        currentAuthor.setCommentNumber(currentAuthor.getCommentNumber() + 1);
        this.authorService.save(currentAuthor);
        CreateCommentResponsePayload responsePayload = new CreateCommentResponsePayload();
        responsePayload.setCommentId(comment.getId());
        response.setPayload(responsePayload);
    }

    private Article findArticleByRefDocumentId(String refDocumentId) throws ExecutorException {
        Article article = this.articleService.findById(refDocumentId);
        if (article == null) {
            throw new ExecutorException(ExecutorException.Code.ARTICLE_NOT_EXIST);
        }
        return article;
    }

    private Anthology findAnthologyByRefDocumentId(String refDocumentId) throws ExecutorException {
        Anthology anthology = this.anthologyService.findById(refDocumentId);
        if (anthology == null) {
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_NOT_EXIST);
        }
        return anthology;
    }

    private Comment findCommentByRefDocumentId(String refDocumentId) throws ExecutorException {
        Comment comment = this.commentService.findById(refDocumentId);
        if (comment == null) {
            throw new ExecutorException(ExecutorException.Code.COMMENT_NOT_EXIST);
        }
        return comment;
    }
}
