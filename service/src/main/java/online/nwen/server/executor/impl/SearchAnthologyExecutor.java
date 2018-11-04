package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.SearchAnthologyRequestPayload;
import online.nwen.server.executor.api.payload.SearchAnthologyResponsePayload;
import online.nwen.server.repository.IAnthologyRepository;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchAnthologyExecutor
        implements IExecutor<SearchAnthologyResponsePayload, SearchAnthologyRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(SearchAnthologyExecutor.class);
    private IAnthologyRepository anthologyRepository;

    public SearchAnthologyExecutor(IAnthologyRepository anthologyRepository) {
        this.anthologyRepository = anthologyRepository;
    }

    @Override
    public void exec(IExecutorRequest<SearchAnthologyRequestPayload> request,
                     IExecutorResponse<SearchAnthologyResponsePayload> response,
                     ISecurityContext securityContext) throws ExecutorException {
        SearchAnthologyRequestPayload requestPayload = request.getPayload();
        Pageable pageable = PageRequest.of(requestPayload.getPageIndex(), requestPayload.getPageSize());
        if (SearchAnthologyRequestPayload.Condition.Type.AUTHOR_ID == requestPayload.getCondition().getType()) {
            logger.debug("Search anthologies by author id.");
            response.setPayload(this.searchAnthologiesByAuthorId(requestPayload.getCondition(), pageable));
            return;
        }
        if (SearchAnthologyRequestPayload.Condition.Type.TAGS == requestPayload.getCondition().getType()) {
            logger.debug("Search anthologies by tags.");
            response.setPayload(this.searchAnthologiesByTags(requestPayload.getCondition(), pageable));
            return;
        }
        logger.error("Do not support the search type.");
        throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
    }

    private SearchAnthologyResponsePayload searchAnthologiesByAuthorId(
            SearchAnthologyRequestPayload.Condition condition, Pageable pageable) throws ExecutorException {
        String authorId = condition.getParams().get("authorId");
        logger.debug("Search anthologies by author id {}", authorId);
        Page<Anthology> anthologyPage = null;
        try {
            anthologyPage = this.anthologyRepository.findAllByAuthorId(authorId, pageable);
        } catch (Exception e) {
            logger.error("Fail to search anthologies by author id because of exception.", e);
            throw new ExecutorException("Fail to search anthologies by author id because of exception.", e,
                    ExecutorException.Code.SYS_ERROR);
        }
        SearchAnthologyResponsePayload result = new SearchAnthologyResponsePayload();
        result.setRecords(this.convertAnthologyPageToSearchAnthologyRecordPage(anthologyPage));
        return result;
    }

    private SearchAnthologyResponsePayload searchAnthologiesByTags(
            SearchAnthologyRequestPayload.Condition condition, Pageable pageable) throws ExecutorException {
        String tagsStr = condition.getParams().get("tags");
        logger.debug("Search anthologies by tags {}", tagsStr);
        String[] tags = tagsStr.split(",");
        Page<Anthology> anthologyPage = null;
        try {
            anthologyPage = this.anthologyRepository.findAllByTagsContaining(tags, pageable);
        } catch (Exception e) {
            logger.error("Fail to search anthologies by tags because of exception.", e);
            throw new ExecutorException("Fail to search anthologies by tags because of exception.", e,
                    ExecutorException.Code.SYS_ERROR);
        }
        SearchAnthologyResponsePayload result = new SearchAnthologyResponsePayload();
        result.setRecords(this.convertAnthologyPageToSearchAnthologyRecordPage(anthologyPage));
        return result;
    }

    private Page<SearchAnthologyResponsePayload.SearchAnthologyRecord> convertAnthologyPageToSearchAnthologyRecordPage(
            Page<Anthology> anthologyPage) {
        return anthologyPage.map(anthology -> {
            SearchAnthologyResponsePayload.SearchAnthologyRecord record =
                    new SearchAnthologyResponsePayload.SearchAnthologyRecord();
            record.setId(anthology.getId());
            return record;
        });
    }
}
