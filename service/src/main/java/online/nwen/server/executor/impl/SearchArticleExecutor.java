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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchArticleExecutor
        implements IExecutor<SearchArticleResponsePayload, SearchArticleRequestPayload> {
    private IArticleRepository articleRepository;

    public SearchArticleExecutor(IArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void exec(IExecutorRequest<SearchArticleRequestPayload> request,
                     IExecutorResponse<SearchArticleResponsePayload> response,
                     ISecurityContext securityContext) throws ExecutorException {
        SearchArticleRequestPayload requestPayload = request.getPayload();
        SearchArticleResponsePayload responsePayload = new SearchArticleResponsePayload();
        Pageable pageable = PageRequest.of(requestPayload.getPageIndex(), requestPayload.getPageSize());
        if (SearchArticleRequestPayload.Condition.Type.ANTHOLOGY_ID == requestPayload.getCondition().getType()) {
            Page<SearchArticleResponsePayload.SearchArticleRecord> searchArticleRecords =
                    this.searchArticlesByAnthologyId(requestPayload.getCondition(), pageable);
            responsePayload.setRecords(searchArticleRecords);
        }
        response.setPayload(responsePayload);
    }

    private Page<SearchArticleResponsePayload.SearchArticleRecord> searchArticlesByAnthologyId(
            SearchArticleRequestPayload.Condition condition, Pageable pageable) {
        String anthologyId = condition.getParams().get("anthologyId");
        Page<Article> articlePage = articleRepository.findAllByAnthologyId(anthologyId, pageable);
        Page<SearchArticleResponsePayload.SearchArticleRecord> articleIdsPage = articlePage.map(article -> {
            SearchArticleResponsePayload.SearchArticleRecord record =
                    new SearchArticleResponsePayload.SearchArticleRecord();
            record.setId(article.getId());
            return record;
        });
        return articleIdsPage;
    }
}
