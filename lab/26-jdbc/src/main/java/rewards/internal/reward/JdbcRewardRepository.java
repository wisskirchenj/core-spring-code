package rewards.internal.reward;

import common.datetime.SimpleDate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;

import java.util.Date;

/**
 * JDBC implementation of a reward repository that records the result
 * of a reward transaction by inserting a reward confirmation record.
 */
@Repository
public class JdbcRewardRepository implements RewardRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcRewardRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public RewardConfirmation confirmReward(AccountContribution contribution, Dining dining) {
		String sql = """
        insert into T_REWARD
           (CONFIRMATION_NUMBER, REWARD_AMOUNT, REWARD_DATE, ACCOUNT_NUMBER, DINING_MERCHANT_NUMBER, DINING_DATE, DINING_AMOUNT)
            values (?, ?, ?, ?, ?, ?, ?)""";
		String confirmationNumber = nextConfirmationNumber();

		// Update the T_REWARD table with the new Reward
		jdbcTemplate.update(sql, confirmationNumber, contribution.amount().asBigDecimal(),
				new Date(SimpleDate.today().inMilliseconds()), contribution.accountNumber(),
				dining.getMerchantNumber(), new Date(dining.date().inMilliseconds()), dining.getAmount().asBigDecimal());
		return new RewardConfirmation(confirmationNumber, contribution);
	}

	private String nextConfirmationNumber() {
		String sql = "select next value for S_REWARD_CONFIRMATION_NUMBER from DUAL_REWARD_CONFIRMATION_NUMBER";
		return jdbcTemplate.queryForObject(sql, String.class);
	}
}
