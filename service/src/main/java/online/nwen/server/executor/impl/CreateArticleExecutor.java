package online.nwen.server.executor.impl;

import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.service.api.ISecurityContext;
import online.nwen.server.executor.api.payload.CreateArticleRequestPayload;
import online.nwen.server.executor.api.payload.CreateArticleResponsePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateArticleExecutor implements IExecutor<CreateArticleResponsePayload, CreateArticleRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(CreateArticleExecutor.class);

    @Override
    public void exec(IExecutorRequest<CreateArticleRequestPayload> request,
                     IExecutorResponse<CreateArticleResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.info("Create article for author: {}", securityContext.getUsername());
    }
}
