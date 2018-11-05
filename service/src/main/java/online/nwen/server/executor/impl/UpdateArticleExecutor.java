package online.nwen.server.executor.impl;

import online.nwen.server.configuration.GlobalConfiguration;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.UpdateArticleRequestPayload;
import online.nwen.server.executor.api.payload.UpdateArticleResponsePayload;
import online.nwen.server.repository.IArticleRepository;
import online.nwen.server.repository.IAuthorRepository;
import online.nwen.server.repository.IResourceRepository;
import online.nwen.server.service.api.ArticleContentAnalyzeRequest;
import online.nwen.server.service.api.ArticleContentAnalyzeResponse;
import online.nwen.server.service.api.IArticleContentAnalyzeService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

@Service
public class UpdateArticleExecutor
        extends AbstractArticleExecutor<UpdateArticleResponsePayload, UpdateArticleRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(UpdateArticleExecutor.class);
    private IAuthorRepository authorRepository;
    private IArticleRepository articleRepository;
    private GlobalConfiguration globalConfiguration;
    private IArticleContentAnalyzeService articleContentAnalyzeService;

    public UpdateArticleExecutor(IResourceRepository resourceRepository,
                                 IAuthorRepository authorRepository,
                                 IArticleRepository articleRepository,
                                 GlobalConfiguration globalConfiguration,
                                 IArticleContentAnalyzeService articleContentAnalyzeService) {
        super(resourceRepository);
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
        this.globalConfiguration = globalConfiguration;
        this.articleContentAnalyzeService = articleContentAnalyzeService;
    }

    @Override
    public void exec(IExecutorRequest<UpdateArticleRequestPayload> request,
                     IExecutorResponse<UpdateArticleResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Update article for author: {}", securityContext.getUsername());
        UpdateArticleRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getArticleId())) {
            logger.error("Fail to update article because of article id is empty.");
            throw new ExecutorException(ExecutorException.Code.ARTICLE_ID_IS_EMPTY);
        }
        if (StringUtils.isEmpty(requestPayload.getTitle())) {
            logger.error("Fail to update article because of title is empty.");
            throw new ExecutorException(ExecutorException.Code.ARTICLE_TITLE_IS_EMPTY);
        }
        if (requestPayload.getTitle().trim().length() > this.globalConfiguration.getArticleTitleMaxLength()) {
            logger.error("Fail to update article because of title length exceed.");
            throw new ExecutorException(ExecutorException.Code.ARTICLE_TITLE_IS_TOO_LONG);
        }
        Author currentAuthor = null;
        try {
            currentAuthor = this.authorRepository.findByUsername(securityContext.getUsername());
        } catch (Exception e) {
            logger.error("Fail to update article because of exception happen on search current author.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        if (currentAuthor == null) {
            logger.error("Fail to update article because of can not find current author [{}].",
                    securityContext.getUsername());
            throw new ExecutorException(ExecutorException.Code.CURRENT_AUTHOR_NOT_EXIST);
        }
        Optional<Article> targetArticleOptional = this.articleRepository.findById(requestPayload.getArticleId());
        if (!targetArticleOptional.isPresent()) {
            logger.error("Fail to update article because of article not exist, article id = [{}].",
                    requestPayload.getArticleId());
            throw new ExecutorException(ExecutorException.Code.ARTICLE_NOT_EXIST);
        }
        Article targetArticle = targetArticleOptional.get();
        if (!targetArticle.getAuthorId().equals(currentAuthor.getId())) {
            logger.error(
                    "Fail to update article because of author is not the owner, author is [{}], article is [{}].",
                    securityContext.getUsername(), targetArticle.getId());
            throw new ExecutorException(ExecutorException.Code.NOT_ARTICLE_OWNER);
        }
        targetArticle.setTitle(requestPayload.getTitle());
        ArticleContentAnalyzeRequest articleContentAnalyzeRequest = new ArticleContentAnalyzeRequest();
        articleContentAnalyzeRequest.setAuthorId(currentAuthor.getId());
        articleContentAnalyzeRequest.setContent(requestPayload.getContent());
        ArticleContentAnalyzeResponse articleContentAnalyzeResponse =
                this.articleContentAnalyzeService.analyze(articleContentAnalyzeRequest);
        targetArticle.setContent(articleContentAnalyzeResponse.getContent());
        targetArticle.setUpdateDate(new Date());
        targetArticle.setSummary(requestPayload.getSummary());
        targetArticle.setTags(requestPayload.getTags());
        try {
            this.articleRepository.save(targetArticle);
        } catch (Exception e) {
            logger.error("Fail to update article because of exception.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        String resourceSaveAuthorId = currentAuthor.getId();
        saveMediaResources(articleContentAnalyzeResponse, resourceSaveAuthorId);
        UpdateArticleResponsePayload updateArticleResponsePayload = new UpdateArticleResponsePayload();
        updateArticleResponsePayload.setArticleId(targetArticle.getId());
        response.setPayload(updateArticleResponsePayload);
    }
}
