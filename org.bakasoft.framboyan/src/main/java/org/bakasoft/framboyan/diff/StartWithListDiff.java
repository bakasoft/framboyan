package org.bakasoft.framboyan.diff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bakasoft.framboyan.util.Inspector;
import org.bakasoft.framboyan.util.JSON;

public class StartWithListDiff extends AbstractDiff {

	private final List<?> list;
	private final List<?> prefix;
	
	public StartWithListDiff(List<?> list, List<?> prefix) {
		this.list = list;
		this.prefix = prefix;
	}

	@Override
	public boolean test() {
		if (list.size() >= prefix.size()) {
			for (int i = 0; i < prefix.size(); i++) {
				Object valueItem = list.get(i);
				Object prefixItem = prefix.get(i);
				
				if (!Objects.equals(valueItem, prefixItem)) {
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public String generateExpectMessage() {
		return String.format("Expected %s to start with %s.", 
				Inspector.inspect(list),
				Inspector.inspect(prefix));
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s NOT to start with %s.", 
				Inspector.inspect(list),
				Inspector.inspect(prefix));
	}

	@Override
	public String generateActualValue() {
		ArrayList<Object> actualPrefix = new ArrayList<>();
		
		for (int i = 0; i < list.size(); i++) {
			actualPrefix.add(list.get(i));
		}
		
		return JSON.stringify(actualPrefix);
	}

	@Override
	public String generateExpectedValue() {
		return JSON.stringify(prefix);
	}

}
