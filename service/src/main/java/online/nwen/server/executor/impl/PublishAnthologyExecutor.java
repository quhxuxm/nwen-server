package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.PublishAnthologyRequestPayload;
import online.nwen.server.executor.api.payload.PublishAnthologyResponsePayload;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class PublishAnthologyExecutor
        implements IExecutor<PublishAnthologyResponsePayload, PublishAnthologyRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(PublishAnthologyExecutor.class);
    private IAnthologyService anthologyService;

    public PublishAnthologyExecutor(
            IAnthologyService anthologyService
    ) {
        this.anthologyService = anthologyService;
    }

    @Override
    public void exec(IExecutorRequest<PublishAnthologyRequestPayload> request,
                     IExecutorResponse<PublishAnthologyResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Publish anthology for author: {}", securityContext.getAuthorId());
        PublishAnthologyRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getAnthologyId())) {
            logger.error("Fail to publish anthology because of anthology id is empty.");
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_ID_IS_EMPTY);
        }
        Anthology targetAnthology = this.anthologyService.findById(requestPayload.getAnthologyId());
        if (targetAnthology == null) {
            logger.error("Fail to publish anthology because of anthology not exist, anthology id = [{}].",
                    requestPayload.getAnthologyId());
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_NOT_EXIST);
        }
        if (!targetAnthology.getAuthorId().equals(securityContext.getAuthorId())) {
            logger.error(
                    "Fail to publish anthology because of author is not the owner, author is [{}], anthology is [{}].",
                    securityContext.getAuthorId(), targetAnthology.getId());
            throw new ExecutorException(ExecutorException.Code.NOT_ARTICLE_OWNER);
        }
        if (targetAnthology.isAuthorConfirmedPublish() != requestPayload.isPublish()) {
            targetAnthology.setUpdateDate(new Date());
            if (requestPayload.isPublish()) {
                targetAnthology.setAuthorConfirmedPublishDate(new Date());
            }
        }
        targetAnthology.setAuthorConfirmedPublish(requestPayload.isPublish());
        this.anthologyService.save(targetAnthology);
        PublishAnthologyResponsePayload publishAnthologyResponsePayload = new PublishAnthologyResponsePayload();
        publishAnthologyResponsePayload.setAnthologyId(targetAnthology.getId());
        response.setPayload(publishAnthologyResponsePayload);
    }
}
