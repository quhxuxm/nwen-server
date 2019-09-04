package online.nwen.server.bo;

public class UpdateArticleResponseBo extends CreateArticleResponseBo {
    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
