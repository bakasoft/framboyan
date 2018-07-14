package org.bakasoft.framboyan.diff;

import java.math.BigDecimal;

public class CompareNumberDiff extends CompareGenericDiff<BigDecimal> {

	public CompareNumberDiff(BigDecimal left, BigDecimal right, String sign) {
		super(left, right, sign);
	}
	
	@Override
	protected int compare() {
		return left.compareTo(right);
	}

}
