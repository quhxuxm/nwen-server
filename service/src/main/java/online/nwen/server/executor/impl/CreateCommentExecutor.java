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
import online.nwen.server.repository.IAnthologyRepository;
import online.nwen.server.repository.IArticleRepository;
import online.nwen.server.repository.IAuthorRepository;
import online.nwen.server.repository.ICommentRepository;
import online.nwen.server.service.api.ISecurityContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CreateCommentExecutor implements IExecutor<CreateCommentResponsePayload, CreateCommentRequestPayload> {
    private IArticleRepository articleRepository;
    private IAnthologyRepository anthologyRepository;
    private IAuthorRepository authorRepository;
    private ICommentRepository commentRepository;

    public CreateCommentExecutor(IArticleRepository articleRepository,
                                 IAnthologyRepository anthologyRepository,
                                 IAuthorRepository authorRepository,
                                 ICommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.anthologyRepository = anthologyRepository;
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void exec(IExecutorRequest<CreateCommentRequestPayload> request,
                     IExecutorResponse<CreateCommentResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        CreateCommentRequestPayload requestPayload = request.getPayload();
        Optional<Author> currentAuthorOptional = this.authorRepository.findById(securityContext.getAuthorId());
        if (!currentAuthorOptional.isPresent()) {
            throw new ExecutorException(ExecutorException.Code.AUTHOR_NOT_EXIST);
        }
        Author currentAuthor = currentAuthorOptional.get();
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
                this.articleRepository.save(article);
                break;
            case COMMENT:
                Comment parentComment = this.findCommentByRefDocumentId(requestPayload.getRefDocumentId());
                parentComment.setCommentNumber(parentComment.getCommentNumber() + 1);
                this.commentRepository.save(parentComment);
                break;
            case ANTHOLOGY:
                Anthology anthology = this.findAnthologyByRefDocumentId(requestPayload.getRefDocumentId());
                anthology.setCommentsNumber(anthology.getCommentsNumber() + 1);
                this.anthologyRepository.save(anthology);
                break;
            default:
                throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        this.commentRepository.save(comment);
        currentAuthor.setCommentNumber(currentAuthor.getCommentNumber() + 1);
        this.authorRepository.save(currentAuthor);
        CreateCommentResponsePayload responsePayload = new CreateCommentResponsePayload();
        responsePayload.setCommentId(comment.getId());
        response.setPayload(responsePayload);
    }

    private Article findArticleByRefDocumentId(String refDocumentId) throws ExecutorException {
        Optional<Article> articleOptional = this.articleRepository.findById(refDocumentId);
        if (!articleOptional.isPresent()) {
            throw new ExecutorException(ExecutorException.Code.ARTICLE_NOT_EXIST);
        }
        return articleOptional.get();
    }

    private Anthology findAnthologyByRefDocumentId(String refDocumentId) throws ExecutorException {
        Optional<Anthology> anthologyOptional = this.anthologyRepository.findById(refDocumentId);
        if (!anthologyOptional.isPresent()) {
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_NOT_EXIST);
        }
        return anthologyOptional.get();
    }

    private Comment findCommentByRefDocumentId(String refDocumentId) throws ExecutorException {
        Optional<Comment> commentOptional = this.commentRepository.findById(refDocumentId);
        if (!commentOptional.isPresent()) {
            throw new ExecutorException(ExecutorException.Code.COMMENT_NOT_EXIST);
        }
        return commentOptional.get();
    }
}
