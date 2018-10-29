package online.nwen.server.payload;

public class ExceptionPayload {
    public enum Code {
        INPUT_ERROR
    }

    private Code code;

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }
}
