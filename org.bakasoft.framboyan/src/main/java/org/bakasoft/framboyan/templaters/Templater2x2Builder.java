package org.bakasoft.framboyan.templaters;

import org.bakasoft.framboyan.templates.Template2;

@FunctionalInterface
public interface Templater2x2Builder<T1, T2, U1, U2> {
	
	Template2<U1, U2> build(T1 arg1, T2 arg2);
	
}
