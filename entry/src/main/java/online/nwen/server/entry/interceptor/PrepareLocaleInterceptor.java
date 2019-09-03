package online.nwen.server.entry.interceptor;

import online.nwen.server.service.api.ILocaleService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class PrepareLocaleInterceptor implements HandlerInterceptor {
    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    private ILocaleService localeService;

    public PrepareLocaleInterceptor(ILocaleService localeService) {
        this.localeService = localeService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String acceptLanguageHeader = request.getHeader(ACCEPT_LANGUAGE_HEADER);
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        if (acceptLanguageHeader != null) {
            locale = new Locale(acceptLanguageHeader);
        }
        this.localeService.setLocaleToCurrentThread(locale);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        this.localeService.clearLocaleFromCurrentThread();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        this.localeService.clearLocaleFromCurrentThread();
    }
}
