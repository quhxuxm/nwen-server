package online.nwen.server.entry.interceptor;

import online.nwen.server.common.constant.IConstant;
import online.nwen.server.service.api.ISecurityService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadSecurityContextInterceptor implements HandlerInterceptor {
    private ISecurityService securityService;

    public LoadSecurityContextInterceptor(ISecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String refreshedSecurityToken = (String) request.getAttribute(IConstant.RequestAttrName.REFRESHED_SECURITY_TOKEN);
        if (refreshedSecurityToken != null) {
            this.securityService.setSecurityContextToCurrentThread(this.securityService.parseJwtToken(refreshedSecurityToken));
            return true;
        }
        String securityToken = request.getHeader(IConstant.RequestHeaderName.SECURITY_TOKEN);
        if (securityToken != null) {
            this.securityService.setSecurityContextToCurrentThread(this.securityService.parseJwtToken(securityToken));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        this.securityService.clearSecurityContextFromCurrentThread();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        this.securityService.clearSecurityContextFromCurrentThread();
    }
}
