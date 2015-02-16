package org.decimal4j.api;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import org.decimal4j.scale.ScaleMetrics;
import org.decimal4j.truncate.OverflowMode;
import org.decimal4j.truncate.TruncationPolicy;

/**
 * <tt>DecimalArithmetic</tt> defines the basic native operations for
 * {@link Decimal} numbers for one particular combination of {@link #getScale()
 * scale}, {@link #getRoundingMode() rounding mode} and
 * {@link #getOverflowMode() overflow mode}. Native here means that
 * <tt>Decimal</tt> values are simply represented by their underlying unscaled
 * <tt>long</tt> value. All operations therefore use unscaled longs for
 * <tt>Decimal</tt> arguments and return longs for <tt>Decimal</tt> number
 * results.
 * <p>
 * Application code does not usually need to use <tt>DecimalArithmetic</tt>
 * directly. It may be appropriate however for very specialized applications
 * with low latency, high frequency or zero garbage requirements. All operations 
 * of <tt>DecimalArithmetic</tt> do not allocate any objects (zero garbage) unless
 * otherwise indicated.
 */
public interface DecimalArithmetic {
	/**
	 * Returns the scale {@code f} applied to all unscaled decimal values passed
	 * to and returned by this {@code DecimalArithmetic}. Corresponds to the
	 * number of digits to the right of the decimal point (cannot be negative).
	 * <p>
	 * A given {@link Decimal} value multiplied with <code>10<sup>f</sup></code>
	 * results in an unscaled long value. Conversely, a {@code Decimal} value
	 * {@code d} can be computed from the unscaled value {@code u} as
	 * <code>d = u*10<sup>-f</sup></code>.
	 * 
	 * @return the non-negative scale {@code f} applied to unscaled decimal
	 *         values within this {@code DecimalArithmetic} object
	 * @see ScaleMetrics#getScale()
	 */
	int getScale();

	/**
	 * Returns the scale metrics associated with this decimal arithmetic object.
	 * 
	 * @return the scale metrics
	 */
	ScaleMetrics getScaleMetrics();

	/**
	 * Returns the <i>rounding mode</i> applied to operations of this
	 * {@code DecimalArithmetic} object if rounding is necessary.
	 * 
	 * @return the rounding mode applied to operations of this
	 *         {@code DecimalArithmetic} object if rounding is necessary
	 */
	RoundingMode getRoundingMode();

	/**
	 * Returns the <i>overflow mode</i> applied to operations of this
	 * {@code DecimalArithmetic} object if an overflow occurs. The overflow mode
	 * defines whether an operation should throw an exception if an overflow
	 * occurs.
	 * 
	 * @return the overflow mode applied to operations of this
	 *         {@code DecimalArithmetic} object if an overflow occurs
	 */
	OverflowMode getOverflowMode();

	/**
	 * Returns the <i>truncation policy</i> defining how to handle truncation
	 * due to overflow or rounding. The {@code TruncationPolicy} is defined by
	 * the {@link #getOverflowMode() overflow mode} and the
	 * {@link #getRoundingMode() rounding mode}.
	 * 
	 * @return the truncation policy defining how this {@code DecimalArithmetic}
	 *         handles truncation
	 */
	TruncationPolicy getTruncationPolicy();

	/**
	 * Returns the unscaled decimal for the decimal value {@code 1}. One is the
	 * value <code>10<sup>scale</sup></code> which is also the multiplier used
	 * to get the unscaled decimal from the true decimal value.
	 * 
	 * @return the unscaled decimal representing the decimal value 1
	 */
	long one();

	/**
	 * Returns the signum function of the specified unscaled decimal.
	 * 
	 * @param uDecimal
	 *            the unscaled decimal
	 * @return -1, 0, or 1 as the value of the specified unscaled decimal is
	 *         negative, zero, or positive.
	 */
	int signum(long uDecimal);

	/**
	 * Compares two unscaled decimal values numerically.
	 * 
	 * @param uDecimal1
	 *            the first unscaled decimal to compare
	 * @param uDecimal2
	 *            the second unscaled decimal to compare
	 * @return the value {@code 0} if {@code unscaled1 == unscaled2}; a value
	 *         less than {@code 0} if {@code unscaled1 < unscaled2}; and a value
	 *         greater than {@code 0} if {@code unscaled1 > unscaled2}
	 */
	int compare(long uDecimal1, long uDecimal2);

