package ch.javasoft.decimal.op;

import java.math.BigDecimal;

import ch.javasoft.decimal.Decimal;
import ch.javasoft.decimal.arithmetic.DecimalArithmetics;
import ch.javasoft.decimal.scale.ScaleMetrics;

/**
 * Base class for tests comparing the result of some binary operation of the
 * {@link Decimal} with the expected result produced by the equivalent operation
 * of the {@link BigDecimal}. The test operand values created based on random
 * long values.
 */
abstract public class AbstractTwoAryDecimalToDecimalTest extends
		AbstractDecimalVersusBigDecimalTest {

	/**
	 * Constructor with arithemtics determining scale, rounding mode and
	 * overflow policy.
	 * 
	 * @param arithmetics
	 *            the arithmetics determining scale, rounding mode and overlfow
	 *            policy
	 */
	public AbstractTwoAryDecimalToDecimalTest(DecimalArithmetics arithmetics) {
		super(arithmetics);
	}

	abstract protected BigDecimal expectedResult(BigDecimal a, BigDecimal b);

	abstract protected <S extends ScaleMetrics> Decimal<S> actualResult(Decimal<S> a, Decimal<S> b);
	
	@Override
	protected <S extends ScaleMetrics> void runRandomTest(S scaleMetrics, int index) {
		final Decimal<S> dOpA = randomDecimal(scaleMetrics);
		final Decimal<S> dOpB = randomDecimal(scaleMetrics);
		runTest(scaleMetrics, "[" + index + "]", dOpA, dOpB);
	}

	@Override
	protected <S extends ScaleMetrics> void runSpecialValueTest(S scaleMetrics) {
		final long[] specialValues = getSpecialValues(scaleMetrics);
		for (int i = 0; i < specialValues.length; i++) {
			for (int j = 0; j < specialValues.length; j++) {
				final Decimal<S> dOpA = newDecimal(scaleMetrics, specialValues[i]);
				final Decimal<S> dOpB = newDecimal(scaleMetrics, specialValues[j]);
				runTest(scaleMetrics, "[" + i + ", " + j + "]", dOpA, dOpB);
			}
		}
		
	}

	private <S extends ScaleMetrics> void runTest(S scaleMetrics, String name, Decimal<S> dOpA, Decimal<S> dOpB) {
		final BigDecimal bdOpA = toBigDecimal(dOpA);
		final BigDecimal bdOpB = toBigDecimal(dOpB);

		//expected
		ArithmeticResult<Long> expected;
		try {
			final BigDecimal exp = expectedResult(bdOpA, bdOpB).setScale(scaleMetrics.getScale(), arithmetics.getRoundingMode());
			final long expUnscaled = arithmetics.getOverflowMode().isChecked() ? exp.unscaledValue().longValueExact() : exp.unscaledValue().longValue();
			expected = ArithmeticResult.forResult(exp.toPlainString(), expUnscaled);
		} catch (ArithmeticException e) {
			expected = ArithmeticResult.forException(e);
		}

		//actual
		ArithmeticResult<Long> actual;
		try {
			final Decimal<S> act = actualResult(dOpA, dOpB);
			actual = ArithmeticResult.forResult(act.toString(), act.unscaledValue());
		} catch (ArithmeticException e) {
			actual = ArithmeticResult.forException(e);
		}

		//assert
		actual.assertEquivalentTo(expected, getClass().getSimpleName() + name + ": " + dOpA + " " + operation() + " " + dOpB);
	}
}
