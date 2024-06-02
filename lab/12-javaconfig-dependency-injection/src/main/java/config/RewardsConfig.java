package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewards.RewardNetwork;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JdbcAccountRepository;
import rewards.internal.restaurant.JdbcRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;

import javax.sql.DataSource;

@Configuration
public class RewardsConfig {

	// Set this by adding a constructor.
	private final DataSource dataSource;

    public RewardsConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
	RewardNetwork rewardNetwork(AccountRepository accountRepository, RestaurantRepository restaurantRepository,
								RewardRepository rewardRepository) {
        return new RewardNetworkImpl(accountRepository, restaurantRepository, rewardRepository);
    }

	@Bean
	AccountRepository accountRepository() {
		var repo = new JdbcAccountRepository();
		repo.setDataSource(dataSource);
		return repo;
	}

	@Bean
	RestaurantRepository restaurantRepository() {
		var repo = new JdbcRestaurantRepository();
		repo.setDataSource(dataSource);
		return repo;
	}

	@Bean
	RewardRepository rewardRepository() {
		var repo = new JdbcRewardRepository();
		repo.setDataSource(dataSource);
		return repo;		}

}
