package online.nwen.server.controller;

import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.AuthenticateRequestPayload;
import online.nwen.server.executor.api.payload.AuthenticateResponsePayload;
import online.nwen.server.executor.impl.AuthenticateExecutor;
import online.nwen.server.service.api.IExecutorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticate")
class AuthenticateController extends AbstractEntryController {
    private AuthenticateExecutor authenticateExecutor;

    AuthenticateController(IExecutorService executorInvoker,
                           AuthenticateExecutor authenticateExecutor) {
        super(executorInvoker);
        this.authenticateExecutor = authenticateExecutor;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<AuthenticateResponsePayload> authenticate(
            @RequestBody HttpExecutorRequest<AuthenticateRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.authenticateExecutor, false);
    }
}
