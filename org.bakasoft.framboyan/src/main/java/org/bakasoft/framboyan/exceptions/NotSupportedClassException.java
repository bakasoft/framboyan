package org.bakasoft.framboyan.exceptions;

import org.bakasoft.framboyan.util.Strings;

public class NotSupportedClassException extends RuntimeException {

    public NotSupportedClassException(Object obj, Class<?>... expectedType) {
        this(obj != null ? obj.getClass() : null, expectedType);
    }

    public NotSupportedClassException(Class<?> notSupportedType, Class<?>... expectedType) {
        super(generateMessage(notSupportedType, expectedType));
    }

    public static String generateMessage(Class<?> notSupportedType, Class<?>[] expectedTypes) {
        if (expectedTypes != null && expectedTypes.length > 0) {
            String classList = Strings.join(", ", " or ", expectedTypes, clazz -> clazz.getName());

            if (notSupportedType != null) {
                return String.format("Class %s is not supported, it must be assignable to %s.",
                        notSupportedType.getName(),
                        expectedTypes);
            }

            return String.format("Expected a class assignable to %s.",
                    expectedTypes);
        }
        else if (notSupportedType != null) {
            return String.format("Class %s is not supported.",
                    notSupportedType.getName());
        }
        else {
            return String.format("Class not supported.");
        }
    }

}
