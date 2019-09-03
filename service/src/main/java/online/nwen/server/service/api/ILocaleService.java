package online.nwen.server.service.api;

import java.util.Locale;

public interface ILocaleService {
    Locale getLocaleFromCurrentThread();

    void setLocaleToCurrentThread(Locale locale);

    void clearLocaleFromCurrentThread();
}
