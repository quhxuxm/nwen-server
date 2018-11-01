package online.nwen.server.executor.impl;

import online.nwen.server.configuration.GlobalConfiguration;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.CreateArticleRequestPayload;
import online.nwen.server.executor.api.payload.CreateArticleResponsePayload;
import online.nwen.server.repository.IAnthologyRepository;
import online.nwen.server.repository.IArticleRepository;
import online.nwen.server.repository.IAuthorRepository;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

@Service
public class CreateArticleExecutor implements IExecutor<CreateArticleResponsePayload, CreateArticleRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(CreateArticleExecutor.class);
    private IAuthorRepository authorRepository;
    private IArticleRepository articleRepository;
    private IAnthologyRepository anthologyRepository;
    private GlobalConfiguration globalConfiguration;

    public CreateArticleExecutor(IAuthorRepository authorRepository,
                                 IArticleRepository articleRepository,
                                 IAnthologyRepository anthologyRepository,
                                 GlobalConfiguration globalConfiguration) {
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
        this.anthologyRepository = anthologyRepository;
        this.globalConfiguration = globalConfiguration;
    }

    @Override
    public void exec(IExecutorRequest<CreateArticleRequestPayload> request,
                     IExecutorResponse<CreateArticleResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Create article for author: {}", securityContext.getUsername());
        CreateArticleRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getTitle())) {
            logger.error("Fail to create article because of title is empty.");
            throw new ExecutorException(ExecutorException.Code.ARTICLE_TITLE_IS_EMPTY);
        }
        if (requestPayload.getTitle().trim().length() > this.globalConfiguration.getArticleTitleMaxLength()) {
            logger.error("Fail to create article because of title length exceed.");
            throw new ExecutorException(ExecutorException.Code.ARTICLE_TITLE_IS_TOO_LONG);
        }
        Author currentAuthor = null;
        try {
            currentAuthor = this.authorRepository.findByUsername(securityContext.getUsername());
        } catch (Exception e) {
            logger.error("Fail to create article because of exception happen on search current author.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        if (currentAuthor == null) {
            logger.error("Fail to create article because of can not find current author [{}].",
                    securityContext.getUsername());
            throw new ExecutorException(ExecutorException.Code.CURRENT_AUTHOR_NOT_EXIST);
        }
        if (requestPayload.getAnthologyId() == null) {
            logger.error("Fail to create article because of anthology id is empty.");
            throw new ExecutorException(ExecutorException.Code.CREATE_ARTICLE_ANTHOLOGY_ID_IS_EMPTY);
        }
        Optional<Anthology> targetAnthologyOptional =
                this.anthologyRepository.findById(requestPayload.getAnthologyId());
        if (!targetAnthologyOptional.isPresent()) {
            logger.error("Fail to create article because of anthology is not exist.");
            throw new ExecutorException(ExecutorException.Code.CREATE_ARTICLE_ANTHOLOGY_NOT_EXIST);
        }
        if (!currentAuthor.getId().equals(targetAnthologyOptional.get().getAuthorId())) {
            logger.error("Fail to create article because of anthology not belong to author [{}].",
                    securityContext.getUsername());
            throw new ExecutorException(ExecutorException.Code.CREATE_ARTICLE_ANTHOLOGY_NOT_BELONG_TO_AUTHOR);
        }
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
            logger.error("Fail to create article because of exception.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        CreateArticleResponsePayload createArticleResponsePayload = new CreateArticleResponsePayload();
        createArticleResponsePayload.setArticleId(article.getId());
        response.setPayload(createArticleResponsePayload);
    }
}
