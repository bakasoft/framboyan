package org.bakasoft.framboyan;

import java.util.List;

import org.bakasoft.framboyan.annotation.Describe;
import org.bakasoft.framboyan.annotation.Focused;
import org.bakasoft.framboyan.annotation.Pending;

public interface Target {

	Object getDescription();
	
	boolean isPending();
	
	boolean isFocused();
	
	List<Target> getTargets();
	
	Node buildInto(Node parent);
	
	boolean isRoot();
	
	public static Object getDescriptionFromClass(Class<?> clazz, Object defaultValue) {
		Describe describe = clazz.getAnnotation(Describe.class);

		if (describe != null && describe.value() != null && !describe.value().isEmpty()) {
			return describe.value();
		}

		return defaultValue;
	}

	public static boolean getPendingFromClass(Class<?> clazz, boolean defaultValue) {
		Pending pending = clazz.getAnnotation(Pending.class);

		if (pending != null) {
			return pending.value();
		}

		return defaultValue;
	}

	public static boolean getFocusedFromClass(Class<?> clazz, boolean defaultValue) {
		Focused focused = clazz.getAnnotation(Focused.class);

		if (focused != null) {
			return focused.value();
		}

		return defaultValue;
	}
	
}
