package yamsroun.batch05;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.DatabaseType;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class CustomBatchConfigurer extends DefaultBatchConfigurer {

    //@Qualifier("repositoryDataSource")
    private final DataSource dataSource;

    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
        factoryBean.setDatabaseType(DatabaseType.MYSQL.getProductName());
        factoryBean.setTablePrefix("FOO_");
        factoryBean.setIsolationLevelForCreate("ISOLATION_REPEATABLE_READ");
        factoryBean.setDataSource(dataSource);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }
}
