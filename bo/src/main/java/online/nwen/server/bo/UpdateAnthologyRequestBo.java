package online.nwen.server.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UpdateAnthologyRequestBo extends CreateAnthologyRequestBo {
    @JsonIgnore
    private Long anthologyId;

    public Long getAnthologyId() {
        return anthologyId;
    }

    public void setAnthologyId(Long anthologyId) {
        this.anthologyId = anthologyId;
    }
}
