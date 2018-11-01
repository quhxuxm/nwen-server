package online.nwen.server.controller;

import online.nwen.server.service.api.IExecutorService;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.impl.RegisterExecutor;
import online.nwen.server.executor.api.payload.RegisterRequestPayload;
import online.nwen.server.executor.api.payload.RegisterResponsePayload;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
class RegisterController extends AbstractEntryController {
    private RegisterExecutor registerExecutor;

    RegisterController(IExecutorService executorInvoker,
                       RegisterExecutor registerExecutor) {
        super(executorInvoker);
        this.registerExecutor = registerExecutor;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<RegisterResponsePayload> register(
            @RequestBody HttpExecutorRequest<RegisterRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.registerExecutor, false);
    }
}
