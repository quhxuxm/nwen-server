package online.nwen.server.executor.impl;

import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.ViewArticleSummaryRequestPayload;
import online.nwen.server.executor.api.payload.ViewArticleSummaryResponsePayload;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ViewArticleSummaryExecutor implements
        IExecutor<ViewArticleSummaryResponsePayload, ViewArticleSummaryRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(ViewArticleSummaryExecutor.class);

    @Override
    public void exec(IExecutorRequest<ViewArticleSummaryRequestPayload> request,
                     IExecutorResponse<ViewArticleSummaryResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Begin to view article summary.");
        logger.debug("Current security context: {}", securityContext);
    }
}
