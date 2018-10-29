package online.nwen.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
@EnableWebMvc
public class WebConfigure implements WebMvcConfigurer {
    private static final String ROOT_URL_PATTERN = "/**";
    private static final String ROOT_URL = "/";
    private static final String UI_URL = "/ui/";
    private static final String[] STATIC_RESOURCE_LOCATIONS = {"classpath:static/"};
    private static final String UI_INDEX_PAGE = "/ui/index.html";

    /**
     * Register the static resource.
     *
     * @param registry The static resource registry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(ROOT_URL_PATTERN)
                .addResourceLocations(STATIC_RESOURCE_LOCATIONS)
                .resourceChain(true)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy(ROOT_URL_PATTERN));
    }

    /**
     * Redirect the ui request to the main html(index.html) of client side.
     * After redirect the angular engine will be loaded and running.
     *
     * @param registry The view registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //Redirect the root to index.html
        registry.addRedirectViewController(ROOT_URL, UI_INDEX_PAGE);
        //Redirect the "/ui/#/**" to index.html
        registry.addRedirectViewController(UI_URL,
                UI_INDEX_PAGE);
    }
}
