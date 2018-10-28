package online.nwen.server.executor.api;

import java.util.Map;

/**
 * The api request
 *
 * @param <PayloadType>
 */
public interface IExecutorRequest<PayloadType> {
    enum RequestHeader {
        SECURE_TOKEN
    }

    /**
     * The request header.
     *
     * @return The header
     */
    Map<RequestHeader, String> getHeader();

    /**
     * The request payload.
     *
     * @return The payload.
     */
    PayloadType getPayload();
}
