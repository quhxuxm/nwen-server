package online.nwen.server.entry.interceptor;

import online.nwen.server.bo.ResponseCode;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            this.securityService.clearSecurityToken();
            throw new ServiceException(ResponseCode.SECURITY_TOKEN_IS_EMPTY);
        }
        try {
            this.securityService.verifyJwtToken(securityToken);
        } catch (ServiceException e) {
            if (e.getResponseCode() != ResponseCode.SECURITY_TOKEN_EXPIRED) {
                throw e;
            }
        } catch (Exception e) {
            this.securityService.clearSecurityToken();
        }
        this.securityService.setSecurityToken(securityToken);
        return true;
    }
}
