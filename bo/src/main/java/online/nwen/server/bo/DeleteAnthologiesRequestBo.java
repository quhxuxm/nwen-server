package online.nwen.server.bo;

import java.util.HashSet;
import java.util.Set;

public class DeleteAnthologiesRequestBo {
    private Set<Long> anthologyIds;

    public DeleteAnthologiesRequestBo() {
        this.anthologyIds = new HashSet<>();
    }

    public Set<Long> getAnthologyIds() {
        return anthologyIds;
    }

    public void setAnthologyIds(Set<Long> anthologyIds) {
        this.anthologyIds = anthologyIds;
    }
}
