package online.nwen.server.entry.interceptor;

import online.nwen.server.bo.ResponseCode;
import online.nwen.server.bo.SecurityContextBo;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class SecurityHandlerInterceptor implements HandlerInterceptor {
    private static final String SECURITY_TOKEN_HEADER = "NWEN_SECURITY_TOKEN";
    private ISecurityService securityService;

    public SecurityHandlerInterceptor(ISecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String securityToken = request.getHeader(SECURITY_TOKEN_HEADER);
        if (securityToken == null) {
            this.securityService.clearSecurityContextFromCurrentThread();
            throw new ServiceException(ResponseCode.SECURITY_TOKEN_IS_EMPTY);
        }
        try {
            this.securityService.verifyJwtToken(securityToken);
        } catch (ServiceException e) {
            if (e.getResponseCode() != ResponseCode.SECURITY_TOKEN_EXPIRED) {
                this.securityService.clearSecurityContextFromCurrentThread();
                throw e;
            }
            SecurityContextBo securityContextBo = this.securityService.parseJwtToken(securityToken);
            Date securityTokenRefreshTill = securityContextBo.getRefreshAbleTill();
            if (System.currentTimeMillis() > securityTokenRefreshTill.getTime()) {
                this.securityService.markSecurityTokenDisabled(securityToken);
                this.securityService.clearSecurityContextFromCurrentThread();
                throw new ServiceException(ResponseCode.SECURITY_TOKEN_EXPIRED);
            }
            if (this.securityService.isSecurityTokenDisabled(securityToken)) {
                this.securityService.clearSecurityContextFromCurrentThread();
                throw new ServiceException(ResponseCode.SECURITY_TOKEN_EXPIRED);
            }
            this.securityService.markSecurityTokenDisabled(securityToken);
            String refreshSecurityToken = this.securityService.refreshJwtToken(securityContextBo);
            response.setHeader(SECURITY_TOKEN_HEADER, refreshSecurityToken);
            securityToken = refreshSecurityToken;
        } catch (Exception e) {
            this.securityService.clearSecurityContextFromCurrentThread();
            throw new ServiceException(ResponseCode.SYSTEM_ERROR);
        }
        this.securityService.setSecurityContextToCurrentThread(this.securityService.parseJwtToken(securityToken));
        return true;
    }
}
