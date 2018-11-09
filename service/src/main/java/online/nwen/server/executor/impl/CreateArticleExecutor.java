package online.nwen.server.executor.impl;

import online.nwen.server.configuration.GlobalConfiguration;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.CreateArticleRequestPayload;
import online.nwen.server.executor.api.payload.CreateArticleResponsePayload;
import online.nwen.server.service.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class CreateArticleExecutor
        extends AbstractArticleExecutor<CreateArticleResponsePayload, CreateArticleRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(CreateArticleExecutor.class);
    private IAuthorService authorService;
    private IArticleService articleService;
    private IAnthologyService anthologyService;
    private GlobalConfiguration globalConfiguration;
    private IArticleContentAnalyzeService articleContentAnalyzeService;

    public CreateArticleExecutor(IResourceService resourceService,
                                 IAuthorService authorService,
                                 IArticleService articleService,
                                 IAnthologyService anthologyService,
                                 GlobalConfiguration globalConfiguration,
                                 IArticleContentAnalyzeService articleContentAnalyzeService) {
        super(resourceService);
        this.authorService = authorService;
        this.articleService = articleService;
        this.anthologyService = anthologyService;
        this.globalConfiguration = globalConfiguration;
        this.articleContentAnalyzeService = articleContentAnalyzeService;
    }

    @Override
    public void exec(IExecutorRequest<CreateArticleRequestPayload> request,
                     IExecutorResponse<CreateArticleResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Create article for author: {}", securityContext.getAuthorId());
        CreateArticleRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getTitle())) {
            logger.error("Fail to create article because of title is empty.");
            throw new ExecutorException(ExecutorException.Code.ARTICLE_TITLE_IS_EMPTY);
        }
        if (requestPayload.getTitle().trim().length() > this.globalConfiguration.getArticleTitleMaxLength()) {
            logger.error("Fail to create article because of title length exceed.");
            throw new ExecutorException(ExecutorException.Code.ARTICLE_TITLE_IS_TOO_LONG);
        }
        Author currentAuthor = this.authorService.findById(securityContext.getAuthorId());
        if (currentAuthor == null) {
            logger.error("Fail to create article because of can not find current author [{}].",
                    securityContext.getAuthorId());
            throw new ExecutorException(ExecutorException.Code.CURRENT_AUTHOR_NOT_EXIST);
        }
        String targetAnthologyId = requestPayload.getAnthologyId();
        if (targetAnthologyId == null) {
            targetAnthologyId = currentAuthor.getDefaultAnthologyId();
            logger.debug("Use default anthology because the  anthology id in the request is empty, anthology id = {}.",
                    targetAnthologyId);
        }
        Anthology targetAnthology = this.anthologyService.findById(targetAnthologyId);
        if (targetAnthology == null) {
            logger.error("Fail to create article because of anthology is not exist.");
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_NOT_EXIST);
        }
        if (!targetAnthology.getAuthorId().equals(currentAuthor.getId())) {
            logger.debug(
                    "Current author is not the owner of the anthology, author is [{}], anthology is [{}].",
                    securityContext.getAuthorId(), targetAnthology.getId());
            if (!targetAnthology.getParticipantAuthorIds().contains(currentAuthor.getId())) {
                logger.error(
                        "Fail to create article because of author is not the participant of the anthology, author is [{}], anthology is [{}].",
                        securityContext.getAuthorId(), targetAnthology.getId());
                throw new ExecutorException(ExecutorException.Code.NOT_ANTHOLOGY_PARTICIPANT);
            }
        }
        Article article = new Article();
        article.setAnthologyId(targetAnthologyId);
        article.setAuthorId(currentAuthor.getId());
        article.setTitle(requestPayload.getTitle());
        ArticleContentAnalyzeRequest articleContentAnalyzeRequest = new ArticleContentAnalyzeRequest();
        articleContentAnalyzeRequest.setAuthorId(currentAuthor.getId());
        articleContentAnalyzeRequest.setContent(requestPayload.getContent());
        ArticleContentAnalyzeResponse articleContentAnalyzeResponse =
                this.articleContentAnalyzeService.analyze(articleContentAnalyzeRequest);
        article.setContent(articleContentAnalyzeResponse.getContent());
        article.setCreateDate(new Date());
        article.setSummary(requestPayload.getSummary());
        article.setTags(requestPayload.getTags());
        article.setAuthorConfirmedPublish(requestPayload.isPublished());
        if (requestPayload.isPublished()) {
            article.setAuthorConfirmedPublishDate(new Date());
        }
        this.articleService.save(article);
        String resourceSaveAuthorId = currentAuthor.getId();
        saveMediaResources(articleContentAnalyzeResponse, resourceSaveAuthorId);
        targetAnthology.setArticleNumber(targetAnthology.getArticleNumber() + 1);
        targetAnthology.setUpdateDate(new Date());
        this.anthologyService.save(targetAnthology);
        currentAuthor.setArticleNumber(currentAuthor.getArticleNumber() + 1);
        this.authorService.save(currentAuthor);
        CreateArticleResponsePayload createArticleResponsePayload = new CreateArticleResponsePayload();
        createArticleResponsePayload.setArticleId(article.getId());
        createArticleResponsePayload.setAnthologyId(targetAnthologyId);
        response.setPayload(createArticleResponsePayload);
    }
}
