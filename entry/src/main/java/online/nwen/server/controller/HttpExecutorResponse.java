package online.nwen.server.controller;

import online.nwen.server.executor.api.IExecutorResponse;

import java.util.HashMap;
import java.util.Map;

class HttpExecutorResponse<PayloadType> implements IExecutorResponse<PayloadType> {
    private boolean success;
    private Map<IExecutorResponse.ResponseHeader, String> header;
    private PayloadType payload;

    HttpExecutorResponse() {
        this.success = true;
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

    public boolean isSuccess() {
        return success;
    }

    void setSuccess(boolean success) {
        this.success = success;
    }
}
