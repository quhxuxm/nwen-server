package online.nwen.server.executor.impl;

import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.ViewAuthorDetailRequestPayload;
import online.nwen.server.executor.api.payload.ViewAuthorDetailResponsePayload;
import online.nwen.server.repository.IAuthorRepository;
import online.nwen.server.service.api.ISecurityContext;

import java.util.Optional;

public class ViewAuthorDetailExecutor
        implements IExecutor<ViewAuthorDetailResponsePayload, ViewAuthorDetailRequestPayload> {
    private IAuthorRepository authorRepository;

    public ViewAuthorDetailExecutor(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void exec(IExecutorRequest<ViewAuthorDetailRequestPayload> request,
                     IExecutorResponse<ViewAuthorDetailResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        ViewAuthorDetailRequestPayload requestPayload = request.getPayload();
        Optional<Author> targetAuthorOptional = this.authorRepository.findById(requestPayload.getAuthorId());
        if (!targetAuthorOptional.isPresent()) {
            throw new ExecutorException(ExecutorException.Code.AUTHOR_NOT_EXIST);
        }
        Author targetAuthor = targetAuthorOptional.get();
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
