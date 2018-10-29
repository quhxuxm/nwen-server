package online.nwen.server.controller;

import online.nwen.server.executor.api.IExecutorRequest;

import java.util.HashMap;
import java.util.Map;

class EntryRequest implements IExecutorRequest<Object> {
    private Map<IExecutorRequest.RequestHeader, String> header;
    private Object payload;

    EntryRequest() {
        this.header = new HashMap<>();
    }

    public Map<IExecutorRequest.RequestHeader, String> getHeader() {
        return header;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
