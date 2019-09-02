package online.nwen.server.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableScheduling
@EnableAsync
class ServiceConfigure {
    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate result = new TransactionTemplate(transactionManager);
        result.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return result;
    }
}
