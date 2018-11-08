package online.nwen.server.executor.impl;

import online.nwen.server.configuration.GlobalConfiguration;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.CreateAnthologyRequestPayload;
import online.nwen.server.executor.api.payload.CreateAnthologyResponsePayload;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.IAuthorService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class CreateAnthologyExecutor
        implements IExecutor<CreateAnthologyResponsePayload, CreateAnthologyRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(CreateAnthologyExecutor.class);
    private IAnthologyService anthologyService;
    private IAuthorService authorService;
    private GlobalConfiguration globalConfiguration;

    public CreateAnthologyExecutor(IAnthologyService anthologyService,
                                   IAuthorService authorService,
                                   GlobalConfiguration globalConfiguration) {
        this.anthologyService = anthologyService;
        this.authorService = authorService;
        this.globalConfiguration = globalConfiguration;
    }

    @Override
    public void exec(IExecutorRequest<CreateAnthologyRequestPayload> request,
                     IExecutorResponse<CreateAnthologyResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Create anthology for author: {}", securityContext.getUsername());
        CreateAnthologyRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getTitle())) {
            logger.error("Fail to create anthology because of title is empty.");
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_TITLE_IS_EMPTY);
        }
        if (requestPayload.getTitle().trim().length() > this.globalConfiguration.getAnthologyTitleMaxLength()) {
            logger.error("Fail to create anthology because of title length exceed.");
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_TITLE_IS_TOO_LONG);
        }
        Author currentAuthor = null;
        try {
            currentAuthor = this.authorService.findByUsername(securityContext.getUsername());
        } catch (Exception e) {
            logger.error("Fail to create anthology because of exception happen on search current author.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        if (currentAuthor == null) {
            logger.error("Fail to create anthology because of can not find current author [{}].",
                    securityContext.getUsername());
            throw new ExecutorException(ExecutorException.Code.CURRENT_AUTHOR_NOT_EXIST);
        }
        Anthology anthology = new Anthology();
        anthology.setAuthorId(currentAuthor.getId());
        anthology.setTitle(requestPayload.getTitle());
        anthology.setCreateDate(new Date());
        anthology.setSummary(requestPayload.getSummary());
        anthology.setPublish(requestPayload.isPublish());
        anthology.setTags(requestPayload.getTags());
        if (requestPayload.isPublish()) {
            anthology.setPublishDate(new Date());
        }
        try {
            logger.debug("Begin to save anthology: {}", anthology);
            this.anthologyService.save(anthology);
            logger.debug("Success to save anthology: {}", anthology.getId());
        } catch (Exception e) {
            logger.error("Fail to save anthology because of exception.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        currentAuthor.setAnthologyNumber(currentAuthor.getAnthologyNumber() + 1);
        try {
            this.authorService.save(currentAuthor);
        } catch (Exception e) {
            logger.error("Fail to save anthology because of exception when update anthology number for the author.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        CreateAnthologyResponsePayload createAnthologyResponsePayload = new CreateAnthologyResponsePayload();
        createAnthologyResponsePayload.setAnthologyId(anthology.getId());
        response.setPayload(createAnthologyResponsePayload);
    }
}
