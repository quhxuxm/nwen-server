package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.ViewArticleSummariesRequestPayload;
import online.nwen.server.executor.api.payload.ViewArticleSummariesResponsePayload;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.IArticleService;
import online.nwen.server.service.api.IAuthorService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ViewArticleSummariesExecutor implements
        IExecutor<ViewArticleSummariesResponsePayload, ViewArticleSummariesRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(ViewArticleSummariesExecutor.class);
    private IArticleService articleService;
    private IAuthorService authorService;
    private IAnthologyService anthologyService;

    public ViewArticleSummariesExecutor(IArticleService articleService,
                                        IAuthorService authorService,
                                        IAnthologyService anthologyService) {
        this.articleService = articleService;
        this.authorService = authorService;
        this.anthologyService = anthologyService;
    }

    @Override
    public void exec(IExecutorRequest<ViewArticleSummariesRequestPayload> request,
                     IExecutorResponse<ViewArticleSummariesResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Begin to view article summary.");
        ViewArticleSummariesResponsePayload viewArticleSummaryResponsePayload =
                new ViewArticleSummariesResponsePayload();
        response.setPayload(viewArticleSummaryResponsePayload);
        ViewArticleSummariesRequestPayload requestPayload = request.getPayload();
        if (requestPayload.getIds().isEmpty()) {
            return;
        }
        for (String articleId : request.getPayload().getIds()) {
            if (StringUtils.isEmpty(articleId)) {
                logger.error("Fail to view article summary because of article id is empty.");
                continue;
            }
            boolean currentAuthorIsOwner = false;
            if (securityContext != null) {
                currentAuthorIsOwner =
                        this.articleService.isOwner(securityContext.getAuthorId(), articleId);
            }
            Article targetArticle = null;
            if (currentAuthorIsOwner) {
                targetArticle = this.articleService.findById(articleId);
            } else {
                targetArticle = this.articleService.findByIdAndSystemConfirmedPublish(articleId, false);
            }
            if (targetArticle == null) {
                logger.error("Fail to view article summary because of article not exist, article id = [{}].",
                        articleId);
                continue;
            }
            Author articleOwner = this.authorService.findById(targetArticle.getAuthorId());
            if (articleOwner == null) {
                throw new ExecutorException(ExecutorException.Code.ARTICLE_AUTHOR_NOT_EXIST);
            }
            Anthology articleAnthology =
                    this.anthologyService.findById(targetArticle.getAnthologyId());
            if (articleAnthology == null) {
                throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_NOT_EXIST);
            }
            ViewArticleSummariesResponsePayload.ArticleSummary summary =
                    new ViewArticleSummariesResponsePayload.ArticleSummary();
            summary.setTitle(targetArticle.getTitle());
            summary.setId(targetArticle.getId());
            summary.setSummary(targetArticle.getSummary());
            summary.setBookmarksNumber(targetArticle.getBookmarksNumber());
            summary.setCommentNumber(targetArticle.getCommentNumber());
            summary.setViewersNumber(targetArticle.getViewersNumber());
            summary.setCreateDate(targetArticle.getCreateDate());
            summary.setAuthorNickname(articleOwner.getNickname());
            summary.setAuthorId(articleOwner.getId());
            summary.setAuthorIconImageId(articleOwner.getIconImageId());
            summary.setAnthologyId(articleAnthology.getId());
            summary.setAnthologyCoverImageId(articleAnthology.getCoverImageId());
            summary.setAnthologyTitle(articleAnthology.getTitle());
            summary.setPublish(targetArticle.isAuthorConfirmedPublish());
            summary.setPublishDate(targetArticle.getAuthorConfirmedPublishDate());
            summary.setUpdateDate(targetArticle.getUpdateDate());
            summary.setPraisesNumber(targetArticle.getPraisesNumber());
            viewArticleSummaryResponsePayload.getSummaries().add(summary);
        }
    }
}
