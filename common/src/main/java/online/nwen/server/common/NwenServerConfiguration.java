package online.nwen.server.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "nwen-server")
@Component
public class NwenServerConfiguration {
}
