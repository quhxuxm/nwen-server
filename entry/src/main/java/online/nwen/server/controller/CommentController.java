package online.nwen.server.controller;

import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.CreateCommentRequestPayload;
import online.nwen.server.executor.api.payload.CreateCommentResponsePayload;
import online.nwen.server.executor.api.payload.SearchCommentRequestPayload;
import online.nwen.server.executor.api.payload.SearchCommentResponsePayload;
import online.nwen.server.executor.impl.CreateCommentExecutor;
import online.nwen.server.executor.impl.SearchCommentExecutor;
import online.nwen.server.service.api.IExecutorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController extends AbstractEntryController {
    private CreateCommentExecutor createCommentExecutor;
    private SearchCommentExecutor searchCommentExecutor;

    CommentController(IExecutorService executorInvoker,
                      CreateCommentExecutor createCommentExecutor,
                      SearchCommentExecutor searchCommentExecutor) {
        super(executorInvoker);
        this.createCommentExecutor = createCommentExecutor;
        this.searchCommentExecutor = searchCommentExecutor;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<CreateCommentResponsePayload> create(
            @RequestBody HttpExecutorRequest<CreateCommentRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.createCommentExecutor, true);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<SearchCommentResponsePayload> search(
            @RequestBody HttpExecutorRequest<SearchCommentRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.searchCommentExecutor, false);
    }
}