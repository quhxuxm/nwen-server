package online.nwen.server.bo;

public class ArticleDetailBo {
    private ArticleSummaryBo summary;
    private ArticleContentBo content;

    public ArticleSummaryBo getSummary() {
        return summary;
    }

    public void setSummary(ArticleSummaryBo summary) {
        this.summary = summary;
    }

    public ArticleContentBo getContent() {
        return content;
    }

    public void setContent(ArticleContentBo content) {
        this.content = content;
    }
}
