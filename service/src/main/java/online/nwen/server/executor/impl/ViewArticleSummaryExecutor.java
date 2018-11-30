package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.ViewArticleSummaryRequestPayload;
import online.nwen.server.executor.api.payload.ViewArticleSummaryResponsePayload;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.IArticleService;
import online.nwen.server.service.api.IAuthorService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ViewArticleSummaryExecutor implements
        IExecutor<ViewArticleSummaryResponsePayload, ViewArticleSummaryRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(ViewArticleSummaryExecutor.class);
    private IArticleService articleService;
    private IAuthorService authorService;
    private IAnthologyService anthologyService;

    public ViewArticleSummaryExecutor(IArticleService articleService,
                                      IAuthorService authorService,
                                      IAnthologyService anthologyService) {
        this.articleService = articleService;
        this.authorService = authorService;
        this.anthologyService = anthologyService;
    }

    @Override
    public void exec(IExecutorRequest<ViewArticleSummaryRequestPayload> request,
                     IExecutorResponse<ViewArticleSummaryResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Begin to view article summary.");
        ViewArticleSummaryRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getArticleId())) {
            logger.error("Fail to view article summary because of article id is empty.");
            throw new ExecutorException(ExecutorException.Code.ARTICLE_ID_IS_EMPTY);
        }
        boolean currentAuthorIsOwner = false;
        if (securityContext != null) {
            currentAuthorIsOwner =
                    this.articleService.isOwner(securityContext.getAuthorId(), requestPayload.getArticleId());
        }
        Article targetArticle = null;
        if (currentAuthorIsOwner) {
            targetArticle = this.articleService.findById(requestPayload.getArticleId());
        } else {
            targetArticle = this.articleService.findByIdAndSystemConfirmedPublish(requestPayload.getArticleId(), false);
        }
        if (targetArticle == null) {
            logger.error("Fail to view article summary because of article not exist, article id = [{}].",
                    requestPayload.getArticleId());
            throw new ExecutorException(ExecutorException.Code.ARTICLE_NOT_EXIST);
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
        ViewArticleSummaryResponsePayload viewArticleSummaryResponsePayload = new ViewArticleSummaryResponsePayload();
        viewArticleSummaryResponsePayload.setTitle(targetArticle.getTitle());
        viewArticleSummaryResponsePayload.setId(targetArticle.getId());
        viewArticleSummaryResponsePayload.setSummary(targetArticle.getSummary());
        viewArticleSummaryResponsePayload.setBookmarksNumber(targetArticle.getBookmarksNumber());
        viewArticleSummaryResponsePayload.setCommentNumber(targetArticle.getCommentNumber());
        viewArticleSummaryResponsePayload.setViewersNumber(targetArticle.getViewersNumber());
        viewArticleSummaryResponsePayload.setCreateDate(targetArticle.getCreateDate());
        viewArticleSummaryResponsePayload.setAuthorNickname(articleOwner.getNickname());
        viewArticleSummaryResponsePayload.setAuthorId(articleOwner.getId());
        viewArticleSummaryResponsePayload.setAuthorIconImageId(articleOwner.getIconImageId());
        viewArticleSummaryResponsePayload.setAnthologyId(articleAnthology.getId());
        viewArticleSummaryResponsePayload.setAnthologyCoverImageId(articleAnthology.getCoverImageId());
        viewArticleSummaryResponsePayload.setAnthologyTitle(articleAnthology.getTitle());
        viewArticleSummaryResponsePayload.setPublish(targetArticle.isAuthorConfirmedPublish());
        viewArticleSummaryResponsePayload.setPublishDate(targetArticle.getAuthorConfirmedPublishDate());
        viewArticleSummaryResponsePayload.setUpdateDate(targetArticle.getUpdateDate());
        viewArticleSummaryResponsePayload.setPraisesNumber(targetArticle.getPraisesNumber());
        response.setPayload(viewArticleSummaryResponsePayload);
    }
}
