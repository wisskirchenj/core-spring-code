package rewards;

import java.io.Serializable;

/**
 * A summary of a confirmed reward transaction describing a contribution made to an account that was distributed among
 * the account's beneficiaries.
 */
public record RewardConfirmation(String confirmationNumber,
								 AccountContribution accountContribution) implements Serializable {

	public String toString() {
		return confirmationNumber;
	}
}