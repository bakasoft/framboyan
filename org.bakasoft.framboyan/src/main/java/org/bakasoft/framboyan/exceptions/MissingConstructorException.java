package org.bakasoft.framboyan.exceptions;

import org.bakasoft.framboyan.util.Strings;

public class MissingConstructorException extends RuntimeException {

    public MissingConstructorException(Class<?> clazz, Object... parameters) {
        super(generateMessage(clazz, parameters));
    }

    private static String generateMessage(Class<?> clazz, Object[] parameters) {
        String className = clazz.getName();

        if (parameters == null || parameters.length == 0) {
            return String.format("Missing empty constructor in class: %s", className);
        }

        String paramList = Strings.join(", ", parameters,
                paramType -> paramType != null ? paramType.getClass().getSimpleName() : "Object");

        return String.format("Missing constructor compatible with (%s) in class: %s",
                className,
                paramList);
    }

}
