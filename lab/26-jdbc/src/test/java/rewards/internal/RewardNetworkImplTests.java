package rewards.internal;

import common.money.MonetaryAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.internal.account.AccountRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.RewardRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the RewardNetworkImpl application logic. Configures the implementation with stub repositories
 * containing dummy data for fast in-memory testing without the overhead of an external data source.
 * <p>
 * Besides helping catch bugs early, tests are a great way for a new developer to learn an API as he or she can see the
 * API in action. Tests also help validate a design as they are a measure for how easy it is to use your code.
 */
class RewardNetworkImplTests {

    /**
     * The object being tested.
     */
    RewardNetworkImpl rewardNetwork;

    @BeforeEach
    void setUp() {
        // create stubs to facilitate fast in-memory testing with dummy data and no external dependencies
        AccountRepository accountRepo = new StubAccountRepository();
        RestaurantRepository restaurantRepo = new StubRestaurantRepository();
        RewardRepository rewardRepo = new StubRewardRepository();

        // setup the object being tested by handing what it needs to work
        rewardNetwork = new RewardNetworkImpl(accountRepo, restaurantRepo, rewardRepo);
    }

    @Test
    void testRewardForDining() {
        // create a new dining of 100.00 charged to credit card '1234123412341234' by merchant '123457890' as test input
        Dining dining = Dining.createDining("100.00", "1234123412341234", "1234567890");

        // call the 'rewardNetwork' to test its rewardAccountFor(Dining) method
        RewardConfirmation confirmation = rewardNetwork.rewardAccountFor(dining);

        // assert the expected reward confirmation results
        assertNotNull(confirmation);
        assertNotNull(confirmation.confirmationNumber());

        // assert an account contribution was made
        AccountContribution contribution = confirmation.accountContribution();
        assertNotNull(contribution);

        // the account number should be '123456789'
        assertEquals("123456789", contribution.accountNumber());

        // the total contribution amount should be 8.00 (8% of 100.00)
        assertEquals(MonetaryAmount.valueOf("8.00"), contribution.amount());

        // the total contribution amount should have been split into 2 distributions
        assertEquals(2, contribution.distributions().size());

        // each distribution should be 4.00 (as both have a 50% allocation)
        assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Annabelle").amount());
        assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Corgan").amount());
    }
}