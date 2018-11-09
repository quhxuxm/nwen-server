package online.nwen.server.service.impl;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheEventLogger implements CacheEventListener<Object, Object> {
    private static final Logger logger = LoggerFactory.getLogger(CacheEventLogger.class);

    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        logger.debug("Cache event: {} , Key: {} old value: {}, new value: {}", event.getType(), event.getKey(),
                event.getOldValue(), event.getNewValue());
    }
}
