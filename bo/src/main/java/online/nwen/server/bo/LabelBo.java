package online.nwen.server.bo;

public class LabelBo {
    private Long id;
    private String text;
    private Long popularFactor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPopularFactor(Long popularFactor) {
        this.popularFactor = popularFactor;
    }

    public Long getPopularFactor() {
        return popularFactor;
    }
}
