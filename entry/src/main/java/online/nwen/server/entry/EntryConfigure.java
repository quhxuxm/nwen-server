package online.nwen.server.entry;

import online.nwen.server.entry.interceptor.LoadSecurityContextInterceptor;
import online.nwen.server.entry.interceptor.PrepareLocaleInterceptor;
import online.nwen.server.entry.interceptor.SecurityCheckInterceptor;
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
        registry.addInterceptor(new SecurityCheckInterceptor(this.securityService)).order(100).addPathPatterns("/api/security/**");
        registry.addInterceptor(new PrepareLocaleInterceptor(this.localeService)).order(101).addPathPatterns("/api/**");
        registry.addInterceptor(new LoadSecurityContextInterceptor(this.securityService)).order(102).addPathPatterns("/api/**");
    }
}
