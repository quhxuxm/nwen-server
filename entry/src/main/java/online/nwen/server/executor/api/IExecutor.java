package online.nwen.server.executor.api;

import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.service.api.ISecurityContext;

/**
 * The api used to execute the business request.
 *
 * @param <ResponsePayloadType> The request payload.
 * @param <RequestPayloadType>  The response payload.
 */
public interface IExecutor<ResponsePayloadType, RequestPayloadType> {
    /**
     * Execute the business request
     *
     * @param request  The request.
     * @param response The response.
     * @throws ExecutorException The exception.
     */
    void exec(
            IExecutorRequest<RequestPayloadType> request, IExecutorResponse<ResponsePayloadType> response,
            ISecurityContext securityContext)
            throws ExecutorException;
}
