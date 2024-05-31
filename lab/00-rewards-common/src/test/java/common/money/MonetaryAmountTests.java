package common.money;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

/**
 * Unit tests that make sure the MonetaryAmount class works in isolation.
 */
class MonetaryAmountTests {
	@Test
	void testMonetaryAmountValueOfString() {
		MonetaryAmount amount = MonetaryAmount.valueOf("$100");
		assertEquals("$100.00", amount.toString());
	}

	@Test
	void testMonetaryCreation() {
		MonetaryAmount amt = MonetaryAmount.valueOf("100.00");
		assertEquals("$100.00", amt.toString());
	}

	@Test
	void testMonetaryAdd() {
		MonetaryAmount amt1 = MonetaryAmount.valueOf("100.00");
		MonetaryAmount amt2 = MonetaryAmount.valueOf("100.00");
		assertEquals(MonetaryAmount.valueOf("200.00"), amt1.add(amt2));
		assertEquals("$200.00", amt1.add(amt2).toString());
	}

	@Test
	void testMultiplyByPercentage() {
		MonetaryAmount amt = MonetaryAmount.valueOf("100.005");
		assertEquals(MonetaryAmount.valueOf("8.00"), amt.multiplyBy(Percentage.valueOf("8%")));
	}

	@Test
	void testMultiplyByDecimal() {
		MonetaryAmount amt = MonetaryAmount.valueOf("100.005");
		assertEquals(MonetaryAmount.valueOf("8.00"), amt.multiplyBy(new BigDecimal("0.08")));
	}

	@Test
	void testDivideByMonetaryAmount() {
		MonetaryAmount amt = MonetaryAmount.valueOf("100.005");
		assertEquals(new BigDecimal("12.5"), amt.divide(MonetaryAmount.valueOf("8.00")));
	}

	@Test
	void testDivideByDecimal() {
		MonetaryAmount amt = MonetaryAmount.valueOf("100.005");
		assertEquals(MonetaryAmount.valueOf("8.00"), amt.divideBy(new BigDecimal("12.5")));
	}

	@Test
	void testDoubleEquality() {
		MonetaryAmount amt = MonetaryAmount.valueOf(".1");
		assertEquals(new BigDecimal(".10"), amt.asBigDecimal());
	}
}
