package online.nwen.server.bo;

public class ArticleDetailBo {
    private ArticleSummaryBo summary;
    private Long contentId;
    private String content;

    public ArticleSummaryBo getSummary() {
        return summary;
    }

    public void setSummary(ArticleSummaryBo summary) {
        this.summary = summary;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
