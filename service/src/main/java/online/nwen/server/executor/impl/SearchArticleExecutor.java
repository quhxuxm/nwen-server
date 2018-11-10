package online.nwen.server.executor.impl;

import online.nwen.server.domain.Article;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.SearchArticleRequestPayload;
import online.nwen.server.executor.api.payload.SearchArticleResponsePayload;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.IArticleService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SearchArticleExecutor
        implements IExecutor<SearchArticleResponsePayload, SearchArticleRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(SearchArticleExecutor.class);
    private IArticleService articleService;
    private IAnthologyService anthologyService;

    public SearchArticleExecutor(IArticleService articleService,
                                 IAnthologyService anthologyService) {
        this.articleService = articleService;
        this.anthologyService = anthologyService;
    }

    @Override
    public void exec(IExecutorRequest<SearchArticleRequestPayload> request,
                     IExecutorResponse<SearchArticleResponsePayload> response,
                     ISecurityContext securityContext) throws ExecutorException {
        SearchArticleRequestPayload requestPayload = request.getPayload();
        if (requestPayload.getCondition() == null) {
            logger.error("Can not search the article because of condition is null.");
            throw new ExecutorException(ExecutorException.Code.INPUT_ERROR);
        }
        Pageable pageable = null;
        if (requestPayload.getCondition().isAsc()) {
            pageable = PageRequest.of(requestPayload.getPageIndex(), requestPayload.getPageSize(), Sort.Direction.ASC);
        } else {
            pageable = PageRequest.of(requestPayload.getPageIndex(), requestPayload.getPageSize(), Sort.Direction.DESC);
        }
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
        boolean currentAuthorIsOwner = false;
        if (securityContext != null) {
            currentAuthorIsOwner = this.anthologyService.isOwner(securityContext.getAuthorId(), anthologyId);
        }
        Page<Article> articlePage = null;
        if (currentAuthorIsOwner) {
            articlePage = articleService
                    .findAllByAnthologyIdOrderByCreateDate(anthologyId, pageable);
        } else {
            articlePage = articleService
                    .findAllByAnthologyIdAndSystemConfirmedPublishOrderByCreateDate(anthologyId, true,
                            pageable);
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
        Page<Article> articlePage =
                articleService.findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDate(tags, true,
                        pageable);
        SearchArticleResponsePayload result = new SearchArticleResponsePayload();
        result.setRecords(this.convertArticlePageToSearchArticleRecordPage(articlePage));
        return result;
    }

    private SearchArticleResponsePayload searchArticlesByAuthor(
            SearchArticleRequestPayload.Condition condition, Pageable pageable, ISecurityContext securityContext)
            throws ExecutorException {
        String authorId = condition.getParams().get("authorId");
        logger.debug("Search articles by author id {}", authorId);
        boolean currentAuthorIsOwner = false;
        if (securityContext != null) {
            currentAuthorIsOwner = securityContext.getAuthorId().equals(authorId);
        }
        Page<Article> articlePage = null;
        if (currentAuthorIsOwner) {
            articlePage = articleService
                    .findAllByAuthorIdOrderByCreateDate(authorId, pageable);
        } else {
            articlePage = articleService
                    .findAllByAuthorIdAndSystemConfirmedPublishOrderByCreateDate(authorId, true, pageable);
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
        Page<Article> articlePage =
                articleService
                        .findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDateDesc(relativeDate, true,
                                pageable);
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
        Page<Article> articlePage =
                articleService
                        .findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDateDesc(relativeDate, true,
                                pageable);
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
            record.setPublish(article.isAuthorConfirmedPublish());
            return record;
        });
    }
}
