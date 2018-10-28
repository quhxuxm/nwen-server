package online.nwen.server.api;

import java.util.Map;

public class ApiRequest<PayloadType> {
    private PayloadType payload;
    private Map<String, String> header;

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public PayloadType getPayload() {
        return payload;
    }

    public void setPayload(PayloadType payload) {
        this.payload = payload;
    }
}
