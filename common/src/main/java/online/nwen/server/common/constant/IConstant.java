package online.nwen.server.common.constant;

public interface IConstant {
    interface MessageKey{
        String ANTHOLOGY_DEFAULT_TITLE_MESSAGE_KEY="anthology.defaultTitle";
        String ANTHOLOGY_DEFAULT_SUMMARY_MESSAGE_KEY="anthology.defaultSummary";
    }
    interface RequestAttrName{
        String REFRESHED_SECURITY_TOKEN="REFRESHED_SECURITY_TOKEN";
    }
    interface RequestHeaderName{
        String SECURITY_TOKEN="SECURITY_TOKEN";
    }
    interface ResponseHeaderName{
        String REFRESHED_SECURITY_TOKEN="REFRESHED_SECURITY_TOKEN";
    }
}
