package rewards;

import java.util.Set;

import common.money.MonetaryAmount;
import common.money.Percentage;

/**
 * A summary of a monetary contribution made to an account that was distributed among the account's beneficiaries.
 * <p>
 * A value object. Immutable.
 */
public record AccountContribution(String accountNumber, MonetaryAmount amount, Set<Distribution> distributions) {

	/**
	 * Returns how this contribution was distributed to a single account beneficiary.
	 *
	 * @param beneficiary the name of the beneficiary e.g "Annabelle"
	 * @return a summary of how the contribution amount was distributed to the beneficiary
	 */
	public Distribution getDistribution(String beneficiary) {
		for (Distribution d : distributions) {
			if (d.beneficiary.equals(beneficiary)) {
				return d;
			}
		}
		throw new IllegalArgumentException("No such distribution for '" + beneficiary + "'");
	}

	/**
	 * A single distribution made to a beneficiary as part of an account contribution, summarizing the distribution
	 * amount and resulting total beneficiary savings.
	 * <p>
	 * A value object.
	 */
	public record Distribution(String beneficiary, MonetaryAmount amount, Percentage percentage,
							   MonetaryAmount totalSavings) {

		public String toString() {
			return amount + " to '" + beneficiary + "' (" + percentage + ")";
		}
	}

	public String toString() {
		return "Contribution of " + amount + " to account '" + accountNumber + "' distributed " + distributions;
	}
}