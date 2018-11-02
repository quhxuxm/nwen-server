package online.nwen.server.executor.api.payload;

public class PublishArticleRequestPayload {
    private String articleId;
    private boolean publish;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
}
