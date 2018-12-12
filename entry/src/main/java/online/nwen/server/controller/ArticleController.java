package online.nwen.server.controller;

import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.*;
import online.nwen.server.executor.impl.*;
import online.nwen.server.service.api.IExecutorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
class ArticleController extends AbstractEntryController {
    private CreateArticleExecutor createArticleExecutor;
    private UpdateArticleExecutor updateArticleExecutor;
    private PublishArticleExecutor publishArticleExecutor;
    private ViewArticleDetailExecutor viewArticleDetailExecutor;
    private ViewArticleSummariesExecutor viewArticleSummariesExecutor;
    private SearchArticleExecutor searchArticleExecutor;

    public ArticleController(IExecutorService executorInvoker,
                             CreateArticleExecutor createArticleExecutor,
                             UpdateArticleExecutor updateArticleExecutor,
                             PublishArticleExecutor publishArticleExecutor,
                             ViewArticleDetailExecutor viewArticleDetailExecutor,
                             ViewArticleSummariesExecutor viewArticleSummariesExecutor,
                             SearchArticleExecutor searchArticleExecutor) {
        super(executorInvoker);
        this.createArticleExecutor = createArticleExecutor;
        this.updateArticleExecutor = updateArticleExecutor;
        this.publishArticleExecutor = publishArticleExecutor;
        this.viewArticleDetailExecutor = viewArticleDetailExecutor;
        this.viewArticleSummariesExecutor = viewArticleSummariesExecutor;
        this.searchArticleExecutor = searchArticleExecutor;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<CreateArticleResponsePayload> create(
            @RequestBody HttpExecutorRequest<CreateArticleRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.createArticleExecutor, true);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<UpdateArticleResponsePayload> update(
            @RequestBody HttpExecutorRequest<UpdateArticleRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.updateArticleExecutor, true);
    }

    @PostMapping(value = "/publish", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<PublishArticleResponsePayload> publish(
            @RequestBody HttpExecutorRequest<PublishArticleRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.publishArticleExecutor, true);
    }

    @PostMapping(value = "/view/detail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<ViewArticleDetailResponsePayload> viewDetail(
            @RequestBody HttpExecutorRequest<ViewArticleDetailRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.viewArticleDetailExecutor, false);
    }

    @PostMapping(value = "/view/summaries", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<ViewArticleSummariesResponsePayload> viewSummaries(
            @RequestBody HttpExecutorRequest<ViewArticleSummariesRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.viewArticleSummariesExecutor, false);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<SearchArticleResponsePayload> search(
            @RequestBody HttpExecutorRequest<SearchArticleRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.searchArticleExecutor, false);
    }
}
