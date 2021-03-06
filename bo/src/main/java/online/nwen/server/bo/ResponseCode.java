package online.nwen.server.bo;

public enum ResponseCode {
    SUCCESS,
    INPUT_ERROR,
    SYSTEM_ERROR,
    REGISTER_USERNAME_EMPTY,
    REGISTER_USERNAME_FORMAT_ERROR,
    REGISTER_PASSWORD_EMPTY,
    REGISTER_PASSWORD_FORMAT_ERROR,
    REGISTER_NICKNAME_EMPTY,
    REGISTER_NICKNAME_FORMAT_ERROR,
    REGISTER_USERNAME_EXIST,
    REGISTER_NICKNAME_EXIST,
    AUTHENTICATE_USERNAME_IS_EMPTY,
    AUTHENTICATE_PASSWORD_IS_EMPTY,
    AUTHENTICATE_USERNAME_NOT_EXIST,
    AUTHENTICATE_PASSWORD_NOT_MATCH,
    SECURITY_TOKEN_IS_EMPTY,
    SECURITY_TOKEN_EXPIRED,
    SECURITY_TOKEN_PARSE_FAIL,
    SECURITY_TOKEN_GENERATE_FAIL,
    SECURITY_CHECK_FAIL,
    ANTHOLOGY_ID_IS_EMPTY,
    ANTHOLOGY_TITLE_IS_EMPTY,
    ANTHOLOGY_TITLE_IS_TOO_LONG,
    ANTHOLOGY_SUMMARY_IS_TOO_LONG,
    ANTHOLOGY_NOT_BELONG_TO_AUTHOR,
    ANTHOLOGY_NOT_EXIST,
    ARTICLE_ID_IS_EMPTY,
    ARTICLE_TITLE_IS_EMPTY,
    ARTICLE_TITLE_IS_TOO_LONG,
    ARTICLE_SUMMARY_IS_TOO_LONG,
    ARTICLE_NOT_EXIST,
    ARTICLE_NOT_BELONG_TO_ANTHOLOGY,
    COMMENT_NOT_BELONG_TO_ARTICLE,
    COMMENT_NOT_EXIST,
    USER_NOT_EXIST,
    MEDIA_RESOURCE_UPLOAD_FAIL,
    MEDIA_RESOURCE_NOT_EXIT,
    MEDIA_RESOURCE_READ_FAIL,
    CATEGORY_NOT_EXIST
}
