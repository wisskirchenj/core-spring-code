package rewards.internal.reward;

import common.datetime.SimpleDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * JDBC implementation of a reward repository that records the result of a reward transaction by inserting a reward
 * confirmation record.
 */
@Repository
@Profile("jdbc")
public class JdbcRewardRepository implements RewardRepository {

	private DataSource dataSource;

	/**
	 * Sets the data source this repository will use to insert rewards.
	 * @param dataSource the data source
	 */
    @Autowired
    public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public RewardConfirmation updateReward(AccountContribution contribution, Dining dining) {
		String sql = "insert into T_REWARD (CONFIRMATION_NUMBER, REWARD_AMOUNT, REWARD_DATE, ACCOUNT_NUMBER, DINING_MERCHANT_NUMBER, DINING_DATE, DINING_AMOUNT) values (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String confirmationNumber = nextConfirmationNumber();
            ps.setString(1, confirmationNumber);
            ps.setBigDecimal(2, contribution.amount().asBigDecimal());
            ps.setDate(3, new Date(SimpleDate.today().inMilliseconds()));
            ps.setString(4, contribution.accountNumber());
            ps.setString(5, dining.getMerchantNumber());
            ps.setDate(6, new Date(dining.date().inMilliseconds()));
            ps.setBigDecimal(7, dining.getAmount().asBigDecimal());
            ps.execute();
            return new RewardConfirmation(confirmationNumber, contribution);
        } catch (SQLException e) {
            throw new RuntimeException("SQL exception occurred inserting reward record", e);
        }
    }

	private String nextConfirmationNumber() {
		String sql = "select next value for S_REWARD_CONFIRMATION_NUMBER from DUAL_REWARD_CONFIRMATION_NUMBER";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException("SQL exception getting next confirmation number", e);
        }
    }
}