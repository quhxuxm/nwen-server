package online.nwen.server.executor.impl;

import online.nwen.server.configuration.GlobalConfiguration;
import online.nwen.server.domain.Article;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.UpdateArticleRequestPayload;
import online.nwen.server.executor.api.payload.UpdateArticleResponsePayload;
import online.nwen.server.service.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class UpdateArticleExecutor
        extends AbstractArticleExecutor<UpdateArticleResponsePayload, UpdateArticleRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(UpdateArticleExecutor.class);
    private IAuthorService authorService;
    private IArticleService articleService;
    private GlobalConfiguration globalConfiguration;
    private IArticleContentAnalyzeService articleContentAnalyzeService;

    public UpdateArticleExecutor(IResourceService resourceService,
                                 IAuthorService authorService,
                                 IArticleService articleService,
                                 GlobalConfiguration globalConfiguration,
                                 IArticleContentAnalyzeService articleContentAnalyzeService) {
        super(resourceService);
        this.authorService = authorService;
        this.articleService = articleService;
        this.globalConfiguration = globalConfiguration;
        this.articleContentAnalyzeService = articleContentAnalyzeService;
    }

    @Override
    public void exec(IExecutorRequest<UpdateArticleRequestPayload> request,
                     IExecutorResponse<UpdateArticleResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Update article for author: {}", securityContext.getAuthorId());
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
        Article targetArticle = this.articleService.findById(requestPayload.getArticleId());
        if (targetArticle == null) {
            logger.error("Fail to update article because of article not exist, article id = [{}].",
                    requestPayload.getArticleId());
            throw new ExecutorException(ExecutorException.Code.ARTICLE_NOT_EXIST);
        }
        if (!targetArticle.getAuthorId().equals(securityContext.getAuthorId())) {
            logger.error(
                    "Fail to update article because of author is not the owner, author is [{}], article is [{}].",
                    securityContext.getAuthorId(), targetArticle.getId());
            throw new ExecutorException(ExecutorException.Code.NOT_ARTICLE_OWNER);
        }
        targetArticle.setTitle(requestPayload.getTitle());
        ArticleContentAnalyzeRequest articleContentAnalyzeRequest = new ArticleContentAnalyzeRequest();
        articleContentAnalyzeRequest.setAuthorId(securityContext.getAuthorId());
        articleContentAnalyzeRequest.setContent(requestPayload.getContent());
        ArticleContentAnalyzeResponse articleContentAnalyzeResponse =
                this.articleContentAnalyzeService.analyze(articleContentAnalyzeRequest);
        targetArticle.setContent(articleContentAnalyzeResponse.getContent());
        targetArticle.setUpdateDate(new Date());
        targetArticle.setSummary(requestPayload.getSummary());
        targetArticle.setTags(requestPayload.getTags());
        this.articleService.save(targetArticle);
        String resourceSaveAuthorId = securityContext.getAuthorId();
        saveMediaResources(articleContentAnalyzeResponse, resourceSaveAuthorId);
        UpdateArticleResponsePayload updateArticleResponsePayload = new UpdateArticleResponsePayload();
        updateArticleResponsePayload.setArticleId(targetArticle.getId());
        response.setPayload(updateArticleResponsePayload);
    }
}
