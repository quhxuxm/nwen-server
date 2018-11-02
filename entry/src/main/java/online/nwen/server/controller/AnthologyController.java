package online.nwen.server.controller;

import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.CreateAnthologyRequestPayload;
import online.nwen.server.executor.api.payload.CreateAnthologyResponsePayload;
import online.nwen.server.executor.api.payload.UpdateAnthologyRequestPayload;
import online.nwen.server.executor.api.payload.UpdateAnthologyResponsePayload;
import online.nwen.server.executor.impl.CreateAnthologyExecutor;
import online.nwen.server.executor.impl.UpdateAnthologyExecutor;
import online.nwen.server.service.api.IExecutorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anthology")
class AnthologyController extends AbstractEntryController {
    private CreateAnthologyExecutor createAnthologyExecutor;
    private UpdateAnthologyExecutor updateAnthologyExecutor;

    public AnthologyController(IExecutorService executorInvoker,
                               CreateAnthologyExecutor createAnthologyExecutor,
                               UpdateAnthologyExecutor updateAnthologyExecutor) {
        super(executorInvoker);
        this.createAnthologyExecutor = createAnthologyExecutor;
        this.updateAnthologyExecutor = updateAnthologyExecutor;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<CreateAnthologyResponsePayload> create(
            @RequestBody HttpExecutorRequest<CreateAnthologyRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.createAnthologyExecutor, true);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<UpdateAnthologyResponsePayload> update(
            @RequestBody HttpExecutorRequest<UpdateAnthologyRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.updateAnthologyExecutor, true);
    }
}
