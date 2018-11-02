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
import online.nwen.server.repository.IAnthologyRepository;
import online.nwen.server.repository.IArticleRepository;
import online.nwen.server.repository.IAuthorRepository;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class ViewArticleSummaryExecutor implements
        IExecutor<ViewArticleSummaryResponsePayload, ViewArticleSummaryRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(ViewArticleSummaryExecutor.class);
    private IArticleRepository articleRepository;
    private IAuthorRepository authorRepository;
    private IAnthologyRepository anthologyRepository;

    public ViewArticleSummaryExecutor(IArticleRepository articleRepository,
                                      IAuthorRepository authorRepository,
                                      IAnthologyRepository anthologyRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.anthologyRepository = anthologyRepository;
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
        Optional<Article> targetArticleOptional = this.articleRepository.findById(requestPayload.getArticleId());
        if (!targetArticleOptional.isPresent()) {
            logger.error("Fail to view article summary because of article not exist, article id = [{}].",
                    requestPayload.getArticleId());
            throw new ExecutorException(ExecutorException.Code.ARTICLE_NOT_EXIST);
        }
        Article targetArticle = targetArticleOptional.get();
        Optional<Author> articleOwnerOptional = this.authorRepository.findById(targetArticle.getAuthorId());
        if (!articleOwnerOptional.isPresent()) {
            throw new ExecutorException(ExecutorException.Code.ARTICLE_AUTHOR_NOT_EXIST);
        }
        Author articleOwner = articleOwnerOptional.get();
        Optional<Anthology> articleAnthologyOptional =
                this.anthologyRepository.findById(targetArticle.getAnthologyId());
        if (!articleAnthologyOptional.isPresent()) {
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_NOT_EXIST);
        }
        Anthology articleAnthology = articleAnthologyOptional.get();
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
        viewArticleSummaryResponsePayload.setPublish(targetArticle.isPublish());
        viewArticleSummaryResponsePayload.setPublishDate(targetArticle.getPublishDate());
        viewArticleSummaryResponsePayload.setUpdateDate(targetArticle.getUpdateDate());
        viewArticleSummaryResponsePayload.setPraisesNumber(targetArticle.getPraisesNumber());
        response.setPayload(viewArticleSummaryResponsePayload);
    }
}