	/**
	 * Returns an unscaled decimal whose value is
	 * {@code (uDecimal1 + uDecimal2)}.
	 * 
	 * @param uDecimal1
	 *            first unscaled decimal value to be added
	 * @param uDecimal2
	 *            second unscaled decimal value to be added
	 * @return {@code uDecimal1 + uDecimal2}
	 * @throws ArithmeticException
	 *             if an overflow occurs and the {@link #getOverflowMode()
	 *             overflow mode} is set to throw an exception
	 */
	long add(long uDecimal1, long uDecimal2);

	/**
	 * Returns an unscaled decimal whose value is {@code (uDecimalMinuend -
	 * uDecimalSubtrahend)}.
	 * 
	 * @param uDecimalMinuend
	 *            unscaled decimal value to be subtracted from
	 * @param uDecimalSubtrahend
	 *            unscaled decimal value to subtract from the minuend
	 * @return {@code uDecimalMinuend - uDecimalSubtrahend}
	 * @throws ArithmeticException
	 *             if an overflow occurs and the {@link #getOverflowMode()
	 *             overflow mode} is set to throw an exception
	 */
	long subtract(long uDecimalMinuend, long uDecimalSubtrahend);

	/**
	 * Returns an unscaled decimal whose value is <tt>(uDecimal1 &times;
	 * uDecimal2)</tt>.
	 * 
	 * @param uDecimal1
	 *            first unscaled decimal value to be multiplied
	 * @param uDecimal2
	 *            second unscaled decimal value to be multiplied
	 * @return {@code uDecimal1 * uDecimal2}
	 * @throws ArithmeticException
	 *             if an overflow occurs and the {@link #getOverflowMode()
	 *             overflow mode} is set to throw an exception
	 */
	long multiply(long uDecimal1, long uDecimal2);

	/**
	 * Returns an unscaled decimal whose value is <tt>(uDecimal &times;
	 * lValue)} where the second argument is a true long value instead of an unscaled decimal.
	 * 
	 * @param uDecimal
	 *            unscaled decimal value to be multiplied
	 * @param lValue
	 *            long value to be multiplied
	 * @return {@code uDecimal * value}
	 * @throws ArithmeticException
	 *             if an overflow occurs and the {@link #getOverflowMode()
	 *             overflow mode} is set to throw an exception
	 */
	long multiplyByLong(long uDecimal, long lValue);

	/**
	 * Returns an unscaled decimal whose value is
	 * <code>(uDecimal &times; 10<sup>n</sup>)</code>.
	 * <p>
	 * The power, {@code n}, may be negative, in which case this method performs
	 * a multiplication by a power of ten. If rounding must be performed (for
	 * negative n), this arithmetic's {@link #getRoundingMode() rounding mode}
	 * is applied.
	 * 
	 * @param uDecimal
	 *            value to be divided.
	 * @param n
	 *            the power of ten
	 * @return <code>(uDecimal &times; 10<sup>n</sup>)</code> as unscaled
	 *         decimal
	 * @throws ArithmeticException
	 *             if an overflow occurs and the {@link #getOverflowMode()
	 *             overflow mode} is set to throw an exception
	 */
	long multiplyByPowerOf10(long uDecimal, int n);

	/**
	 * Returns an unscaled decimal whose value is
	 * {@code (uDecimalDividend / uDecimalDivisor)}. If rounding must be
	 * performed, this arithmetic's {@link #getRoundingMode() rounding mode} is
	 * applied.
	 * 
	 * @param uDecimalDividend
	 *            value to be divided.
	 * @param uDecimalDivisor
	 *            value by which the dividend is to be divided.
	 * @return {@code uDecimalDividend / uDecimalDivisor} as unscaled decimal
	 * @throws ArithmeticException
	 *             if {@code uDecimalDividend} is zero, if
	 *             {@link #getRoundingMode() rounding mode} is UNNECESSARY and
	 *             rounding is necessary or if an overflow occurs and the
	 *             {@link #getOverflowMode() overflow mode} is set to throw an
	 *             exception
	 */
	long divide(long uDecimalDividend, long uDecimalDivisor);

