package online.nwen.server.entry;

import online.nwen.server.entry.interceptor.SecurityHandlerInterceptor;
import online.nwen.server.service.api.ISecurityService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class EntryConfigure implements WebMvcConfigurer {
    private ISecurityService securityService;

    EntryConfigure(ISecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityHandlerInterceptor(this.securityService)).addPathPatterns("/api/security/**");
    }
}
