package online.nwen.server.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "nwen-server")
@Component
public class ServerConfiguration {
    private String jwtSecret;
    private String jwtIssuer;
    private Long jwtRefreshableInterval;
    private Long jwtExpireInterval;
    private String usernameFormat;
    private String passwordFormat;
    private String nicknameFormat;
    private Integer anthologyTitleMaxLength;
    private Integer anthologyDescriptionMaxLength;
    private Integer articleTitleMaxLength;
    private Integer articleDescriptionMaxLength;
    private Integer articleContentSaveInterval;
    private Long timerIntervalForSecurityJob;

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

    public Long getJwtRefreshableInterval() {
        return jwtRefreshableInterval;
    }

    public void setJwtRefreshableInterval(Long jwtRefreshableInterval) {
        this.jwtRefreshableInterval = jwtRefreshableInterval;
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

    public Integer getAnthologyDescriptionMaxLength() {
        return anthologyDescriptionMaxLength;
    }

    public void setAnthologyDescriptionMaxLength(Integer anthologyDescriptionMaxLength) {
        this.anthologyDescriptionMaxLength = anthologyDescriptionMaxLength;
    }

    public Integer getArticleTitleMaxLength() {
        return articleTitleMaxLength;
    }

    public void setArticleTitleMaxLength(Integer articleTitleMaxLength) {
        this.articleTitleMaxLength = articleTitleMaxLength;
    }

    public Integer getArticleDescriptionMaxLength() {
        return articleDescriptionMaxLength;
    }

    public void setArticleDescriptionMaxLength(Integer articleDescriptionMaxLength) {
        this.articleDescriptionMaxLength = articleDescriptionMaxLength;
    }

    public void setArticleContentSaveInterval(Integer articleContentSaveInterval) {
        this.articleContentSaveInterval = articleContentSaveInterval;
    }

    public Integer getArticleContentSaveInterval() {
        return articleContentSaveInterval;
    }

    public void setTimerIntervalForSecurityJob(Long timerIntervalForSecurityJob) {
        this.timerIntervalForSecurityJob = timerIntervalForSecurityJob;
    }

    public Long getTimerIntervalForSecurityJob() {
        return timerIntervalForSecurityJob;
    }
}
