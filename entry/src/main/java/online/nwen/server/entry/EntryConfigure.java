package online.nwen.server.entry;

import online.nwen.server.entry.interceptor.LocaleHandlerInterceptor;
import online.nwen.server.entry.interceptor.SecurityHandlerInterceptor;
import online.nwen.server.service.api.ILocaleService;
import online.nwen.server.service.api.ISecurityService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class EntryConfigure implements WebMvcConfigurer {
    private ISecurityService securityService;
    private ILocaleService localeService;

    EntryConfigure(ISecurityService securityService, ILocaleService localeService) {
        this.securityService = securityService;
        this.localeService = localeService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleHandlerInterceptor(this.localeService)).addPathPatterns("/**");
        registry.addInterceptor(new SecurityHandlerInterceptor(this.securityService)).addPathPatterns("/api/security/**");
    }
}
