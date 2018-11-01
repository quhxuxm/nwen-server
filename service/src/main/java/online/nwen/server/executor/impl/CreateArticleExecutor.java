package online.nwen.server.executor.impl;

import online.nwen.server.domain.Article;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.CreateArticleRequestPayload;
import online.nwen.server.executor.api.payload.CreateArticleResponsePayload;
import online.nwen.server.repository.IArticleRepository;
import online.nwen.server.repository.IAuthorRepository;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CreateArticleExecutor implements IExecutor<CreateArticleResponsePayload, CreateArticleRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(CreateArticleExecutor.class);
    private IAuthorRepository authorRepository;
    private IArticleRepository articleRepository;

    public CreateArticleExecutor(IAuthorRepository authorRepository,
                                 IArticleRepository articleRepository) {
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public void exec(IExecutorRequest<CreateArticleRequestPayload> request,
                     IExecutorResponse<CreateArticleResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Create article for author: {}", securityContext.getUsername());
        Author currentAuthor = null;
        try {
            currentAuthor = this.authorRepository.findByUsername(securityContext.getUsername());
        } catch (Exception e) {
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        if (currentAuthor == null) {
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        CreateArticleRequestPayload requestPayload = request.getPayload();
        Article article = new Article();
        article.setAnthologyId(requestPayload.getAnthologyId());
        article.setAuthorId(currentAuthor.getId());
        article.setTitle(requestPayload.getTitle());
        article.setContent(requestPayload.getContent());
        article.setCreateDate(new Date());
        article.setSummary(requestPayload.getSummary());
        article.setTags(requestPayload.getTags());
        try {
            this.articleRepository.save(article);
        } catch (Exception e) {
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        CreateArticleResponsePayload createArticleResponsePayload = new CreateArticleResponsePayload();
        createArticleResponsePayload.setArticleId(article.getId());
        response.setPayload(createArticleResponsePayload);
    }
}
