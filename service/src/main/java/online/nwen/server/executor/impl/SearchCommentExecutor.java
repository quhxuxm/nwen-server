package online.nwen.server.executor.impl;

import online.nwen.server.domain.Author;
import online.nwen.server.domain.Comment;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.SearchCommentRequestPayload;
import online.nwen.server.executor.api.payload.SearchCommentResponsePayload;
import online.nwen.server.service.api.IAuthorService;
import online.nwen.server.service.api.ICommentService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SearchCommentExecutor implements IExecutor<SearchCommentResponsePayload, SearchCommentRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(SearchCommentExecutor.class);
    private ICommentService commentService;
    private IAuthorService authorService;

    public SearchCommentExecutor(ICommentService commentService,
                                 IAuthorService authorService) {
        this.commentService = commentService;
        this.authorService = authorService;
    }

    @Override
    public void exec(IExecutorRequest<SearchCommentRequestPayload> request,
                     IExecutorResponse<SearchCommentResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        SearchCommentRequestPayload requestPayload = request.getPayload();
        if (requestPayload.getCondition() == null) {
            logger.error("Can not search the comment because of condition is null.");
            throw new ExecutorException(ExecutorException.Code.INPUT_ERROR);
        }
        Pageable pageable = null;
        if (requestPayload.getCondition().isAsc()) {
            pageable = PageRequest.of(requestPayload.getPageIndex(), requestPayload.getPageSize(), Sort.Direction.ASC);
        } else {
            pageable = PageRequest.of(requestPayload.getPageIndex(), requestPayload.getPageSize(), Sort.Direction.DESC);
        }
        if (SearchCommentRequestPayload.Condition.Type.ARTICLE == requestPayload.getCondition().getType()) {
            String articleId = requestPayload.getCondition().getParams().get("articleId");
            Page<Comment> commentPage =
                    this.commentService.findAllByTypeAndRefDocumentId(Comment.Type.ARTICLE, articleId, pageable);
            Page<SearchCommentResponsePayload.CommentSearchRecord> recordPage =
                    this.convertCommentPageToSearchRecordPage(commentPage);
            SearchCommentResponsePayload responsePayload = new SearchCommentResponsePayload();
            responsePayload.setRecords(recordPage);
            response.setPayload(responsePayload);
            return;
        }
        if (SearchCommentRequestPayload.Condition.Type.ANTHOLOGY == requestPayload.getCondition().getType()) {
            String anthologyId = requestPayload.getCondition().getParams().get("anthologyId");
            Page<Comment> commentPage =
                    this.commentService.findAllByTypeAndRefDocumentId(Comment.Type.ANTHOLOGY, anthologyId, pageable);
            Page<SearchCommentResponsePayload.CommentSearchRecord> recordPage =
                    this.convertCommentPageToSearchRecordPage(commentPage);
            SearchCommentResponsePayload responsePayload = new SearchCommentResponsePayload();
            responsePayload.setRecords(recordPage);
            response.setPayload(responsePayload);
            return;
        }
        if (SearchCommentRequestPayload.Condition.Type.COMMENT == requestPayload.getCondition().getType()) {
            String commentId = requestPayload.getCondition().getParams().get("commentId");
            Page<Comment> commentPage =
                    this.commentService.findAllByTypeAndRefDocumentId(Comment.Type.COMMENT, commentId, pageable);
            Page<SearchCommentResponsePayload.CommentSearchRecord> recordPage =
                    this.convertCommentPageToSearchRecordPage(commentPage);
            SearchCommentResponsePayload responsePayload = new SearchCommentResponsePayload();
            responsePayload.setRecords(recordPage);
            response.setPayload(responsePayload);
            return;
        }
        throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
    }

    private Page<SearchCommentResponsePayload.CommentSearchRecord> convertCommentPageToSearchRecordPage(
            Page<Comment> commentPage) {
        return commentPage.map(comment -> {
            SearchCommentResponsePayload.CommentSearchRecord record =
                    new SearchCommentResponsePayload.CommentSearchRecord();
            record.setCommentId(comment.getId());
            record.setContent(comment.getContent());
            record.setCreateDate(comment.getCreateDate());
            record.setSubCommentNumber(comment.getCommentNumber());
            record.setAuthorId(comment.getAuthorId());
            Author commentAuthor = this.authorService.findById(comment.getAuthorId());
            if (commentAuthor != null) {
                record.setAuthorNickname(commentAuthor.getNickname());
                record.setAuthorUsername(commentAuthor.getUsername());
                record.setAuthorIconImageId(commentAuthor.getIconImageId());
            }
            return record;
        });
    }
}
