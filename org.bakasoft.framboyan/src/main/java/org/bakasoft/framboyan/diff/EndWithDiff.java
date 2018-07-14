package org.bakasoft.framboyan.diff;

import java.util.Arrays;
import java.util.List;

import org.bakasoft.framboyan.util.Normalizer;

public class EndWithDiff extends WrappedDiff {

	public EndWithDiff(Object value, Object suffix) {
		super(() -> {
			Object valueN = Normalizer.normalize(value);
			Object suffixN = Normalizer.normalize(suffix);
			
			if (valueN instanceof String && suffixN instanceof String) {
				return new EndWithStringDiff((String)valueN, (String)suffixN);
			}
			else if (valueN instanceof List) {
				if (suffixN instanceof List) {
					return new EndWithListDiff((List<?>)valueN, (List<?>)suffixN);	
				}
				else {
					return new EndWithListDiff((List<?>)valueN, Arrays.asList(suffixN));
				}
			}
			else {
				throw new RuntimeException();
			}
		});
	}
}
