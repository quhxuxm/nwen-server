package online.nwen.server.service.executor.api;

/**
 * The executor used to execute the business request.
 *
 * @param <ResponsePayloadType> The request payload.
 * @param <RequestPayloadType>  The response payload.
 */
public interface IExecutor<ResponsePayloadType, RequestPayloadType> {
    /**
     * Execute the business request
     *
     * @param request The request.
     * @return The response.
     * @throws ExecutorException The exe
     */
    IExecutorResponse<ResponsePayloadType> exec(
            IExecutorRequest<RequestPayloadType> request) throws ExecutorException;
}
