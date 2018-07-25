package org.bakasoft.framboyan.diff;

import java.math.BigDecimal;
import java.util.List;

import org.bakasoft.framboyan.exceptions.NotSupportedClassException;
import org.bakasoft.framboyan.util.Normalizer;

public class CompareDiff extends WrappedDiff {

	public CompareDiff(Object left, Object right, String sign) {
		super(() -> {
			Object leftN = Normalizer.normalize(left);
			Object rightN = Normalizer.normalize(right);
			
			if (leftN instanceof String) {
				if (rightN instanceof String) {
					return new CompareStringDiff((String)leftN, (String)rightN, sign);
				}
				else {
					throw new NotSupportedClassException(right, String.class);
				}
			}
			else if (leftN instanceof BigDecimal) {
				if (rightN instanceof BigDecimal) {
					return new CompareNumberDiff((BigDecimal)leftN, (BigDecimal)rightN, sign);
				}
				else {
					throw new NotSupportedClassException(right, BigDecimal.class);
				}
			}
			else if (leftN instanceof List) {
				if (rightN instanceof List) {
					return new CompareListDiff((List<?>)leftN, (List<?>)rightN, sign);
				}
				else {
					throw new NotSupportedClassException(right, List.class);
				}
			}
			else {
				throw new NotSupportedClassException(right, String.class, BigDecimal.class, List.class);
			}
		});
	}

}
