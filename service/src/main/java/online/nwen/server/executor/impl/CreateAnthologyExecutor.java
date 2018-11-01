package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.CreateAnthologyRequestPayload;
import online.nwen.server.executor.api.payload.CreateAnthologyResponsePayload;
import online.nwen.server.repository.IAnthologyRepository;
import online.nwen.server.repository.IAuthorRepository;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CreateAnthologyExecutor
        implements IExecutor<CreateAnthologyResponsePayload, CreateAnthologyRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(CreateAnthologyExecutor.class);
    private IAnthologyRepository anthologyRepository;
    private IAuthorRepository authorRepository;

    public CreateAnthologyExecutor(IAnthologyRepository anthologyRepository,
                                   IAuthorRepository authorRepository) {
        this.anthologyRepository = anthologyRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void exec(IExecutorRequest<CreateAnthologyRequestPayload> request,
                     IExecutorResponse<CreateAnthologyResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Create anthology for author: {}", securityContext.getUsername());
        Author currentAuthor = null;
        try {
            currentAuthor = this.authorRepository.findByUsername(securityContext.getUsername());
        } catch (Exception e) {
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        if (currentAuthor == null) {
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        CreateAnthologyRequestPayload requestPayload = request.getPayload();
        Anthology anthology = new Anthology();
        anthology.setAuthorId(currentAuthor.getId());
        anthology.setTitle(requestPayload.getTitle());
        anthology.setCreateDate(new Date());
        anthology.setSummary(requestPayload.getSummary());
        anthology.setPublished(requestPayload.isPublish());
        try {
            this.anthologyRepository.save(anthology);
        } catch (Exception e) {
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        CreateAnthologyResponsePayload createAnthologyResponsePayload = new CreateAnthologyResponsePayload();
        createAnthologyResponsePayload.setAnthologyId(anthology.getId());
        response.setPayload(createAnthologyResponsePayload);
    }
}
