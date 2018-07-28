/* WARNING! This file is autogenerated by a script. */
package org.bakasoft.framboyan.templaters;

import java.util.ArrayList;

import org.bakasoft.framboyan.templates.Template2;

public class Templater3x2<T1, T2, T3, U1, U2> {

	private final Templater3x2Builder<T1, T2, T3, U1, U2> builder;

	public Templater3x2(Templater3x2Builder<T1, T2, T3, U1, U2> builder) {
		this.builder = builder;
	}

	public Batch using(T1 arg1, T2 arg2, T3 arg3) {
		return new Batch().using(arg1, arg2, arg3);
	}

	public class Batch {

		private final ArrayList<Template2<U1, U2>> templates;

		public Batch() {
			this.templates = new ArrayList<>();
		}

		public Batch using(T1 arg1, T2 arg2, T3 arg3) {
			templates.add(builder.build(arg1, arg2, arg3));
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
