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
        logger.debug("Create anthology for author: {}", securityContext.getAuthorId());
        CreateAnthologyRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getTitle())) {
            logger.error("Fail to create anthology because of title is empty.");
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_TITLE_IS_EMPTY);
        }
        if (requestPayload.getTitle().trim().length() > this.globalConfiguration.getAnthologyTitleMaxLength()) {
            logger.error("Fail to create anthology because of title length exceed.");
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_TITLE_IS_TOO_LONG);
        }
        Author currentAuthor = this.authorService.findById(securityContext.getAuthorId());
        if (currentAuthor == null) {
            logger.error("Fail to create anthology because of can not find current author [{}].",
                    securityContext.getAuthorId());
            throw new ExecutorException(ExecutorException.Code.CURRENT_AUTHOR_NOT_EXIST);
        }
        Anthology anthology = new Anthology();
        anthology.setAuthorId(currentAuthor.getId());
        anthology.setTitle(requestPayload.getTitle());
        anthology.setCreateDate(new Date());
        anthology.setSummary(requestPayload.getSummary());
        anthology.setAuthorConfirmedPublish(requestPayload.isPublish());
        anthology.setTags(requestPayload.getTags());
        if (requestPayload.isPublish()) {
            anthology.setAuthorConfirmedPublishDate(new Date());
        }
        logger.debug("Begin to save anthology: {}", anthology);
        this.anthologyService.save(anthology);
        logger.debug("Success to save anthology: {}", anthology.getId());
        currentAuthor.setAnthologyNumber(currentAuthor.getAnthologyNumber() + 1);
        this.authorService.save(currentAuthor);
        CreateAnthologyResponsePayload createAnthologyResponsePayload = new CreateAnthologyResponsePayload();
        createAnthologyResponsePayload.setAnthologyId(anthology.getId());
        response.setPayload(createAnthologyResponsePayload);
    }
}
