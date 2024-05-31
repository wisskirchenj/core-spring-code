package rewards;

import java.io.Serializable;

import common.datetime.SimpleDate;
import common.money.MonetaryAmount;

/**
 * A dining event that occurred, representing a charge made to an credit card by a merchant on a specific date.
 * For a dining to be eligible for reward, the credit card number should map to an account in the reward network. In
 * addition, the merchant number should map to a restaurant in the network.
 * A value object. Immutable.
 */
public record Dining(MonetaryAmount amount, String creditCardNumber, String merchantNumber,
					 SimpleDate date) implements Serializable {


	/**
	 * Creates a new dining, reflecting an amount that was charged to a credit card by a merchant on today's date. A
	 * convenient static factory method.
	 *
	 * @param amount           the total amount of the dining bill as a string
	 * @param creditCardNumber the number of the credit card used to pay for the dining bill
	 * @param merchantNumber   the merchant number of the restaurant where the dining occurred
	 * @return the dining event
	 */
	public static Dining createDining(String amount, String creditCardNumber, String merchantNumber) {
		return new Dining(MonetaryAmount.valueOf(amount), creditCardNumber, merchantNumber, SimpleDate.today());
	}

	/**
	 * Creates a new dining, reflecting an amount that was charged to a credit card by a merchant on the date specified.
	 * A convenient static factory method.
	 *
	 * @param amount           the total amount of the dining bill as a string
	 * @param creditCardNumber the number of the credit card used to pay for the dining bill
	 * @param merchantNumber   the merchant number of the restaurant where the dining occurred
	 * @param month            the month of the dining event
	 * @param day              the day of the dining event
	 * @param year             the year of the dining event
	 * @return the dining event
	 */
	public static Dining createDining(String amount, String creditCardNumber, String merchantNumber, int month,
									  int day, int year) {
		return new Dining(MonetaryAmount.valueOf(amount), creditCardNumber, merchantNumber, new SimpleDate(month, day,
				year));
	}

	public String toString() {
		return "Dining of " + amount + " charged to '" + creditCardNumber + "' by '" + merchantNumber + "' on " + date;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public String getMerchantNumber() {
		return merchantNumber;
	}

	public MonetaryAmount getAmount() {
		return amount;
	}
}