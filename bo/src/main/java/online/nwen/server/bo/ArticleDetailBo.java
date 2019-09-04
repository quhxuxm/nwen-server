package online.nwen.server.bo;

public class ArticleDetailBo {
    private ArticleSummaryBo summary;
    private Long contentVersion;
    private String content;

    public ArticleSummaryBo getSummary() {
        return summary;
    }

    public void setSummary(ArticleSummaryBo summary) {
        this.summary = summary;
    }

    public Long getContentVersion() {
        return contentVersion;
    }

    public void setContentVersion(Long contentVersion) {
        this.contentVersion = contentVersion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