	/**
	 * Returns an unscaled decimal whose value is
	 * {@code (uDecimalDividend / lDivisor)} where the second argument is a true
	 * long value instead of an unscaled decimal. If rounding must be performed,
	 * this arithmetic's {@link #getRoundingMode() rounding mode} is applied.
	 * 
	 * @param uDecimalDividend
	 *            value to be divided.
	 * @param lDivisor
	 *            long value by which the dividend is to be divided.
	 * @return {@code uDecimalDividend / lDivisor} as unscaled decimal
	 * @throws ArithmeticException
	 *             if {@code uDecimalDividend} is zero, if
	 *             {@link #getRoundingMode() rounding mode} is UNNECESSARY and
	 *             rounding is necessary or if an overflow occurs and the
	 *             {@link #getOverflowMode() overflow mode} is set to throw an
	 *             exception
	 */
	long divideByLong(long uDecimalDividend, long lDivisor);

	/**
	 * Returns an unscaled decimal whose value is
	 * <code>(uDecimal / 10<sup>n</sup>)</code>. If rounding must be performed,
	 * this arithmetic's {@link #getRoundingMode() rounding mode} is applied.
	 * <p>
	 * The power, {@code n}, may be negative, in which case this method performs
	 * a multiplication by a power of ten.
	 * 
	 * @param uDecimal
	 *            value to be divided.
	 * @param n
	 *            the power of ten
	 * @return <code>(uDecimal / 10<sup>n</sup>)</code> as unscaled decimal
	 * @throws ArithmeticException
	 *             if {@link #getRoundingMode() rounding mode} is UNNECESSARY
	 *             and rounding is necessary or if an overflow occurs and the
	 *             {@link #getOverflowMode() overflow mode} is set to throw an
	 *             exception
	 */
	long divideByPowerOf10(long uDecimal, int n);

	/**
	 * Returns the average of {@code uDecimal1} and {@code uDecimal2}. The
	 * method is designed to avoid overflows. If rounding must be performed,
	 * this arithmetic's {@link #getRoundingMode() rounding mode} is applied.
	 *
	 * @param uDecimal1
	 *            the first unscaled decimal value to average
	 * @param uDecimal2
	 *            the second unscaled decimal value to average
	 * @return {@code (uDecimal1+uDecimal2)/2} using the rounding mode of this
	 *         arithetics
	 * @throws ArithmeticException
	 *             if {@link #getRoundingMode() rounding mode} is UNNECESSARY
	 *             and rounding is necessary or if an overflow occurs and the
	 *             {@link #getOverflowMode() overflow mode} is set to throw an
	 *             exception
	 */
	long avg(long uDecimal1, long uDecimal2);

	/**
	 * Returns the non-negative value {@code abs(uDecimal)}, which is the value
	 * itself if {@code uDecimal>=0} and {@code -uDecimal} if the given value is
	 * negative.
	 * 
	 * @param uDecimal
	 *            the unscaled decimal value
	 * @return {@code uDecimal} if {@code uDecimal>=0} and {@code -uDecimal}
	 *         otherwise
	 * @throws ArithmeticException
	 *             if an overflow occurs and the {@link #getOverflowMode()
	 *             overflow mode} is set to throw an exception
	 */
	long abs(long uDecimal);

	/**
	 * Returns the negated value {@code -uDecimal} again as unscaled decimal
	 * value.
	 * 
	 * @param uDecimal
	 *            the unscaled decimal value to negate
	 * @return {@code -uDecimal} as unscaled decimal value
	 * @throws ArithmeticException
	 *             if an overflow occurs and the {@link #getOverflowMode()
	 *             overflow mode} is set to throw an exception
	 */
	long negate(long uDecimal);

	/**
	 * Returns the inverted value {@code 1/uDecimal} again as unscaled decimal
	 * value.
	 * 
	 * @param uDecimal
	 *            the unscaled decimal value to invert
	 * @return {@code 1/uDecimal} as unscaled decimal value
	 * @throws ArithmeticException
	 *             if {@code uDecimal} is zero, if {@link #getRoundingMode()
	 *             rounding mode} is UNNECESSARY and rounding is necessary or if
	 *             an overflow occurs and the {@link #getOverflowMode() overflow
	 *             mode} is set to throw an exception
	 */
	long invert(long uDecimal);

	/**
	 * Returns the square of {@code uDecimal} again as unscaled decimal value.
	 * 
	 * @param uDecimal
	 *            the unscaled decimal value
	 * @return {@code uDecimal^s}
	 * @throws ArithmeticException
	 *             if {@link #getRoundingMode() rounding mode} is UNNECESSARY
	 *             and rounding is necessary or if an overflow occurs and the
	 *             {@link #getOverflowMode() overflow mode} is set to throw an
	 *             exception
	 */
	long square(long uDecimal);

