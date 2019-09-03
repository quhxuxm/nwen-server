package online.nwen.server.entry.interceptor;

import online.nwen.server.bo.ResponseCode;
import online.nwen.server.bo.SecurityContextBo;
import online.nwen.server.common.constant.IConstant;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class SecurityCheckInterceptor implements HandlerInterceptor {
    private ISecurityService securityService;

    public SecurityCheckInterceptor(ISecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String securityToken = request.getHeader(IConstant.RequestHeaderName.SECURITY_TOKEN);
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
            response.setHeader(IConstant.ResponseHeaderName.REFRESHED_SECURITY_TOKEN, refreshSecurityToken);
            request.setAttribute(IConstant.RequestAttrName.REFRESHED_SECURITY_TOKEN, refreshSecurityToken);
        } catch (Exception e) {
            this.securityService.clearSecurityContextFromCurrentThread();
            throw new ServiceException(ResponseCode.SYSTEM_ERROR);
        }
        return true;
    }


}
