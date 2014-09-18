package ch.javasoft.decimal.scale;

/**
 * Scale class for decimals with 16 {@link #getScale() fraction digit} and
 * {@link #getScaleFactor() scale factor} 10,000,000,000,000,000.
 */
public final class Scale16f extends AbstractScale {
	public static final Scale16f INSTANCE = new Scale16f();

	@Override
	public int getScale() {
		return 16;
	}

	@Override
	public long getScaleFactor() {
		return 10000000000000000L;
	}

	@Override
	public long multiplyByScaleFactor(long factor) {
		return factor * 10000000000000000L;
	}

	@Override
	public long mulloByScaleFactor(int factor) {
		return (factor & LONG_MASK) * 1874919424;//(scaleFactor & LONG_MASK)
	}

	@Override
	public long mulhiByScaleFactor(int factor) {
		return (factor & LONG_MASK) * 2328306;//(scaleFactor >>> 32)
	}

	@Override
	public long divideByScaleFactor(long dividend) {
		return dividend / 10000000000000000L;
	}

	@Override
	public long moduloByScaleFactor(long dividend) {
		return dividend % 10000000000000000L;
	}
}