package online.nwen.server.executor.api.payload;

public class PublishAnthologyRequestPayload {
    private String anthologyId;
    private boolean publish;

    public String getAnthologyId() {
        return anthologyId;
    }

    public void setAnthologyId(String anthologyId) {
        this.anthologyId = anthologyId;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
}
