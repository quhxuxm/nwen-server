package online.nwen.server.service.job;

import online.nwen.server.dao.api.ISecurityTokenDao;
import online.nwen.server.domain.SecurityToken;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

@DisallowConcurrentExecution
public class AutoSecurityJob extends QuartzJobBean {
    private static final int PAGE_SIZE = 100;
    private ISecurityTokenDao securityTokenDao;

    public AutoSecurityJob(ISecurityTokenDao securityTokenDao) {
        this.securityTokenDao = securityTokenDao;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date currentDate = new Date();
        Page<SecurityToken> securityTokens = this.securityTokenDao.getTokensExceedRefreshTill(currentDate, PageRequest.of(0, PAGE_SIZE));
        securityTokens.forEach(this::clearToken);
        while (!securityTokens.isEmpty()) {
            securityTokens = this.securityTokenDao.getTokensExceedRefreshTill(currentDate, PageRequest.of(0, PAGE_SIZE));
            securityTokens.forEach(this::clearToken);
        }
    }

    private void clearToken(SecurityToken securityToken) {
        this.securityTokenDao.delete(securityToken);
    }
}
