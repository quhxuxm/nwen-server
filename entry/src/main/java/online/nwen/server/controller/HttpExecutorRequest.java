package online.nwen.server.controller;

import online.nwen.server.executor.api.IExecutorRequest;

import java.util.HashMap;
import java.util.Map;

class HttpExecutorRequest<PayloadType> implements IExecutorRequest<PayloadType> {
    private Map<IExecutorRequest.RequestHeader, String> header;
    private PayloadType payload;

    HttpExecutorRequest() {
        this.header = new HashMap<>();
    }

    public Map<IExecutorRequest.RequestHeader, String> getHeader() {
        return header;
    }

    public PayloadType getPayload() {
        return payload;
    }

    public void setPayload(PayloadType payload) {
        this.payload = payload;
    }
}