	/**
	 * Returns the square root of {@code uDecimal} again as unscaled decimal
	 * value.
	 * 
	 * @param uDecimal
	 *            the unscaled decimal value
	 * @return {@code sqrt(uDecimal)}
	 * @throws ArithmeticException
	 *             if {@code uDecimal} is negative or if
	 *             {@link #getRoundingMode() rounding mode} is UNNECESSARY and
	 *             rounding is necessary
	 */
	long sqrt(long uDecimal);

	/**
	 * Returns an unscaled decimal whose value is
	 * <tt>(uDecimalBase<sup>exponent</sup>)</tt>. Note that {@code exponent} is
	 * an integer rather than a decimal.
	 * 
	 * @param uDecimalBase
	 *            the unscaled decimal base value
	 * @param exponent
	 *            exponent to which {@code uDecimalBase} is to be raised.
	 * @return <tt>uDecimalBase<sup>exponent</sup></tt>
	 * @throws ArithmeticException
	 *             if {@code uDecimalBase==0} and {@code exponent} is negative.
	 *             (This would cause a division by zero.), if
	 *             {@link #getRoundingMode() rounding mode} is UNNECESSARY and
	 *             rounding is necessary or if an overflow occurs and the
	 *             {@link #getOverflowMode() overflow mode} is set to throw an
	 *             exception
	 */
	long pow(long uDecimalBase, int exponent);

	/**
	 * Returns an unscaled decimal whose value is {@code (uDecimal << n)}. The
	 * shift distance, {@code n}, may be negative, in which case this method
	 * performs a right shift. The result is <tt>(uDecimal * 2<sup>n</sup>)</tt>
	 * .
	 * 
	 * @param uDecimal
	 *            the unscaled decimal value to shift
	 * @param n
	 *            shift distance, in bits.
	 * @return {@code uDecimal << n}
	 * @throws ArithmeticException
	 *             if an overflow occurs and the {@link #getOverflowMode()
	 *             overflow mode} is set to throw an exception
	 * @see #shiftRight
	 */
	long shiftLeft(long uDecimal, int n);

	/**
	 * Returns an unscaled decimal whose value is {@code (uDecimal >> n)}. Sign
	 * extension is performed. The shift distance, {@code n}, may be negative,
	 * in which case this method performs a left shift. The result is
	 * <tt>floor(this / 2<sup>n</sup>)</tt>.
	 * 
	 * @param uDecimal
	 *            the unscaled decimal value to shift
	 * @param n
	 *            shift distance, in bits.
	 * @return {@code this >> n}
	 * @throws ArithmeticException
	 *             if {@link #getRoundingMode() rounding mode} is UNNECESSARY
	 *             and rounding is necessary or if an overflow occurs and the
	 *             {@link #getOverflowMode() overflow mode} is set to throw an
	 *             exception
	 * @see #shiftLeft
	 */
	long shiftRight(long uDecimal, int n);

	/**
	 * Returns an unscaled decimal whose value is rounded to the specified
	 * {@code precision} using the rounding mode of this arithmetic.
	 * <p>
	 * Note that this method does not change the scale of the value --- extra
	 * digits are simply zeroised.
	 * <p>
	 * <i>Examples and special cases:</i>
	 * <dl>
	 * <dt>precision = 0</dt>
	 * <dd>value is rounded to an integer value</dd>
	 * <dt>precision = 2</dt>
	 * <dd>value is rounded to the second digit after the decimal point</dd>
	 * <dt>precision = -3</dt>
	 * <dd>value is rounded to the thousands</dd>
	 * <dt>precision >= scale</dt>
	 * <dd>values is left unchanged</dd>
	 * <dt>precision < scale - 18</dt>
	 * <dd>{@code IllegalArgumentException} is thrown</dd>
	 * </dl>
	 * 
	 * @param uDecimal
	 *            the unscaled decimal value to round
	 * @param precision
	 *            the precision to use for the rounding, for instance 2 to round
	 *            to the second digit after the decimal point; must be at least
	 *            {@code (scale - 18)}
	 * @return an unsigned decimal rounded to the given precision
	 * @throws IllegalArgumentException
	 *             if {@code precision < scale - 18}
	 * @throws ArithmeticException
	 *             if {@link #getRoundingMode() rounding mode} is UNNECESSARY
	 *             and rounding is necessary or if an overflow occurs and the
	 *             {@link #getOverflowMode() overflow mode} is set to throw an
	 *             exception
	 */
	long round(long uDecimal, int precision);

