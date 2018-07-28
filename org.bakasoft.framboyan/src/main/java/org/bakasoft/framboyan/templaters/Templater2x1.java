/* WARNING! This file is autogenerated by a script. */
package org.bakasoft.framboyan.templaters;

import java.util.ArrayList;

import org.bakasoft.framboyan.templates.Template1;

public class Templater2x1<T1, T2, U1> {

	private final Templater2x1Builder<T1, T2, U1> builder;

	public Templater2x1(Templater2x1Builder<T1, T2, U1> builder) {
		this.builder = builder;
	}

	public Batch using(T1 arg1, T2 arg2) {
		return new Batch().using(arg1, arg2);
	}

	public class Batch {

		private final ArrayList<Template1<U1>> templates;

		public Batch() {
			this.templates = new ArrayList<>();
		}

		public Batch using(T1 arg1, T2 arg2) {
			templates.add(builder.build(arg1, arg2));
			return this;
		}

		public Batch test(U1 arg1) {
			if (templates.isEmpty()) {
				throw new RuntimeException();
			}

			for (Template1<U1> template : templates) {
				template.test(arg1);
			}

			return this;
		}
	}
}
