/* WARNING! This file is autogenerated by a script. */
package org.bakasoft.framboyan.templaters;

import java.util.ArrayList;

import org.bakasoft.framboyan.exceptions.MissingTestException;
import org.bakasoft.framboyan.templates.Template3;

public class Templater4x3<T1, T2, T3, T4, U1, U2, U3> {

	private final Templater4x3Builder<T1, T2, T3, T4, U1, U2, U3> builder;

	public Templater4x3(Templater4x3Builder<T1, T2, T3, T4, U1, U2, U3> builder) {
		this.builder = builder;
	}

	public Batch using(T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
		return new Batch().using(arg1, arg2, arg3, arg4);
	}

	public class Batch {

		private final ArrayList<Template3<U1, U2, U3>> templates;

		public Batch() {
			this.templates = new ArrayList<>();
		}

		public Batch using(T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
			templates.add(builder.build(arg1, arg2, arg3, arg4));
			return this;
		}

		public Batch test(U1 arg1, U2 arg2, U3 arg3) {
			if (templates.isEmpty()) {
				throw new MissingTestException();
			}

			for (Template3<U1, U2, U3> template : templates) {
				template.test(arg1, arg2, arg3);
			}

			return this;
		}
	}
}
