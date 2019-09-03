package online.nwen.server.bo;

import java.util.HashSet;
import java.util.Set;

public class DeleteArticlesResponseBo {
    private Set<Long> articleIds;

    public DeleteArticlesResponseBo() {
        this.articleIds = new HashSet<>();
    }

    public void setArticleIds(Set<Long> articleIds) {
        this.articleIds = articleIds;
    }

    public Set<Long> getArticleIds() {
        return articleIds;
    }
}
