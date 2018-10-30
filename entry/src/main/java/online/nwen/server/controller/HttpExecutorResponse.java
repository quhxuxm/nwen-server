package online.nwen.server.controller;

import online.nwen.server.executor.api.IExecutorResponse;

import java.util.HashMap;
import java.util.Map;

class HttpExecutorResponse<PayloadType> implements IExecutorResponse<PayloadType> {
    private boolean success;
    private Map<IExecutorResponse.ResponseHeader, String> header;
    private PayloadType payload;

    HttpExecutorResponse() {
        this.header = new HashMap<>();
        this.success = true;
    }

    @Override
    public boolean isSuccess() {
        return this.success;
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

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
