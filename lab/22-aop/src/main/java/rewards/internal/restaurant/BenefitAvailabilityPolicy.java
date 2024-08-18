package rewards.internal.restaurant;

import rewards.Dining;
import rewards.internal.account.Account;

/**
 * Determines if benefit is available for an account for dining.
 * <p>
 * A value object. A strategy. Scoped by the Resturant aggregate.
 */
public interface BenefitAvailabilityPolicy {

    /**
     * Calculates if an account is eligible to receive benefits for a dining.
     *
     * @param account the account of the member who dined
     * @param dining  the dining event
     * @return benefit availability status
     */
    boolean isBenefitAvailableFor(Account account, Dining dining);
}
