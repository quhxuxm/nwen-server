package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.SearchAnthologyRequestPayload;
import online.nwen.server.executor.api.payload.SearchAnthologyResponsePayload;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SearchAnthologyExecutor
        implements IExecutor<SearchAnthologyResponsePayload, SearchAnthologyRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(SearchAnthologyExecutor.class);
    private IAnthologyService anthologyService;

    public SearchAnthologyExecutor(IAnthologyService anthologyService) {
        this.anthologyService = anthologyService;
    }

    @Override
    public void exec(IExecutorRequest<SearchAnthologyRequestPayload> request,
                     IExecutorResponse<SearchAnthologyResponsePayload> response,
                     ISecurityContext securityContext) throws ExecutorException {
        SearchAnthologyRequestPayload requestPayload = request.getPayload();
        Pageable pageable = PageRequest.of(requestPayload.getPageIndex(), requestPayload.getPageSize());
        if (SearchAnthologyRequestPayload.Condition.Type.AUTHOR_ID == requestPayload.getCondition().getType()) {
            logger.debug("Search anthologies by author id.");
            response.setPayload(
                    this.searchAnthologiesByAuthorId(requestPayload.getCondition(), pageable, securityContext));
            return;
        }
        if (SearchAnthologyRequestPayload.Condition.Type.TAGS == requestPayload.getCondition().getType()) {
            logger.debug("Search anthologies by tags.");
            response.setPayload(this.searchAnthologiesByTags(requestPayload.getCondition(), pageable, securityContext));
            return;
        }
        if (SearchAnthologyRequestPayload.Condition.Type.RECENT_CREATED == requestPayload.getCondition().getType()) {
            logger.debug("Search anthologies by recent created.");
            response.setPayload(
                    this.searchAnthologiesByRecentCreated(requestPayload.getCondition(), pageable, securityContext));
            return;
        }
        if (SearchAnthologyRequestPayload.Condition.Type.RECENT_UPDATED == requestPayload.getCondition().getType()) {
            logger.debug("Search anthologies by recent updated.");
            response.setPayload(
                    this.searchAnthologiesByRecentUpdated(requestPayload.getCondition(), pageable, securityContext));
            return;
        }
        logger.error("Do not support the search type.");
        throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
    }

    private SearchAnthologyResponsePayload searchAnthologiesByAuthorId(
            SearchAnthologyRequestPayload.Condition condition, Pageable pageable, ISecurityContext securityContext)
            throws ExecutorException {
        String authorId = condition.getParams().get("authorId");
        boolean currentAuthorIsOwner = false;
        if (securityContext != null) {
            currentAuthorIsOwner = securityContext.getAuthorId().equals(authorId);
        }
        logger.debug("Search anthologies by author id {}", authorId);
        Page<Anthology> anthologyPage = null;
        if (currentAuthorIsOwner) {
            anthologyPage = this.anthologyService
                    .findAllByAuthorIdOrderByCreateDateDesc(authorId, pageable);
        } else {
            anthologyPage = this.anthologyService
                    .findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDateDesc(authorId, true, pageable);
        }
        SearchAnthologyResponsePayload result = new SearchAnthologyResponsePayload();
        result.setRecords(this.convertAnthologyPageToSearchAnthologyRecordPage(anthologyPage));
        return result;
    }

    private SearchAnthologyResponsePayload searchAnthologiesByTags(
            SearchAnthologyRequestPayload.Condition condition, Pageable pageable, ISecurityContext securityContext)
            throws ExecutorException {
        String tagsStr = condition.getParams().get("tags");
        logger.debug("Search anthologies by tags {}", tagsStr);
        String[] tags = tagsStr.split(",");
        Page<Anthology> anthologyPage = this.anthologyService
                .findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDateDesc(tags, true, pageable);
        SearchAnthologyResponsePayload result = new SearchAnthologyResponsePayload();
        result.setRecords(this.convertAnthologyPageToSearchAnthologyRecordPage(anthologyPage));
        return result;
    }

    private SearchAnthologyResponsePayload searchAnthologiesByRecentCreated(
            SearchAnthologyRequestPayload.Condition condition, Pageable pageable, ISecurityContext securityContext)
            throws ExecutorException {
        String relativeDateString = condition.getParams().get("relativeDate");
        logger.debug("Search recent created by relative date: {}", relativeDateString);
        Date relativeDate = getRelativeDateFromRequest(relativeDateString);
        Page<Anthology> anthologyPage = this.anthologyService
                .findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDateDesc(relativeDate, true,
                        pageable);
        SearchAnthologyResponsePayload result = new SearchAnthologyResponsePayload();
        result.setRecords(this.convertAnthologyPageToSearchAnthologyRecordPage(anthologyPage));
        return result;
    }

    private SearchAnthologyResponsePayload searchAnthologiesByRecentUpdated(
            SearchAnthologyRequestPayload.Condition condition, Pageable pageable, ISecurityContext securityContext)
            throws ExecutorException {
        String relativeDateString = condition.getParams().get("relativeDate");
        logger.debug("Search recent created by relative date: {}", relativeDateString);
        Date relativeDate = getRelativeDateFromRequest(relativeDateString);
        Page<Anthology> anthologyPage = this.anthologyService
                .findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDateDesc(relativeDate, true,
                        pageable);
        SearchAnthologyResponsePayload result = new SearchAnthologyResponsePayload();
        result.setRecords(this.convertAnthologyPageToSearchAnthologyRecordPage(anthologyPage));
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

    private Page<SearchAnthologyResponsePayload.SearchAnthologyRecord> convertAnthologyPageToSearchAnthologyRecordPage(
            Page<Anthology> anthologyPage) {
        return anthologyPage.map(anthology -> {
            SearchAnthologyResponsePayload.SearchAnthologyRecord record =
                    new SearchAnthologyResponsePayload.SearchAnthologyRecord();
            record.setId(anthology.getId());
            record.setPublish(anthology.isAuthorConfirmedPublish());
            return record;
        });
    }
}
