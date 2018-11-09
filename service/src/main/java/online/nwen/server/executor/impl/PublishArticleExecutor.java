package online.nwen.server.executor.impl;

import online.nwen.server.domain.Article;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.PublishArticleRequestPayload;
import online.nwen.server.executor.api.payload.PublishArticleResponsePayload;
import online.nwen.server.service.api.IArticleService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class PublishArticleExecutor implements IExecutor<PublishArticleResponsePayload, PublishArticleRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(PublishArticleExecutor.class);
    private IArticleService articleService;

    public PublishArticleExecutor(
            IArticleService articleService
    ) {
        this.articleService = articleService;
    }

    @Override
    public void exec(IExecutorRequest<PublishArticleRequestPayload> request,
                     IExecutorResponse<PublishArticleResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Publish article for author: {}", securityContext.getAuthorId());
        PublishArticleRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getArticleId())) {
            logger.error("Fail to publish article because of article id is empty.");
            throw new ExecutorException(ExecutorException.Code.ARTICLE_ID_IS_EMPTY);
        }
        Article targetArticle = this.articleService.findById(requestPayload.getArticleId());
        if (targetArticle == null) {
            logger.error("Fail to publish article because of article not exist, article id = [{}].",
                    requestPayload.getArticleId());
            throw new ExecutorException(ExecutorException.Code.ARTICLE_NOT_EXIST);
        }
        if (!targetArticle.getAuthorId().equals(securityContext.getAuthorId())) {
            logger.error(
                    "Fail to publish article because of author is not the owner, author is [{}], article is [{}].",
                    securityContext.getAuthorId(), targetArticle.getId());
            throw new ExecutorException(ExecutorException.Code.NOT_ARTICLE_OWNER);
        }
        if (targetArticle.isAuthorConfirmedPublish() != requestPayload.isPublish()) {
            targetArticle.setUpdateDate(new Date());
            if (requestPayload.isPublish()) {
                targetArticle.setAuthorConfirmedPublishDate(new Date());
            }
        }
        targetArticle.setAuthorConfirmedPublish(requestPayload.isPublish());
        this.articleService.save(targetArticle);
        this.articleService.systemPublishArticle(targetArticle);
        PublishArticleResponsePayload publishArticleResponsePayload = new PublishArticleResponsePayload();
        publishArticleResponsePayload.setArticleId(targetArticle.getId());
        response.setPayload(publishArticleResponsePayload);
    }
}
