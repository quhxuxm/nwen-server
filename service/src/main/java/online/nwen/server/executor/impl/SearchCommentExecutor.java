package online.nwen.server.executor.impl;

import online.nwen.server.domain.Comment;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.SearchCommentRequestPayload;
import online.nwen.server.executor.api.payload.SearchCommentResponsePayload;
import online.nwen.server.service.api.ICommentService;
import online.nwen.server.service.api.ISecurityContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchCommentExecutor implements IExecutor<SearchCommentResponsePayload, SearchCommentRequestPayload> {
    private ICommentService commentService;

    public SearchCommentExecutor(ICommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public void exec(IExecutorRequest<SearchCommentRequestPayload> request,
                     IExecutorResponse<SearchCommentResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        SearchCommentRequestPayload requestPayload = request.getPayload();
        if (requestPayload.getCondition() == null) {
            throw new ExecutorException(ExecutorException.Code.INPUT_ERROR);
        }
        SearchCommentRequestPayload.Condition condition = requestPayload.getCondition();
        if (condition.getType() == null) {
            throw new ExecutorException(ExecutorException.Code.INPUT_ERROR);
        }
        Pageable pageable = PageRequest.of(requestPayload.getPageIndex(), requestPayload.getPageSize());
        if (SearchCommentRequestPayload.Condition.Type.ARTICLE == condition.getType()) {
            String articleId = condition.getParams().get("articleId");
            Page<Comment> commentPage =
                    this.commentService.findAllByTypeAndRefDocumentId(Comment.Type.ARTICLE, articleId, pageable);
            Page<SearchCommentResponsePayload.CommentSearchRecord> recordPage =
                    this.convertCommentPageToSearchRecordPage(commentPage);
            SearchCommentResponsePayload responsePayload = new SearchCommentResponsePayload();
            responsePayload.setRecords(recordPage);
            response.setPayload(responsePayload);
            return;
        }
        if (SearchCommentRequestPayload.Condition.Type.ANTHOLOGY == condition.getType()) {
            String anthologyId = condition.getParams().get("anthologyId");
            Page<Comment> commentPage =
                    this.commentService.findAllByTypeAndRefDocumentId(Comment.Type.ANTHOLOGY, anthologyId, pageable);
            Page<SearchCommentResponsePayload.CommentSearchRecord> recordPage =
                    this.convertCommentPageToSearchRecordPage(commentPage);
            SearchCommentResponsePayload responsePayload = new SearchCommentResponsePayload();
            responsePayload.setRecords(recordPage);
            response.setPayload(responsePayload);
            return;
        }
        if (SearchCommentRequestPayload.Condition.Type.COMMENT == condition.getType()) {
            String commentId = condition.getParams().get("commentId");
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
            return record;
        });
    }
}