	/**
	 * Converts the specified long value to an unscaled decimal.
	 * 
	 * @param value
	 *            the value to convert
	 * @return the unscaled decimal representing the same value as the given
	 *         long value
	 */
	long fromLong(long value);

	/**
	 * Converts the specified float value to an unscaled decimal.
	 * 
	 * @param value
	 *            the value to convert
	 * @return the unscaled decimal representing the same value as the given
	 *         float value
	 * @throws ArithmeticException
	 *             if value is {@link Float#NaN} or {@link Float#isInfinite()
	 *             infinite}, if {@link #getRoundingMode() rounding mode} is
	 *             UNNECESSARY and rounding is necessary or if an overflow
	 *             occurs and the {@link #getOverflowMode() overflow mode} is
	 *             set to throw an exception
	 */
	long fromFloat(float value);

	/**
	 * Converts the specified double value to an unscaled decimal.
	 * 
	 * @param value
	 *            the value to convert
	 * @return the unscaled decimal representing the same value as the given
	 *         double value
	 * @throws ArithmeticException
	 *             if value is {@link Double#NaN} or {@link Double#isInfinite()
	 *             infinite}, if {@link #getRoundingMode() rounding mode} is
	 *             UNNECESSARY and rounding is necessary or if an overflow
	 *             occurs and the {@link #getOverflowMode() overflow mode} is
	 *             set to throw an exception
	 */
	long fromDouble(double value);

	/**
	 * Converts the specified {@link BigInteger} value to an unscaled decimal.
	 * 
	 * @param value
	 *            the value to convert
	 * @return the unscaled decimal representing the same value as the given big
	 *         integer value, or if an overflow occurs and the
	 *         {@link #getOverflowMode() overflow mode} is set to throw an
	 *         exception
	 */
	long fromBigInteger(BigInteger value);

	/**
	 * Converts the specified {@link BigDecimal} value to an unscaled decimal.
	 * <p>
	 * Note: this operation is <b>not</b> garbage free, meaning that new
	 * temporary objects may be allocated during the conversion.
	 * 
	 * @param value
	 *            the value to convert
	 * @return the unscaled decimal representing the same value as the given big
	 *         decimal value, if {@link #getRoundingMode() rounding mode} is
	 *         UNNECESSARY and rounding is necessary or if an overflow occurs
	 *         and the {@link #getOverflowMode() overflow mode} is set to throw
	 *         an exception
	 */
	long fromBigDecimal(BigDecimal value);

	/**
	 * Converts the specified unscaled decimal with the given scale to another
	 * unscaled decimal using the scale of this arithmetic object.
	 * 
	 * @param unscaledValue
	 *            the unscaled decimal value to convert
	 * @param scale
	 *            the scale used for {@code unscaledValue}
	 * @return the unscaled decimal representing the same value as the given
	 *         unscaled decimal with the new scale defined by this arithmetic
	 *         object, if {@link #getRoundingMode() rounding mode} is
	 *         UNNECESSARY and rounding is necessary or if an overflow occurs
	 *         and the {@link #getOverflowMode() overflow mode} is set to throw
	 *         an exception
	 */
	long fromUnscaled(long unscaledValue, int scale);

	/**
	 * Parses the string argument as a signed decimal returned as unscaled long
	 * value. The characters in the string must all be decimal digits, except an
	 * optional decimal point {@code '.'} and the first character that may be an
	 * ASCII minus sign {@code '-'} (<code>&#92;u002D'</code>) to indicate a
	 * negative value or an ASCII plus sign {@code '+'} (
	 * <code>'&#92;u002B'</code>) to indicate a positive value.
	 * 
	 * @param value
	 *            a {@code String} containing the decimal value representation
	 *            to be parsed
	 * @return the decimal as unscaled {@code long} value
	 * @throws NumberFormatException
	 *             if the string does not contain a parsable decimal.
	 */
	long parse(String value);

	/**
	 * Converts the specified unscaled decimal value {@code uDecimal} into a
	 * long value and returns it. The arithmetic's {@link #getRoundingMode()
	 * rounding mode} is applied if rounding is necessary.
	 * 
	 * @param uDecimal
	 *            the unscaled decimal value convert into a long value
	 * @return the {@code uDecimal} value converted into a long value, possibly
	 *         rounded or truncated
	 */
	long toLong(long uDecimal);

	float toFloat(long uDecimal);

	double toDouble(long uDecimal);

	BigDecimal toBigDecimal(long uDecimal);

	BigDecimal toBigDecimal(long uDecimal, int scale);

	String toString(long uDecimal);

}
