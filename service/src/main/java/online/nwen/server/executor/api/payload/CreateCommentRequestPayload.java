package online.nwen.server.executor.api.payload;

public class CreateCommentRequestPayload {
    private String type;
    private String refDocumentId;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRefDocumentId() {
        return refDocumentId;
    }

    public void setRefDocumentId(String refDocumentId) {
        this.refDocumentId = refDocumentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
