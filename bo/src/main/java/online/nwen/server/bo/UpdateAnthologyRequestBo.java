package online.nwen.server.bo;

public class UpdateAnthologyRequestBo extends CreateAnthologyRequestBo {
    private Long anthologyId;

    public Long getAnthologyId() {
        return anthologyId;
    }

    public void setAnthologyId(Long anthologyId) {
        this.anthologyId = anthologyId;
    }
}
