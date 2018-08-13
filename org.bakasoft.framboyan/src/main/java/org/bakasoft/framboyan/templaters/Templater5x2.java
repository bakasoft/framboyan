/* WARNING! This file is autogenerated by a script. */
package org.bakasoft.framboyan.templaters;

import java.util.ArrayList;

import org.bakasoft.framboyan.exceptions.MissingTestException;
import org.bakasoft.framboyan.templates.Template2;

public class Templater5x2<T1, T2, T3, T4, T5, U1, U2> {

	private final Templater5x2Builder<T1, T2, T3, T4, T5, U1, U2> builder;

	public Templater5x2(Templater5x2Builder<T1, T2, T3, T4, T5, U1, U2> builder) {
		this.builder = builder;
	}

	public Batch using(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5) {
		return new Batch().using(arg1, arg2, arg3, arg4, arg5);
	}

	public class Batch {

		private final ArrayList<Template2<U1, U2>> templates;

		public Batch() {
			this.templates = new ArrayList<>();
		}

		public Batch using(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5) {
			templates.add(builder.build(arg1, arg2, arg3, arg4, arg5));
			return this;
		}

		public Batch test(U1 arg1, U2 arg2) {
			if (templates.isEmpty()) {
				throw new MissingTestException();
			}

			for (Template2<U1, U2> template : templates) {
				template.test(arg1, arg2);
			}

			return this;
		}
	}
}
