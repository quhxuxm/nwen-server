package online.nwen.server.executor.impl;

import online.nwen.server.configuration.GlobalConfiguration;
import online.nwen.server.domain.Anthology;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.UpdateAnthologyRequestPayload;
import online.nwen.server.executor.api.payload.UpdateAnthologyResponsePayload;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class UpdateAnthologyExecutor
        implements IExecutor<UpdateAnthologyResponsePayload, UpdateAnthologyRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(UpdateAnthologyExecutor.class);
    private IAnthologyService anthologyService;
    private GlobalConfiguration globalConfiguration;

    public UpdateAnthologyExecutor(IAnthologyService anthologyService,
                                   GlobalConfiguration globalConfiguration) {
        this.anthologyService = anthologyService;
        this.globalConfiguration = globalConfiguration;
    }

    @Override
    public void exec(IExecutorRequest<UpdateAnthologyRequestPayload> request,
                     IExecutorResponse<UpdateAnthologyResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Update anthology for author: {}", securityContext.getAuthorId());
        UpdateAnthologyRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getAnthologyId())) {
            logger.error("Fail to update anthology because of anthology id is empty.");
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_ID_IS_EMPTY);
        }
        if (StringUtils.isEmpty(requestPayload.getTitle())) {
            logger.error("Fail to update anthology because of title is empty.");
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_TITLE_IS_EMPTY);
        }
        if (requestPayload.getTitle().trim().length() > this.globalConfiguration.getArticleTitleMaxLength()) {
            logger.error("Fail to update anthology because of title length exceed.");
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_TITLE_IS_TOO_LONG);
        }
        Anthology targetAnthology =
                this.anthologyService.findById(requestPayload.getAnthologyId());
        if (targetAnthology == null) {
            logger.error("Fail to update anthology because of anthology not exist, anthology id = [{}].",
                    requestPayload.getAnthologyId());
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_NOT_EXIST);
        }
        if (!targetAnthology.getAuthorId().equals(securityContext.getAuthorId())) {
            logger.error(
                    "Fail to update anthology because of author is not the owner, author is [{}], anthology is [{}].",
                    securityContext.getAuthorId(), targetAnthology.getId());
            throw new ExecutorException(ExecutorException.Code.NOT_ANTHOLOGY_OWNER);
        }
        targetAnthology.setTitle(requestPayload.getTitle());
        targetAnthology.setUpdateDate(new Date());
        targetAnthology.setSummary(requestPayload.getSummary());
        targetAnthology.setTags(requestPayload.getTags());
        this.anthologyService.save(targetAnthology);
        UpdateAnthologyResponsePayload updateAnthologyResponsePayload = new UpdateAnthologyResponsePayload();
        updateAnthologyResponsePayload.setAnthologyId(targetAnthology.getId());
        response.setPayload(updateAnthologyResponsePayload);
    }
}
