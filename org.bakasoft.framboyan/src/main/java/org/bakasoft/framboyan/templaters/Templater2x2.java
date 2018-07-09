package org.bakasoft.framboyan.templaters;

import java.util.ArrayList;

import org.bakasoft.framboyan.templates.Template2;

public class Templater2x2<T1, T2, U1, U2> {
	
	private final Templater2x2Builder<T1, T2, U1, U2> builder;

	public Templater2x2(Templater2x2Builder<T1, T2, U1, U2> builder) {
		this.builder = builder;
	}
	
	public Batch using(T1 arg1, T2 arg2) {
		return new Batch().using(arg1, arg2);
	}
	
	public class Batch {

		private final ArrayList<Template2<U1, U2>> templates;
		
		public Batch() {
			this.templates = new ArrayList<>();
		}
		
		public Batch using(T1 arg1, T2 arg2) {
			templates.add(builder.build(arg1, arg2));
			return this;
		}

		public Batch test(U1 arg1, U2 arg2) {
			if (templates.isEmpty()) {
				throw new RuntimeException();
			}
			
			for (Template2<U1, U2> template : templates) {
				template.test(arg1, arg2);
			}
			
			return this;
		}
	}
}
