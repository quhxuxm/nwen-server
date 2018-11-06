package online.nwen.server.executor.impl;

import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.ViewAuthorDetailRequestPayload;
import online.nwen.server.executor.api.payload.ViewAuthorDetailResponsePayload;
import online.nwen.server.service.api.IAuthorService;
import online.nwen.server.service.api.ISecurityContext;
import org.springframework.stereotype.Service;

@Service
public class ViewAuthorDetailExecutor
        implements IExecutor<ViewAuthorDetailResponsePayload, ViewAuthorDetailRequestPayload> {
    private IAuthorService authorService;

    public ViewAuthorDetailExecutor(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void exec(IExecutorRequest<ViewAuthorDetailRequestPayload> request,
                     IExecutorResponse<ViewAuthorDetailResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        ViewAuthorDetailRequestPayload requestPayload = request.getPayload();
        Author targetAuthor = this.authorService.findById(requestPayload.getAuthorId());
        if (targetAuthor == null) {
            throw new ExecutorException(ExecutorException.Code.AUTHOR_NOT_EXIST);
        }
        ViewAuthorDetailResponsePayload responsePayload = new ViewAuthorDetailResponsePayload();
        responsePayload.setUsername(targetAuthor.getUsername());
        responsePayload.setNickname(targetAuthor.getNickname());
        responsePayload.setDescription(targetAuthor.getDescription());
        responsePayload.setId(targetAuthor.getId());
        responsePayload.setAnthologyNumber(targetAuthor.getAnthologyNumber());
        responsePayload.setArticleNumber(targetAuthor.getArticleNumber());
        responsePayload.setCommentNumber(targetAuthor.getCommentNumber());
        responsePayload.setFollowerNumber(targetAuthor.getFollowerNumber());
        response.setPayload(responsePayload);
    }
}
