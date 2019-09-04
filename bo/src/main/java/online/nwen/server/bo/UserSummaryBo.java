package online.nwen.server.bo;

import java.util.HashSet;
import java.util.Set;

public class UserSummaryBo {
    private String nickname;
    private Long authorId;
    private Set<LabelBo> labels;

    public UserSummaryBo() {
        this.labels = new HashSet<>();
    }

    public Set<LabelBo> getLabels() {
        return labels;
    }

    public void setLabels(Set<LabelBo> labels) {
        this.labels = labels;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
