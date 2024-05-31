package config;

import org.assertj.core.api.Fail;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import rewards.RewardNetwork;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JdbcAccountRepository;
import rewards.internal.restaurant.JdbcRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;

import java.lang.reflect.Field;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test the Spring configuration class to ensure it is creating the right
 * beans.
 */
class RewardsConfigTests {
	// Provide a mock for testing
	DataSource dataSource = Mockito.mock(DataSource.class);

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

	/**
	 * Ensure the data-source is set for the repository. Uses reflection as we do
	 * not wish to provide a getDataSource() method.
	 * 
	 * @param repository One of our three repositories.
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
