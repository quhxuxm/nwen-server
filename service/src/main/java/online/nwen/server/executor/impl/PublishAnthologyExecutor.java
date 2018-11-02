package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.PublishAnthologyRequestPayload;
import online.nwen.server.executor.api.payload.PublishAnthologyResponsePayload;
import online.nwen.server.repository.IAnthologyRepository;
import online.nwen.server.repository.IAuthorRepository;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

@Service
public class PublishAnthologyExecutor
        implements IExecutor<PublishAnthologyResponsePayload, PublishAnthologyRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(PublishAnthologyExecutor.class);
    private IAuthorRepository authorRepository;
    private IAnthologyRepository anthologyRepository;

    public PublishAnthologyExecutor(IAuthorRepository authorRepository,
                                    IAnthologyRepository anthologyRepository
    ) {
        this.authorRepository = authorRepository;
        this.anthologyRepository = anthologyRepository;
    }

    @Override
    public void exec(IExecutorRequest<PublishAnthologyRequestPayload> request,
                     IExecutorResponse<PublishAnthologyResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Publish anthology for author: {}", securityContext.getUsername());
        PublishAnthologyRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getAnthologyId())) {
            logger.error("Fail to publish anthology because of anthology id is empty.");
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_ID_IS_EMPTY);
        }
        Author currentAuthor = null;
        try {
            currentAuthor = this.authorRepository.findByUsername(securityContext.getUsername());
        } catch (Exception e) {
            logger.error("Fail to publish anthology because of exception happen on search current author.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        if (currentAuthor == null) {
            logger.error("Fail to publish anthology because of can not find current author [{}].",
                    securityContext.getUsername());
            throw new ExecutorException(ExecutorException.Code.CURRENT_AUTHOR_NOT_EXIST);
        }
        Optional<Anthology> targetAnthologyOptional =
                this.anthologyRepository.findById(requestPayload.getAnthologyId());
        if (!targetAnthologyOptional.isPresent()) {
            logger.error("Fail to publish anthology because of anthology not exist, anthology id = [{}].",
                    requestPayload.getAnthologyId());
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_NOT_EXIST);
        }
        Anthology targetAnthology = targetAnthologyOptional.get();
        if (!targetAnthology.getAuthorId().equals(currentAuthor.getId())) {
            logger.error(
                    "Fail to publish anthology because of author is not the owner, author is [{}], anthology is [{}].",
                    securityContext.getUsername(), targetAnthology.getId());
            throw new ExecutorException(ExecutorException.Code.NOT_ARTICLE_OWNER);
        }
        if (targetAnthology.isPublish() != requestPayload.isPublish()) {
            targetAnthology.setUpdateDate(new Date());
            if (requestPayload.isPublish()) {
                targetAnthology.setPublishDate(new Date());
            }
        }
        targetAnthology.setPublish(requestPayload.isPublish());
        try {
            this.anthologyRepository.save(targetAnthology);
        } catch (Exception e) {
            logger.error("Fail to publish anthology because of exception.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        PublishAnthologyResponsePayload publishAnthologyResponsePayload = new PublishAnthologyResponsePayload();
        publishAnthologyResponsePayload.setAnthologyId(targetAnthology.getId());
        response.setPayload(publishAnthologyResponsePayload);
    }
}
