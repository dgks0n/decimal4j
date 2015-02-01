package org.decimal4j.arithmetic;

/**
 * Helper class used by multiplication to handle some special cases.
 */
enum SpecialMultiplicationResult {
	/**
	 * {@code a*b} with {@code a==0} or {@code b==0} leading to {@code 0}
	 */
	FACTOR_IS_ZERO {
		@Override
		long multiply(DecimalArithmetics arithmetics, long uDecimal1, long uDecimal2) {
			return 0;
		}
	},
	/**
	 * {@code a*b} with {@code a==1} leading to {@code b}
	 */
	FACTOR_1_IS_ONE {
		@Override
		long multiply(DecimalArithmetics arithmetics, long uDecimal1, long uDecimal2) {
			return uDecimal2;
		}
	},
	/**
	 * {@code a*b} with {@code b==1} leading to {@code a}
	 */
	FACTOR_2_IS_ONE {
		@Override
		long multiply(DecimalArithmetics arithmetics, long uDecimal1, long uDecimal2) {
			return uDecimal1;
		}
	},
	/**
	 * {@code a*b} with {@code a==-1} leading to {@code -b}
	 */
	FACTOR_1_IS_MINUS_ONE {
		@Override
		long multiply(DecimalArithmetics arithmetics, long uDecimal1, long uDecimal2) {
			return arithmetics.negate(uDecimal2);//we must go through arithmetics because overflow is possible
		}
	},
	/**
	 * {@code a*b} with {@code b==-1} leading to {@code -a}
	 */
	FACTOR_2_IS_MINUS_ONE {
		@Override
		long multiply(DecimalArithmetics arithmetics, long uDecimal1, long uDecimal2) {
			return arithmetics.negate(uDecimal1);//we must go through arithmetics because overflow is possible
		}
	},
	/**
	 * {@code a*b} with {@code a==b} leading to {@code a^2}
	 */
	FACTORS_ARE_EQUAL {
		@Override
		long multiply(DecimalArithmetics arithmetics, long uDecimal1, long uDecimal2) {
			return arithmetics.square(uDecimal1);
		}
	};
	abstract long multiply(DecimalArithmetics arithmetics, long uDecimal1, long uDecimal2);

	/**
	 * Returns the special multiplication case if it is one and null otherwise.
	 * 
	 * @param arithmetics
	 *            the arithmetics object
	 * @param uDecimal1
	 *            the first factor
	 * @param uDecimal2
	 *            the second factor
	 * @return special case if found one and null otherwise
	 */
	static SpecialMultiplicationResult getFor(DecimalArithmetics arithmetics, long uDecimal1, long uDecimal2) {
		if (uDecimal1 == 0 | uDecimal2 == 0) {
			return FACTOR_IS_ZERO;
		}
		final long one = arithmetics.one();
		if (uDecimal1 == one) {
			return FACTOR_1_IS_ONE;
		}
		if (uDecimal2 == one) {
			return FACTOR_2_IS_ONE;
		}
		if (uDecimal1 == -one) {
			return FACTOR_1_IS_MINUS_ONE;
		}
		if (uDecimal2 == -one) {
			return FACTOR_2_IS_MINUS_ONE;
		}
		if (uDecimal1 == uDecimal2) {
			return FACTORS_ARE_EQUAL;
		}
		return null;
	}
}