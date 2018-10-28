package online.nwen.server.service.executor.api;

import java.util.Map;

/**
 * The executor request
 *
 * @param <PayloadType>
 */
public interface IExecutorRequest<PayloadType> {
    /**
     * The request header.
     *
     * @return The header
     */
    Map<String, String> getHeader();

    /**
     * The request payload.
     *
     * @return The payload.
     */
    PayloadType getPayload();
}
