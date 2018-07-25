package org.bakasoft.framboyan.diff;

import java.util.Arrays;
import java.util.List;

import org.bakasoft.framboyan.exceptions.NotSupportedClassException;
import org.bakasoft.framboyan.util.Normalizer;

public class StartWithDiff extends WrappedDiff {

	public StartWithDiff(Object value, Object prefix) {
		super(() -> {
			Object valueN = Normalizer.normalize(value);
			Object prefixN = Normalizer.normalize(prefix);
			
			if (valueN instanceof String) {
				if (prefixN instanceof String) {
					return new StartWithStringDiff((String)valueN, (String)prefixN);
				}
				else {
					throw new NotSupportedClassException(prefix, String.class);
				}
			}
			else if (valueN instanceof List) {
				if (prefixN instanceof List) {
					return new StartWithListDiff((List<?>)valueN, (List<?>)prefixN);	
				}
				else {
					return new StartWithListDiff((List<?>)valueN, Arrays.asList(prefixN));
				}
			}
			else {
				throw new NotSupportedClassException(value, String.class, List.class);
			}
		});
	}
}
