package online.nwen.server.executor.impl;

import online.nwen.server.domain.Article;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.ListArticlesOfAnthologyRequestPayload;
import online.nwen.server.executor.api.payload.ListArticlesOfAnthologyResponsePayload;
import online.nwen.server.repository.IArticleRepository;
import online.nwen.server.service.api.ISecurityContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ListArticlesOfAnthologyExecutor
        implements IExecutor<ListArticlesOfAnthologyResponsePayload, ListArticlesOfAnthologyRequestPayload> {
    private IArticleRepository articleRepository;

    @Override
    public void exec(IExecutorRequest<ListArticlesOfAnthologyRequestPayload> request,
                     IExecutorResponse<ListArticlesOfAnthologyResponsePayload> response,
                     ISecurityContext securityContext) throws ExecutorException {
        ListArticlesOfAnthologyRequestPayload requestPayload = request.getPayload();
        Pageable pageable = PageRequest.of(requestPayload.getPageIndex(), requestPayload.getPageSize());
        Page<Article> articlePage = articleRepository.findAllByAnthologyId(requestPayload.getAnthologyId(), pageable);
    }
}
