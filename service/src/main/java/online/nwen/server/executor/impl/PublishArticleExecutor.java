package online.nwen.server.executor.impl;

import online.nwen.server.domain.Article;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.PublishArticleRequestPayload;
import online.nwen.server.executor.api.payload.PublishArticleResponsePayload;
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
public class PublishArticleExecutor implements IExecutor<PublishArticleResponsePayload, PublishArticleRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(PublishArticleExecutor.class);
    private IAuthorRepository authorRepository;
    private IArticleRepository articleRepository;

    public PublishArticleExecutor(IAuthorRepository authorRepository,
                                  IArticleRepository articleRepository
    ) {
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public void exec(IExecutorRequest<PublishArticleRequestPayload> request,
                     IExecutorResponse<PublishArticleResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Publish article for author: {}", securityContext.getUsername());
        PublishArticleRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getArticleId())) {
            logger.error("Fail to publish article because of article id is empty.");
            throw new ExecutorException(ExecutorException.Code.ARTICLE_ID_IS_EMPTY);
        }
        Author currentAuthor = null;
        try {
            currentAuthor = this.authorRepository.findByUsername(securityContext.getUsername());
        } catch (Exception e) {
            logger.error("Fail to publish article because of exception happen on search current author.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        if (currentAuthor == null) {
            logger.error("Fail to publish article because of can not find current author [{}].",
                    securityContext.getUsername());
            throw new ExecutorException(ExecutorException.Code.CURRENT_AUTHOR_NOT_EXIST);
        }
        Optional<Article> targetArticleOptional = this.articleRepository.findById(requestPayload.getArticleId());
        if (!targetArticleOptional.isPresent()) {
            logger.error("Fail to publish article because of article not exist, article id = [{}].",
                    requestPayload.getArticleId());
            throw new ExecutorException(ExecutorException.Code.ARTICLE_NOT_EXIST);
        }
        Article targetArticle = targetArticleOptional.get();
        if (!targetArticle.getAuthorId().equals(currentAuthor.getId())) {
            logger.error(
                    "Fail to publish article because of author is not the owner, author is [{}], article is [{}].",
                    securityContext.getUsername(), targetArticle.getId());
            throw new ExecutorException(ExecutorException.Code.NOT_ARTICLE_OWNER);
        }
        if (targetArticle.isPublish() != requestPayload.isPublish()) {
            targetArticle.setUpdateDate(new Date());
            if (requestPayload.isPublish()) {
                targetArticle.setPublishDate(new Date());
            }
        }
        targetArticle.setPublish(requestPayload.isPublish());
        try {
            this.articleRepository.save(targetArticle);
        } catch (Exception e) {
            logger.error("Fail to create article because of exception.", e);
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        PublishArticleResponsePayload publishArticleResponsePayload = new PublishArticleResponsePayload();
        publishArticleResponsePayload.setArticleId(targetArticle.getId());
        response.setPayload(publishArticleResponsePayload);
    }
}
