package online.nwen.server.api;

import java.util.Map;

public class ApiResponse<PayloadType> {
    private PayloadType payload;
    private Map<String, String> header;
    private boolean success;

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PayloadType getPayload() {
        return payload;
    }

    public void setPayload(PayloadType payload) {
        this.payload = payload;
    }
}
