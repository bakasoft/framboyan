package org.bakasoft.framboyan.diff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bakasoft.framboyan.util.JSON;
import org.bakasoft.framboyan.util.Inspector;

public class EndWithListDiff extends AbstractDiff {

	private final List<?> list;
	private final List<?> suffix;
	
	public EndWithListDiff(List<?> list, List<?> suffix) {
		this.list = list;
		this.suffix = suffix;
	}

	@Override
	public boolean test() {
		if (list.size() >= suffix.size()) {
			for (int i = suffix.size() - 1, j = list.size() - 1; i >= 0 && j >= 0; i--, j--) {
				Object valueItem = list.get(j);
				Object suffixItem = suffix.get(i);
				
				if (!Objects.equals(valueItem, suffixItem)) {
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public String generateExpectMessage() {
		return String.format("Expected %s to end with %s.", 
				Inspector.inspect(list),
				Inspector.inspect(suffix));
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s NOT to end with %s.", 
				Inspector.inspect(list),
				Inspector.inspect(suffix));
	}

	@Override
	public String generateActualValue() {
		ArrayList<Object> actualSuffix = new ArrayList<>();
		
		for (int i = list.size() - suffix.size(); i < list.size(); i++) {
			actualSuffix.add(list.get(i));
		}
		
		return JSON.stringify(actualSuffix);
	}

	@Override
	public String generateExpectedValue() {
		return JSON.stringify(suffix);
	}

}
