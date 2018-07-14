package org.bakasoft.framboyan.diff;

import java.util.List;

public class CompareListDiff extends CompareGenericDiff<List<?>> {

	public CompareListDiff(List<?> left, List<?> right, String sign) {
		super(left, right, sign);
	}

	@Override
	protected int compare() {
		Integer leftSize = left.size();
		Integer rightSize = right.size();
		
		return leftSize.compareTo(rightSize);
	}


}
