package online.nwen.server.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "nwen-server")
@Component
public class ServerConfiguration {
    private String jwtSecret;
    private String jwtIssuer;
    private Long jwtRefreshInterval;
    private Long jwtExpireInterval;
    private String usernameFormat;
    private String passwordFormat;
    private String nicknameFormat;
    private Integer anthologyTitleMaxLength;
    private Integer anthologySummaryMaxLength;
    private Integer articleTitleMaxLength;
    private Integer articleSummaryMaxLength;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }

    public void setJwtIssuer(String jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }

    public Long getJwtRefreshInterval() {
        return jwtRefreshInterval;
    }

    public void setJwtRefreshInterval(Long jwtRefreshInterval) {
        this.jwtRefreshInterval = jwtRefreshInterval;
    }

    public Long getJwtExpireInterval() {
        return jwtExpireInterval;
    }

    public void setJwtExpireInterval(Long jwtExpireInterval) {
        this.jwtExpireInterval = jwtExpireInterval;
    }

    public void setUsernameFormat(String usernameFormat) {
        this.usernameFormat = usernameFormat;
    }

    public String getUsernameFormat() {
        return usernameFormat;
    }

    public String getPasswordFormat() {
        return passwordFormat;
    }

    public void setPasswordFormat(String passwordFormat) {
        this.passwordFormat = passwordFormat;
    }

    public String getNicknameFormat() {
        return nicknameFormat;
    }

    public void setNicknameFormat(String nicknameFormat) {
        this.nicknameFormat = nicknameFormat;
    }

    public Integer getAnthologyTitleMaxLength() {
        return anthologyTitleMaxLength;
    }

    public void setAnthologyTitleMaxLength(Integer anthologyTitleMaxLength) {
        this.anthologyTitleMaxLength = anthologyTitleMaxLength;
    }

    public Integer getAnthologySummaryMaxLength() {
        return anthologySummaryMaxLength;
    }

    public void setAnthologySummaryMaxLength(Integer anthologySummaryMaxLength) {
        this.anthologySummaryMaxLength = anthologySummaryMaxLength;
    }

    public Integer getArticleTitleMaxLength() {
        return articleTitleMaxLength;
    }

    public void setArticleTitleMaxLength(Integer articleTitleMaxLength) {
        this.articleTitleMaxLength = articleTitleMaxLength;
    }

    public Integer getArticleSummaryMaxLength() {
        return articleSummaryMaxLength;
    }

    public void setArticleSummaryMaxLength(Integer articleSummaryMaxLength) {
        this.articleSummaryMaxLength = articleSummaryMaxLength;
    }
}
