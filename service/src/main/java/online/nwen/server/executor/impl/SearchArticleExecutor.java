package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.SearchArticleRequestPayload;
import online.nwen.server.executor.api.payload.SearchArticleResponsePayload;
import online.nwen.server.repository.IAnthologyRepository;
import online.nwen.server.repository.IArticleRepository;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SearchArticleExecutor
        implements IExecutor<SearchArticleResponsePayload, SearchArticleRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(SearchArticleExecutor.class);
    private IArticleRepository articleRepository;
    private IAnthologyRepository anthologyRepository;

    public SearchArticleExecutor(IArticleRepository articleRepository,
                                 IAnthologyRepository anthologyRepository) {
        this.articleRepository = articleRepository;
        this.anthologyRepository = anthologyRepository;
    }

    @Override
    public void exec(IExecutorRequest<SearchArticleRequestPayload> request,
                     IExecutorResponse<SearchArticleResponsePayload> response,
                     ISecurityContext securityContext) throws ExecutorException {
        SearchArticleRequestPayload requestPayload = request.getPayload();
        Pageable pageable = PageRequest.of(requestPayload.getPageIndex(), requestPayload.getPageSize());
        if (SearchArticleRequestPayload.Condition.Type.ANTHOLOGY_ID == requestPayload.getCondition().getType()) {
            logger.debug("Search articles by anthology id.");
            response.setPayload(
                    this.searchArticlesByAnthologyId(requestPayload.getCondition(), pageable, securityContext));
            return;
        }
        if (SearchArticleRequestPayload.Condition.Type.TAGS == requestPayload.getCondition().getType()) {
            logger.debug("Search articles by tags.");
            response.setPayload(this.searchArticlesByTags(requestPayload.getCondition(), pageable, securityContext));
            return;
        }
        if (SearchArticleRequestPayload.Condition.Type.AUTHOR_ID == requestPayload.getCondition().getType()) {
            logger.debug("Search articles by author id.");
            response.setPayload(this.searchArticlesByAuthor(requestPayload.getCondition(), pageable, securityContext));
            return;
        }
        if (SearchArticleRequestPayload.Condition.Type.RECENT_CREATED == requestPayload.getCondition().getType()) {
            logger.debug("Search articles by recent created.");
            response.setPayload(
                    this.searchArticlesByRecentCreated(requestPayload.getCondition(), pageable, securityContext));
            return;
        }
        if (SearchArticleRequestPayload.Condition.Type.RECENT_UPDATED == requestPayload.getCondition().getType()) {
            logger.debug("Search articles by recent updated.");
            response.setPayload(
                    this.searchArticlesByRecentUpdated(requestPayload.getCondition(), pageable, securityContext));
            return;
        }
        logger.error("Do not support the search type.");
        throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
    }

    private SearchArticleResponsePayload searchArticlesByAnthologyId(
            SearchArticleRequestPayload.Condition condition, Pageable pageable, ISecurityContext securityContext)
            throws ExecutorException {
        String anthologyId = condition.getParams().get("anthologyId");
        logger.debug("Search articles by anthology id {}", anthologyId);
        boolean includePublish = false;
        if (securityContext != null) {
            Optional<Anthology> targetAnthologyOptional = this.anthologyRepository.findById(anthologyId);
            if (!targetAnthologyOptional.isPresent()) {
                logger.error("Fail to search articles by anthology id because of anthology not exist.");
                throw new ExecutorException("Fail to search articles by anthology id because of anthology not exist.",
                        ExecutorException.Code.SYS_ERROR);
            }
            Anthology targetAnthology = targetAnthologyOptional.get();
            if (targetAnthology.getAuthorId().equals(securityContext.getAuthorId())) {
                includePublish = true;
            }
        }
        Page<Article> articlePage = null;
        try {
            articlePage = articleRepository.findAllByAnthologyIdAndPublishOrderByUpdateDateDesc(anthologyId, includePublish, pageable);
        } catch (Exception e) {
            logger.error("Fail to search articles by anthology id because of exception.", e);
            throw new ExecutorException("Fail to search articles by anthology id because of exception.", e,
                    ExecutorException.Code.SYS_ERROR);
        }
        SearchArticleResponsePayload result = new SearchArticleResponsePayload();
        result.setRecords(this.convertArticlePageToSearchArticleRecordPage(articlePage));
        return result;
    }

    private SearchArticleResponsePayload searchArticlesByTags(
            SearchArticleRequestPayload.Condition condition, Pageable pageable, ISecurityContext securityContext)
            throws ExecutorException {
        String tagsStr = condition.getParams().get("tags");
        logger.debug("Search articles by tags {}", tagsStr);
        String[] tags = tagsStr.split(",");
        Page<Article> articlePage = null;
        try {
            articlePage = articleRepository.findAllByTagsContainingAndPublishOrderByUpdateDateDesc(tags, false, pageable);
        } catch (Exception e) {
            logger.error("Fail to search articles by tags because of exception.", e);
            throw new ExecutorException("Fail to search articles by tags because of exception.", e,
                    ExecutorException.Code.SYS_ERROR);
        }
        SearchArticleResponsePayload result = new SearchArticleResponsePayload();
        result.setRecords(this.convertArticlePageToSearchArticleRecordPage(articlePage));
        return result;
    }

    private SearchArticleResponsePayload searchArticlesByAuthor(
            SearchArticleRequestPayload.Condition condition, Pageable pageable, ISecurityContext securityContext)
            throws ExecutorException {
        String authorId = condition.getParams().get("authorId");
        logger.debug("Search articles by author id {}", authorId);
        boolean includePublish = false;
        if (securityContext != null && securityContext.getAuthorId().equals(authorId)) {
            includePublish = true;
        }
        Page<Article> articlePage = null;
        try {
            articlePage = articleRepository.findAllByAuthorIdAndPublishOrderByUpdateDateDesc(authorId, includePublish, pageable);
        } catch (Exception e) {
            logger.error("Fail to search articles by author id because of exception.", e);
            throw new ExecutorException("Fail to search articles by author id because of exception.", e,
                    ExecutorException.Code.SYS_ERROR);
        }
        SearchArticleResponsePayload result = new SearchArticleResponsePayload();
        result.setRecords(this.convertArticlePageToSearchArticleRecordPage(articlePage));
        return result;
    }

    private SearchArticleResponsePayload searchArticlesByRecentCreated(
            SearchArticleRequestPayload.Condition condition, Pageable pageable, ISecurityContext securityContext)
            throws ExecutorException {
        String relativeDateString = condition.getParams().get("relativeDate");
        logger.debug("Search recent created by relative date: {}", relativeDateString);
        Date relativeDate = getRelativeDateFromRequest(relativeDateString);
        Page<Article> articlePage = null;
        try {
            articlePage =
                    articleRepository
                            .findAllByCreateDateBeforeAndPublishOrderByCreateDateDesc(relativeDate, false, pageable);
        } catch (Exception e) {
            logger.error("Fail to search articles by author id because of exception.", e);
            throw new ExecutorException("Fail to search articles by author id because of exception.", e,
                    ExecutorException.Code.SYS_ERROR);
        }
        SearchArticleResponsePayload result = new SearchArticleResponsePayload();
        result.setRecords(this.convertArticlePageToSearchArticleRecordPage(articlePage));
        return result;
    }

    private SearchArticleResponsePayload searchArticlesByRecentUpdated(
            SearchArticleRequestPayload.Condition condition, Pageable pageable, ISecurityContext securityContext)
            throws ExecutorException {
        String relativeDateString = condition.getParams().get("relativeDate");
        logger.debug("Search recent created by relative date: {}", relativeDateString);
        Date relativeDate = getRelativeDateFromRequest(relativeDateString);
        Page<Article> articlePage = null;
        try {
            articlePage =
                    articleRepository
                            .findAllByUpdateDateBeforeAndPublishOrderByUpdateDateDesc(relativeDate, false, pageable);
        } catch (Exception e) {
            logger.error("Fail to search articles by author id because of exception.", e);
            throw new ExecutorException("Fail to search articles by author id because of exception.", e,
                    ExecutorException.Code.SYS_ERROR);
        }
        SearchArticleResponsePayload result = new SearchArticleResponsePayload();
        result.setRecords(this.convertArticlePageToSearchArticleRecordPage(articlePage));
        return result;
    }

    private Date getRelativeDateFromRequest(String relativeDateString) {
        Date relativeDate;
        if (relativeDateString == null) {
            relativeDate = new Date();
        } else {
            try {
                relativeDate = new Date(Long.parseLong(relativeDateString));
            } catch (NumberFormatException e) {
                relativeDate = new Date();
            }
        }
        return relativeDate;
    }

    private Page<SearchArticleResponsePayload.SearchArticleRecord> convertArticlePageToSearchArticleRecordPage(
            Page<Article> articlePage) {
        return articlePage.map(article -> {
            SearchArticleResponsePayload.SearchArticleRecord record =
                    new SearchArticleResponsePayload.SearchArticleRecord();
            record.setId(article.getId());
            return record;
        });
    }
}
