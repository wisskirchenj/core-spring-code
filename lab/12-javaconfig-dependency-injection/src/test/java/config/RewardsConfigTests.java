package config;

import org.assertj.core.api.Fail;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test the Spring configuration class to ensure it is creating the right
 * beans.
 */
@SuppressWarnings("unused")
class RewardsConfigTests {
	// Provide a mock object for testing
	DataSource dataSource = Mockito.mock(DataSource.class);

	// TODO-05: Run the test
	// - Uncomment the code below between /* and */
	// - If you have implemented RewardsConfig as requested it should compile.
	// - Fix RewardsConfig if necessary.
	// - Now run the test, it should pass.

	/*
	RewardsConfig rewardsConfig = new RewardsConfig(dataSource);

	@Test
	void getBeans() {
		RewardNetwork rewardNetwork = rewardsConfig.rewardNetwork();
        assertInstanceOf(RewardNetworkImpl.class, rewardNetwork);

		AccountRepository accountRepository = rewardsConfig.accountRepository();
        assertInstanceOf(JdbcAccountRepository.class, accountRepository);
		checkDataSource(accountRepository);

		RestaurantRepository restaurantRepository = rewardsConfig.restaurantRepository();
        assertInstanceOf(JdbcRestaurantRepository.class, restaurantRepository);
		checkDataSource(restaurantRepository);

		RewardRepository rewardsRepository = rewardsConfig.rewardRepository();
        assertInstanceOf(JdbcRewardRepository.class, rewardsRepository);
		checkDataSource(rewardsRepository);
	}
	*/

	/**
	 * Ensure the data-source is set for the repository. Uses reflection as we do
	 * not wish to provide a getDataSource() method.
	 * 
	 * @param repository One of our three repositories.
	 *
	 */
	private void checkDataSource(Object repository) {
		Class<?> repositoryClass = repository.getClass();

		try {
			Field source = repositoryClass.getDeclaredField("dataSource");
			source.setAccessible(true);
			assertNotNull(source.get(repository));
		} catch (Exception e) {
			String failureMessage = "Unable to validate dataSource in " + repositoryClass.getSimpleName();
			System.out.println(failureMessage);
			e.printStackTrace();
			Fail.fail(failureMessage);
		}
	}
}
