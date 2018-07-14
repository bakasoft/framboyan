package org.bakasoft.framboyan.diff;

public class CompareStringDiff extends CompareGenericDiff<String> {

	public CompareStringDiff(String left, String right, String sign) {
		super(left, right, sign);
	}

	@Override
	protected int compare() {
		return left.compareTo(right);
	}

}
