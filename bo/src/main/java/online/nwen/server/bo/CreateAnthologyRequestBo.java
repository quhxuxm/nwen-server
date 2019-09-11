package online.nwen.server.bo;

import java.util.HashSet;
import java.util.Set;

public class CreateAnthologyRequestBo {
    private String title;
    private String description;
    private boolean asDefault;
    private Set<String> labels;
    private Long categoryId;

    public CreateAnthologyRequestBo() {
        this.labels = new HashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAsDefault() {
        return asDefault;
    }

    public void setAsDefault(boolean asDefault) {
        this.asDefault = asDefault;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
