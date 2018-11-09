package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.ViewArticleDetailRequestPayload;
import online.nwen.server.executor.api.payload.ViewArticleDetailResponsePayload;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.IArticleService;
import online.nwen.server.service.api.IAuthorService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ViewArticleDetailExecutor implements
        IExecutor<ViewArticleDetailResponsePayload, ViewArticleDetailRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(ViewArticleDetailExecutor.class);
    private IArticleService articleService;
    private IAuthorService authorService;
    private IAnthologyService anthologyService;

    public ViewArticleDetailExecutor(IArticleService articleService,
                                     IAuthorService authorService,
                                     IAnthologyService anthologyService) {
        this.articleService = articleService;
        this.authorService = authorService;
        this.anthologyService = anthologyService;
    }

    @Override
    public void exec(IExecutorRequest<ViewArticleDetailRequestPayload> request,
                     IExecutorResponse<ViewArticleDetailResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Begin to view article detail.");
        ViewArticleDetailRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getArticleId())) {
            logger.error("Fail to view article detail because of article id is empty.");
            throw new ExecutorException(ExecutorException.Code.ARTICLE_ID_IS_EMPTY);
        }
        boolean currentAuthorIsOwner = false;
        if (securityContext != null) {
            currentAuthorIsOwner =
                    this.articleService.isOwner(securityContext.getAuthorId(), requestPayload.getArticleId());
        }
        logger.debug("Current author id the owner of the article: {}", currentAuthorIsOwner);
        Article targetArticle = null;
        if (currentAuthorIsOwner) {
            logger.debug(
                    "Current author id the owner of the article, search ignore the system confirmed publish status.");
            targetArticle = this.articleService.findById(requestPayload.getArticleId());
        } else {
            logger.debug(
                    "Current author id the owner of the article, search with the system confirmed publish status.");
            targetArticle = this.articleService.findByIdAndSystemConfirmedPublish(requestPayload.getArticleId(), true);
        }
        if (targetArticle == null) {
            logger.error("Fail to view article detail because of article not exist, article id = [{}].",
                    requestPayload.getArticleId());
            throw new ExecutorException(ExecutorException.Code.ARTICLE_NOT_EXIST);
        }
        Anthology articleAnthology =
                this.anthologyService.findByIdAndSystemConfirmedPublish(targetArticle.getAnthologyId(), true);
        if (articleAnthology == null) {
            logger.error("Fail to view article detail because of can not find anthology [{}].",
                    targetArticle.getAnthologyId());
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_NOT_EXIST);
        }
        if (securityContext != null) {
            targetArticle.setViewersNumber(targetArticle.getViewersNumber() + 1);
            this.articleService.save(targetArticle);
        }
        Author articleAuthor = this.authorService.findById(targetArticle.getAuthorId());
        if (articleAuthor == null) {
            logger.error("Fail to view article detail because of can not find author [{}].",
                    targetArticle.getAuthorId());
            throw new ExecutorException(ExecutorException.Code.AUTHOR_NOT_EXIST);
        }
        ViewArticleDetailResponsePayload viewArticleDetailResponsePayload = new ViewArticleDetailResponsePayload();
        viewArticleDetailResponsePayload.setTitle(targetArticle.getTitle());
        viewArticleDetailResponsePayload.setId(targetArticle.getId());
        viewArticleDetailResponsePayload.setContent(targetArticle.getContent());
        viewArticleDetailResponsePayload.setSummary(targetArticle.getSummary());
        viewArticleDetailResponsePayload.setBookmarksNumber(targetArticle.getBookmarksNumber());
        viewArticleDetailResponsePayload.setCommentNumber(targetArticle.getCommentNumber());
        viewArticleDetailResponsePayload.setViewersNumber(targetArticle.getViewersNumber());
        viewArticleDetailResponsePayload.setCreateDate(targetArticle.getCreateDate());
        viewArticleDetailResponsePayload.setAuthorNickname(articleAuthor.getNickname());
        viewArticleDetailResponsePayload.setAuthorId(articleAuthor.getId());
        viewArticleDetailResponsePayload.setAuthorIconImageId(articleAuthor.getIconImageId());
        viewArticleDetailResponsePayload.setAnthologyId(articleAnthology.getId());
        viewArticleDetailResponsePayload.setAnthologyCoverImageId(articleAnthology.getCoverImageId());
        viewArticleDetailResponsePayload.setAnthologyTitle(articleAnthology.getTitle());
        viewArticleDetailResponsePayload.setAuthorConfirmedPublish(targetArticle.isAuthorConfirmedPublish());
        viewArticleDetailResponsePayload.setAuthorConfirmedPublishDate(targetArticle.getAuthorConfirmedPublishDate());
        viewArticleDetailResponsePayload.setSystemConfirmedPublish(targetArticle.isSystemConfirmedPublish());
        viewArticleDetailResponsePayload.setSystemConfirmedPublishDate(targetArticle.getSystemConfirmedPublishDate());
        viewArticleDetailResponsePayload.setUpdateDate(targetArticle.getUpdateDate());
        viewArticleDetailResponsePayload.setPraisesNumber(targetArticle.getPraisesNumber());
        response.setPayload(viewArticleDetailResponsePayload);
    }
}
