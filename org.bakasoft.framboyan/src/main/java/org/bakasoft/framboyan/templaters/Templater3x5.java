/* WARNING! This file is autogenerated by a script. */
package org.bakasoft.framboyan.templaters;

import java.util.ArrayList;

import org.bakasoft.framboyan.exceptions.MissingTestException;
import org.bakasoft.framboyan.templates.Template5;

public class Templater3x5<T1, T2, T3, U1, U2, U3, U4, U5> {

	private final Templater3x5Builder<T1, T2, T3, U1, U2, U3, U4, U5> builder;

	public Templater3x5(Templater3x5Builder<T1, T2, T3, U1, U2, U3, U4, U5> builder) {
		this.builder = builder;
	}

	public Batch using(T1 arg1, T2 arg2, T3 arg3) {
		return new Batch().using(arg1, arg2, arg3);
	}

	public class Batch {

		private final ArrayList<Template5<U1, U2, U3, U4, U5>> templates;

		public Batch() {
			this.templates = new ArrayList<>();
		}

		public Batch using(T1 arg1, T2 arg2, T3 arg3) {
			templates.add(builder.build(arg1, arg2, arg3));
			return this;
		}

		public Batch test(U1 arg1, U2 arg2, U3 arg3, U4 arg4, U5 arg5) {
			if (templates.isEmpty()) {
				throw new MissingTestException();
			}

			for (Template5<U1, U2, U3, U4, U5> template : templates) {
				template.test(arg1, arg2, arg3, arg4, arg5);
			}

			return this;
		}
	}
}