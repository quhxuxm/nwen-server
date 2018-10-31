package online.nwen.server.controller;

import online.nwen.server.executor.api.IExecutorInvoker;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.impl.AuthenticateExecutor;
import online.nwen.server.payload.AuthenticateRequestPayload;
import online.nwen.server.payload.AuthenticateResponsePayload;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticate")
class AuthenticateController extends AbstractEntryController {
    private IExecutorInvoker executorInvoker;
    private AuthenticateExecutor authenticateExecutor;

    AuthenticateController(IExecutorInvoker executorInvoker,
                           AuthenticateExecutor authenticateExecutor) {
        this.executorInvoker = executorInvoker;
        this.authenticateExecutor = authenticateExecutor;
    }

    @PostMapping(consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    HttpExecutorResponse<AuthenticateResponsePayload> service(
            @RequestBody HttpExecutorRequest<AuthenticateRequestPayload> request)
            throws ExecutorException {
        if (!this.verifyInput(request)) {
            throw new ExecutorException(ExecutorException.Code.INPUT_ERROR);
        }
        HttpExecutorResponse<AuthenticateResponsePayload> response = new HttpExecutorResponse<>();
        this.executorInvoker.invoke(this.authenticateExecutor, request, response, false);
        return response;
    }
}
