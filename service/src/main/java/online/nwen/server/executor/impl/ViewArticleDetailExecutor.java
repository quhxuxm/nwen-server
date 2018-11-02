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
public class ViewArticleDetailExecutor implements
        IExecutor<ViewArticleDetailResponsePayload, ViewArticleDetailRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(ViewArticleDetailExecutor.class);
    private IArticleRepository articleRepository;
    private IAuthorRepository authorRepository;
    private IAnthologyRepository anthologyRepository;

    public ViewArticleDetailExecutor(IArticleRepository articleRepository,
                                     IAuthorRepository authorRepository,
                                     IAnthologyRepository anthologyRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.anthologyRepository = anthologyRepository;
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
        Author currentAuthor = null;
        if (securityContext != null) {
            try {
                currentAuthor = this.authorRepository.findByUsername(securityContext.getUsername());
            } catch (Exception e) {
                logger.error(
                        "Can not find current author for view article because of exception happen on search current author.",
                        e);
            }
            if (currentAuthor == null) {
                logger.error("Can not find current author view article because of can not find current author [{}].",
                        securityContext.getUsername());
            }
        }
        Optional<Article> targetArticleOptional = this.articleRepository.findById(requestPayload.getArticleId());
        if (!targetArticleOptional.isPresent()) {
            logger.error("Fail to view article detail because of article not exist, article id = [{}].",
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
        if (currentAuthor != null) {
            targetArticle.setViewersNumber(targetArticle.getViewersNumber() + 1);
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
        viewArticleDetailResponsePayload.setAuthorNickname(articleOwner.getNickname());
        viewArticleDetailResponsePayload.setAuthorId(articleOwner.getId());
        viewArticleDetailResponsePayload.setAuthorIconImageId(articleOwner.getIconImageId());
        viewArticleDetailResponsePayload.setAnthologyId(articleAnthology.getId());
        viewArticleDetailResponsePayload.setAnthologyCoverImageId(articleAnthology.getCoverImageId());
        viewArticleDetailResponsePayload.setAnthologyTitle(articleAnthology.getTitle());
        response.setPayload(viewArticleDetailResponsePayload);
    }
}
