package online.nwen.server.controller;

import online.nwen.server.executor.api.IExecutorResponse;

import java.util.HashMap;
import java.util.Map;

class HttpExecutorResponse<PayloadType> implements IExecutorResponse<PayloadType> {
    private Map<IExecutorResponse.ResponseHeader, String> header;
    private PayloadType payload;

    HttpExecutorResponse() {
        this.header = new HashMap<>();
    }

    public Map<IExecutorResponse.ResponseHeader, String> getHeader() {
        return header;
    }

    public PayloadType getPayload() {
        return payload;
    }

    public void setPayload(PayloadType payload) {
        this.payload = payload;
    }
}
