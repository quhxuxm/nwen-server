package online.nwen.server.executor.impl;

import online.nwen.server.domain.Article;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.SearchArticleRequestPayload;
import online.nwen.server.executor.api.payload.SearchArticleResponsePayload;
import online.nwen.server.repository.IArticleRepository;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchArticleExecutor
        implements IExecutor<SearchArticleResponsePayload, SearchArticleRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(SearchArticleExecutor.class);
    private IArticleRepository articleRepository;

    public SearchArticleExecutor(IArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void exec(IExecutorRequest<SearchArticleRequestPayload> request,
                     IExecutorResponse<SearchArticleResponsePayload> response,
                     ISecurityContext securityContext) throws ExecutorException {
        SearchArticleRequestPayload requestPayload = request.getPayload();
        Pageable pageable = PageRequest.of(requestPayload.getPageIndex(), requestPayload.getPageSize());
        if (SearchArticleRequestPayload.Condition.Type.ANTHOLOGY_ID == requestPayload.getCondition().getType()) {
            logger.debug("Search articles by anthology id.");
            response.setPayload(this.searchArticlesByAnthologyId(requestPayload.getCondition(), pageable));
            return;
        }
        if (SearchArticleRequestPayload.Condition.Type.TAGS == requestPayload.getCondition().getType()) {
            logger.debug("Search articles by tags.");
            response.setPayload(this.searchArticlesByTags(requestPayload.getCondition(), pageable));
            return;
        }
        logger.error("Do not support the search type.");
        throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
    }

    private SearchArticleResponsePayload searchArticlesByAnthologyId(
            SearchArticleRequestPayload.Condition condition, Pageable pageable) throws ExecutorException {
        String anthologyId = condition.getParams().get("anthologyId");
        logger.debug("Search articles by anthology id {}", anthologyId);
        Page<Article> articlePage = null;
        try {
            articlePage = articleRepository.findAllByAnthologyId(anthologyId, pageable);
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
            SearchArticleRequestPayload.Condition condition, Pageable pageable) throws ExecutorException {
        String tagsStr = condition.getParams().get("tags");
        logger.debug("Search articles by tags {}", tagsStr);
        String[] tags = tagsStr.split(",");
        Page<Article> articlePage = null;
        try {
            articlePage = articleRepository.findAllByTagsContaining(tags, pageable);
        } catch (Exception e) {
            logger.error("Fail to search articles by tags because of exception.", e);
            throw new ExecutorException("Fail to search articles by tags because of exception.", e,
                    ExecutorException.Code.SYS_ERROR);
        }
        SearchArticleResponsePayload result = new SearchArticleResponsePayload();
        result.setRecords(this.convertArticlePageToSearchArticleRecordPage(articlePage));
        return result;
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
