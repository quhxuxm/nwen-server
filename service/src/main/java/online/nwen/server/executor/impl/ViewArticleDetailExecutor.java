package online.nwen.server.executor.impl;

import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.service.api.payload.ViewArticleDetailRequestPayload;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.service.api.payload.ViewArticleDetailResponsePayload;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ViewArticleDetailExecutor implements
        IExecutor<ViewArticleDetailResponsePayload, ViewArticleDetailRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(ViewArticleDetailExecutor.class);

    @Override
    public void exec(IExecutorRequest<ViewArticleDetailRequestPayload> request,
                     IExecutorResponse<ViewArticleDetailResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Begin to view article detail.");
        logger.debug("Current security context: {}", securityContext);
    }
}