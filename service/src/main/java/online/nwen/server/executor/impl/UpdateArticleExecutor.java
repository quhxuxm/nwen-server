package online.nwen.server.executor.impl;

import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.service.api.payload.UpdateArticleRequestPayload;
import online.nwen.server.service.api.payload.UpdateArticleResponsePayload;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UpdateArticleExecutor implements IExecutor<UpdateArticleResponsePayload, UpdateArticleRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(UpdateArticleExecutor.class);

    @Override
    public void exec(IExecutorRequest<UpdateArticleRequestPayload> request,
                     IExecutorResponse<UpdateArticleResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.info("Create article for author: {}", securityContext.getUsername());
    }
}
