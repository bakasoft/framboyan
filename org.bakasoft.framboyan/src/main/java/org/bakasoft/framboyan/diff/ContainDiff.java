package org.bakasoft.framboyan.diff;

import java.util.Arrays;
import java.util.List;

import org.bakasoft.framboyan.exceptions.NotSupportedClassException;
import org.bakasoft.framboyan.util.Normalizer;

public class ContainDiff extends WrappedDiff {

	public ContainDiff(Object set, Object subset) {
		super(() -> {
			Object setN = Normalizer.normalize(set);
			Object subsetN = Normalizer.normalize(subset);
			
			if (setN instanceof String) {
				if (subsetN instanceof String) {
					return new ContainStringDiff((String)setN, (String)subsetN);
				}
				else {
					throw new NotSupportedClassException(subset, String.class);
				}
			}
			else if (setN instanceof List) {
				if (subsetN instanceof List) {
					return new ContainListDiff((List<?>)setN, (List<?>)subsetN);	
				}
				else {
					return new ContainListDiff((List<?>)setN, Arrays.asList(subsetN));
				}
			}
			else {
				throw new NotSupportedClassException(subset, String.class, List.class);
			}
		});
	}

}
