package rewards.internal.reward;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Tests the JDBC reward repository with a test data source to test repository
 * behavior and verifies the Reward JDBC code is correct.
 */
public class JdbcRewardRepositoryTests extends AbstractRewardRepositoryTests {

	@BeforeEach
	public void setUp() {
		dataSource = createTestDataSource();
		rewardRepository = new JdbcRewardRepository(dataSource);
	}

	@Test
	@Override
	public void testProfile() {
        assertInstanceOf(JdbcRewardRepository.class, rewardRepository, "JDBC expected");
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder().setName("rewards")
				.addScript("/rewards/testdb/schema.sql")
				.addScript("/rewards/testdb/data.sql").build();
	}
}
