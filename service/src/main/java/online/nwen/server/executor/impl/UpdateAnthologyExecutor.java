package online.nwen.server.executor.impl;

import online.nwen.server.configuration.GlobalConfiguration;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.UpdateAnthologyRequestPayload;
import online.nwen.server.executor.api.payload.UpdateAnthologyResponsePayload;
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
public class UpdateAnthologyExecutor
        implements IExecutor<UpdateAnthologyResponsePayload, UpdateAnthologyRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(UpdateAnthologyExecutor.class);
    private IAnthologyRepository anthologyRepository;
    private GlobalConfiguration globalConfiguration;
    private IAuthorRepository authorRepository;

    public UpdateAnthologyExecutor(IAnthologyRepository anthologyRepository,
                                   GlobalConfiguration globalConfiguration,
                                   IAuthorRepository authorRepository) {
        this.anthologyRepository = anthologyRepository;
        this.globalConfiguration = globalConfiguration;
        this.authorRepository = authorRepository;
    }

    @Override
    public void exec(IExecutorRequest<UpdateAnthologyRequestPayload> request,
                     IExecutorResponse<UpdateAnthologyResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Update anthology for author: {}", securityContext.getUsername());
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
        Author currentAuthor = null;
        try {
            currentAuthor = this.authorRepository.findByUsername(securityContext.getUsername());
        } catch (Exception e) {
            logger.error("Fail to update anthology because of exception happen on search current author.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        if (currentAuthor == null) {
            logger.error("Fail to update anthology because of can not find current author [{}].",
                    securityContext.getUsername());
            throw new ExecutorException(ExecutorException.Code.CURRENT_AUTHOR_NOT_EXIST);
        }
        Optional<Anthology> targetAnthologyOptional =
                this.anthologyRepository.findById(requestPayload.getAnthologyId());
        if (!targetAnthologyOptional.isPresent()) {
            logger.error("Fail to update anthology because of anthology not exist, anthology id = [{}].",
                    requestPayload.getAnthologyId());
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_NOT_EXIST);
        }
        Anthology targetAnthology = targetAnthologyOptional.get();
        if (!targetAnthology.getAuthorId().equals(currentAuthor.getId())) {
            logger.error(
                    "Fail to update anthology because of author is not the owner, author is [{}], anthology is [{}].",
                    securityContext.getUsername(), targetAnthology.getId());
            throw new ExecutorException(ExecutorException.Code.NOT_ANTHOLOGY_OWNER);
        }
        targetAnthology.setTitle(requestPayload.getTitle());
        targetAnthology.setUpdateDate(new Date());
        targetAnthology.setSummary(requestPayload.getSummary());
        targetAnthology.setTags(requestPayload.getTags());
        try {
            this.anthologyRepository.save(targetAnthology);
        } catch (Exception e) {
            logger.error("Fail to create article because of exception.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        UpdateAnthologyResponsePayload updateAnthologyResponsePayload = new UpdateAnthologyResponsePayload();
        updateAnthologyResponsePayload.setAnthologyId(targetAnthology.getId());
        response.setPayload(updateAnthologyResponsePayload);
    }
}
