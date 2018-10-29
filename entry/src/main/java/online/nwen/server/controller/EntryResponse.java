package online.nwen.server.controller;

import java.util.HashMap;
import java.util.Map;

class EntryResponse {
    private Map<String, String> header;
    private String payload;

    EntryResponse() {
        this.header = new HashMap<>();
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
