package online.nwen.server.controller;

import online.nwen.server.executor.api.IExecutorInvoker;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.impl.CreateArticleExecutor;
import online.nwen.server.executor.impl.UpdateArticleExecutor;
import online.nwen.server.payload.CreateArticleRequestPayload;
import online.nwen.server.payload.CreateArticleResponsePayload;
import online.nwen.server.payload.UpdateArticleRequestPayload;
import online.nwen.server.payload.UpdateArticleResponsePayload;
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

    public ArticleController(IExecutorInvoker executorInvoker,
                             CreateArticleExecutor createArticleExecutor,
                             UpdateArticleExecutor updateArticleExecutor) {
        super(executorInvoker);
        this.createArticleExecutor = createArticleExecutor;
        this.updateArticleExecutor = updateArticleExecutor;
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
}
