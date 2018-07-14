package org.bakasoft.framboyan.diff;

import java.math.BigDecimal;
import java.util.List;

import org.bakasoft.framboyan.util.Normalizer;

public class CompareDiff extends WrappedDiff {

	public CompareDiff(Object left, Object right, String sign) {
		super(() -> {
			Object leftN = Normalizer.normalize(left);
			Object rightN = Normalizer.normalize(right);
			
			if (leftN instanceof String && rightN instanceof String) {
				return new CompareStringDiff((String)leftN, (String)rightN, sign);
			}
			else if (leftN instanceof BigDecimal && rightN instanceof BigDecimal) {
				return new CompareNumberDiff((BigDecimal)leftN, (BigDecimal)rightN, sign);
			}
			else if (leftN instanceof List && rightN instanceof List) {
				return new CompareListDiff((List<?>)leftN, (List<?>)rightN, sign);
			}
			else {
				throw new RuntimeException();
			}
		});
	}

}
