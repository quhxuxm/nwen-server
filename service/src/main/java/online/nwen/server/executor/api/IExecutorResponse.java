package online.nwen.server.executor.api;

import java.util.Map;

/**
 * The api response
 *
 * @param <PayloadType>
 */
public interface IExecutorResponse<PayloadType> {
    /**
     * Is the response success.
     *
     * @return Is the response success
     */
    boolean isSuccess();

    /**
     * The response header
     *
     * @return Response header
     */
    Map<String, String> getHeader();

    /**
     * Get the response payload.
     *
     * @return The response payload
     */
    PayloadType getPayload();

    /**
     * Set the response payload
     *
     * @param payload The response payload.
     */
    void setPayload(PayloadType payload);
}
