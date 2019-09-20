package online.nwen.server.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "nwen-server")
@Component
public class ServerConfiguration {
    private String jwtSecret;
    private String jwtIssuer;
    private Long jwtRefreshableInterval;
    private Long jwtExpireInterval;
    private Resource usernamePatterns;
    private Resource passwordPatterns;
    private Resource nicknamePatterns;
    private Integer anthologyTitleMaxLength;
    private Integer anthologyDescriptionMaxLength;
    private Integer articleTitleMaxLength;
    private Integer articleDescriptionMaxLength;
    private Integer articleContentSaveInterval;
    private Long timerIntervalForSecurityJob;
    private Long timerIntervalForAdjustLabelPopularFactorJob;

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

    public Resource getUsernamePatterns() {
        return usernamePatterns;
    }

    public void setNicknamePatterns(Resource nicknamePatterns) {
        this.nicknamePatterns = nicknamePatterns;
    }

    public Resource getNicknamePatterns() {
        return nicknamePatterns;
    }

    public void setPasswordPatterns(Resource passwordPatterns) {
        this.passwordPatterns = passwordPatterns;
    }

    public Resource getPasswordPatterns() {
        return passwordPatterns;
    }

    public void setUsernamePatterns(Resource usernamePatterns) {
        this.usernamePatterns = usernamePatterns;
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

    public void setTimerIntervalForAdjustLabelPopularFactorJob(Long timerIntervalForAdjustLabelPopularFactorJob) {
        this.timerIntervalForAdjustLabelPopularFactorJob = timerIntervalForAdjustLabelPopularFactorJob;
    }

    public Long getTimerIntervalForAdjustLabelPopularFactorJob() {
        return timerIntervalForAdjustLabelPopularFactorJob;
    }
}
