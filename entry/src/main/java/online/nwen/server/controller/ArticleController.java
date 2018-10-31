package online.nwen.server.controller;

import online.nwen.server.executor.api.IExecutorInvoker;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.impl.CreateArticleExecutor;
import online.nwen.server.payload.CreateArticleRequestPayload;
import online.nwen.server.payload.CreateArticleResponsePayload;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
public class ArticleController extends AbstractEntryController {
    private IExecutorInvoker executorInvoker;
    private CreateArticleExecutor createArticleExecutor;

    public ArticleController(IExecutorInvoker executorInvoker,
                             CreateArticleExecutor createArticleExecutor) {
        this.executorInvoker = executorInvoker;
        this.createArticleExecutor = createArticleExecutor;
    }

    @PostMapping(value = "/create", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE,
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    HttpExecutorResponse<CreateArticleResponsePayload> create(
            @RequestBody HttpExecutorRequest<CreateArticleRequestPayload> request)
            throws ExecutorException {
        if (!this.verifyInput(request)) {
            throw new ExecutorException(ExecutorException.Code.INPUT_ERROR);
        }
        HttpExecutorResponse<CreateArticleResponsePayload> response = new HttpExecutorResponse<>();
        this.executorInvoker.invoke(this.createArticleExecutor, request, response, true);
        return response;
    }
}
