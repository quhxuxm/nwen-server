package online.nwen.server.service.impl;

import online.nwen.server.service.api.ILocaleService;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
class LocaleServiceImpl implements ILocaleService {
    private static final ThreadLocal<Locale> LOCALE_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public Locale getLocaleFromCurrentThread() {
        return LOCALE_THREAD_LOCAL.get();
    }

    @Override
    public void setLocaleToCurrentThread(Locale locale) {
        LOCALE_THREAD_LOCAL.set(locale);
    }

    @Override
    public void clearLocaleFromCurrentThread() {
        LOCALE_THREAD_LOCAL.remove();
    }
}
