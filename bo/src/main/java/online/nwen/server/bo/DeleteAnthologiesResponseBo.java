package online.nwen.server.bo;

import java.util.HashSet;
import java.util.Set;

public class DeleteAnthologiesResponseBo {
    private Set<Long> anthologyIds;

    public DeleteAnthologiesResponseBo() {
        this.anthologyIds = new HashSet<>();
    }

    public Set<Long> getAnthologyIds() {
        return anthologyIds;
    }

    public void setAnthologyIds(Set<Long> anthologyIds) {
        this.anthologyIds = anthologyIds;
    }
}
