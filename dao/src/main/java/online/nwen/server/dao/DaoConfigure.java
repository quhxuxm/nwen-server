package online.nwen.server.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableCaching
@EnableTransactionManagement
class DaoConfigure {
    @Value("${nwen-server.dataSource.url}")
    private String dataSourceUrl;
    @Value("${nwen-server.dataSource.username}")
    private String dataSourceUserName;
    @Value("${nwen-server.dataSource.password}")
    private String dataSourcePassword;
    @Value("${nwen-server.dataSource.driver-class-name}")
    private String dataSourceDriverClassName;
    @Value("${nwen-server.dataSource.max-total}")
    private Integer dataSourceMaxTotal;
    @Value("${nwen-server.dataSource.max-idle}")
    private Integer dataSourceMaxIdle;
    @Value("${nwen-server.dataSource.min-idle}")
    private Integer dataSourceMinIdle;
    @Value("${nwen-server.dataSource.initial-size}")
    private Integer dataSourceInitialSize;
    @Value("${nwen-server.dataSource.validation-query}")
    private String dataSourceValidationQuery;
    @Value("${nwen-server.dataSource.max-wait-millis}")
    private Long dataSourceMaxWaitMillis;
    @Value("${nwen-server.dataSource.test-while-idle}")
    private boolean dataSourceTestWhileIdle;
    @Value("${nwen-server.dataSource.test-on-borrow}")
    private boolean dataSourceTestOnBorrow;
    @Value("${nwen-server.dataSource.test-on-return}")
    private boolean dataSourceTestOnReturn;
    @Value("${nwen-server.dataSource.min-evictable-idle-time-millis}")
    private long dataSourceMinEvictableIdleTimeMillis;

    @Bean
    DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(this.dataSourceUrl);
        dataSource.setUsername(this.dataSourceUserName);
        dataSource.setPassword(this.dataSourcePassword);
        dataSource.setDriverClassName(this.dataSourceDriverClassName);
        dataSource.setMaxIdle(this.dataSourceMaxIdle);
        dataSource.setMinIdle(this.dataSourceMinIdle);
        dataSource.setMaxTotal(this.dataSourceMaxTotal);
        dataSource.setInitialSize(this.dataSourceInitialSize);
        dataSource.setValidationQuery(this.dataSourceValidationQuery);
        dataSource.setMaxWaitMillis(this.dataSourceMaxWaitMillis);
        dataSource.setMinEvictableIdleTimeMillis(this.dataSourceMinEvictableIdleTimeMillis);
        dataSource.setTestOnReturn(this.dataSourceTestOnReturn);
        dataSource.setTestOnBorrow(this.dataSourceTestOnBorrow);
        dataSource.setTestWhileIdle(this.dataSourceTestWhileIdle);
        return dataSource;
    }
}
