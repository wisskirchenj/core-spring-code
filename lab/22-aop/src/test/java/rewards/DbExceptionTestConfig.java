package rewards;

import config.AspectsConfig;
import config.RewardsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

@Configuration
@Import({RewardsConfig.class, AspectsConfig.class})
public class DbExceptionTestConfig {

    /**
     * Creates an in-memory "rewards" database populated
     * with test data for fast testing
     */
    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setName("rewards-dbexception")
                //	No scripts added.  This will cause an exception.
                .build();
    }

}
