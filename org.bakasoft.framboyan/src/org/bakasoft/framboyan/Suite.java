package org.bakasoft.framboyan;

import java.util.ArrayList;

public class Suite {

	private final ArrayList<Spec> specs;
	private final String name;
	
	public Suite(String name) {
		this.name = name;
		this.specs = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void addSpec(Spec spec) {
		this.specs.add(spec);
	}
	
	public Spec[] getSpecs() {
		return specs.toArray(new Spec[specs.size()]);
	}

}
