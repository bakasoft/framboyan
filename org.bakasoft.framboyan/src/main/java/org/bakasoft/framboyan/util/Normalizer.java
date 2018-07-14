package org.bakasoft.framboyan.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bakasoft.framboyan.Action;

public class Normalizer {
	
	public static Object normalize(Object value) {
		// Null
		
		if (value == null) {
			return null;
		}
		
		Object result;
		
		// Boolean
		
		result = toBoolean(value, null);
		
		if (result != null) {
			return result;
		}
		
		// Number
		
		result = toNumber(value, null);
		
		if (result != null) {
			return result;
		}
		
		// String
		
		result = toString(value, null);
		
		if (result != null) {
			return result;
		}
		
		// List
		
		result = toList(value, null);
		
		if (result != null) {
			return result;
		}
		
		// TODO: Map and other values
		
		return value;
	}
	
	public static Boolean toBoolean(Object value, Boolean defaultValue) {
		if (value instanceof Boolean) {
			return (Boolean)value;
		}
		else if ("true".equals(value)) {
			return true;
		}
		else if ("false".equals(value)) {
			return false;
		}
		
		return defaultValue;
	}
	
	public static BigDecimal toNumber(Object value, BigDecimal defaultValue) {
		if (value instanceof BigDecimal) {
			return (BigDecimal)value;
		}
		else if (value instanceof BigInteger) {
			return new BigDecimal((BigInteger)value);
		}
		else if (value instanceof Byte) {
			return new BigDecimal((Byte)value);
		}
		else if (value instanceof Double) {
			return new BigDecimal((Double)value);
		}
		else if (value instanceof Float) {
			return new BigDecimal((Float)value);
		}
		else if (value instanceof Integer) {
			return new BigDecimal((Integer)value);
		}
		else if (value instanceof Long) {
			return new BigDecimal((Long)value);
		}
		else if (value instanceof Short) {
			return new BigDecimal((Short)value);
		}
		else if (value instanceof AtomicInteger) {
			return new BigDecimal(((AtomicInteger)value).get());
		}
		else if (value instanceof AtomicLong) {
			return new BigDecimal(((AtomicLong)value).get());
		}
		else if (value instanceof DoubleAccumulator) {
			return new BigDecimal(((DoubleAccumulator)value).get());
		}
		else if (value instanceof DoubleAdder) {
			return new BigDecimal(((DoubleAdder)value).sum());
		}
		else if (value instanceof LongAccumulator) {
			return new BigDecimal(((LongAccumulator)value).get());
		}
		else if (value instanceof LongAdder) {
			return new BigDecimal(((LongAdder)value).sum());
		}
		else if (value instanceof String) {
			try {
				return new BigDecimal((String)value);
			}
			catch (NumberFormatException e) {
				// oops! not a number
			}
		}
		
		return defaultValue;
	}
	
	public static String toString(Object value, String defaultValue) {
		if (value instanceof String) {
			return (String)value;
		}
		else if (value instanceof Character) {
			return value.toString();
		}
		else if (value instanceof char[]) {
			return new String((char[])value);
		}
		else if (value instanceof CharSequence) {
			// StringBuilder, StringBuffer, etc...
			return ((CharSequence)value).toString();
		}	
		
		return defaultValue;
	}
	
	public static List<?> toList(Object value, List<?> defaultValue) {
		if (value instanceof List) {
			List<?> list = (List<?>)value;
			Object[] items = list.toArray();
			
			normalizeArray(items);
			
			return new NormalizedList(items);
		}
		else if (value instanceof Collection) {
			Object[] items = ((Collection<?>)value).toArray();
			
			normalizeArray(items);
			
			return new NormalizedList(items);
		}
		else if (value instanceof Iterable) {
			Iterable<?> iterable = (Iterable<?>)value;
			Object[] items = StreamSupport.stream(iterable.spliterator(), false).toArray();

			normalizeArray(items);

			return new NormalizedList(items);
		}
		else if (value instanceof Stream<?>) {
			Stream<?> stream = (Stream<?>)value;
			Object[] items = stream.toArray();
			
			normalizeArray(items);
			
			return new NormalizedList(items);
		}
		else if (value != null && value.getClass().isArray()) {
			int length = Array.getLength(value);
			Object[] items = new Object[length];
			
			for (int i = 0; i < length; i++) {
				items[i] = Array.get(value, i);
			}
			
			normalizeArray(items);
			
			return new NormalizedList(items);
		}
		
		return defaultValue;
	}

	public static Action toAction(Object value) {
		if (value instanceof Runnable) {
			return (Action)(((Runnable)value)::run);
		}
		else if (value instanceof Supplier) {
			return (Action)(() -> ((Supplier<?>)value).get());
		}
		else if (value instanceof Consumer) {
			return (Action)(() -> ((Consumer<?>)value).accept(null));
		}
		else if (value instanceof Function) {
			return (Action)(() -> ((Function<?, ?>)value).apply(null));
		}
		else if (value instanceof Predicate) {
			return (Action)(() -> ((Predicate<?>)value).test(null));
		}
		else if (value instanceof Action) {
			return (Action) value;
		}
		else {
			throw new RuntimeException();
		}	
	}

	public static Pattern toPattern(Object value) {
		if (value instanceof Pattern) {
			return ((Pattern)value);
		}
		else if (value instanceof String) {
			return (Pattern.compile((String)value));
		}
		
		throw new RuntimeException("invalid pattern");
	}

	public static void normalizeArray(Object[] items) {
		for (int i = 0; i < items.length; i++) {
			items[i] = normalize(items[i]);
		}
	}
	
	public static BigDecimal toNumber(Object value) {
		BigDecimal number = toNumber(value, null);
	
		if (number == null) {
			throw new RuntimeException(); // TODO: message: cannot convert to number
		}
		
		return number;
	}
	
	public static String toString(Object value) {
		String string = toString(value, null);
		
		if (string == null) {
			throw new RuntimeException(); // TODO: message: cannot convert to string
		}
		
		return string;
	}
	
	// TODO: complete method overloads
	
	private static class NormalizedList extends ArrayList<Object> {

		private static final long serialVersionUID = 2843166128879835990L;
		
		public NormalizedList(Object[] items) {
			super(items.length);
			
			for (Object item : items) {
				add(item);
			}
		}
		
		@Override
		public int hashCode() {
			int hash = List.class.hashCode();
			
			for (Object item : this) {
				hash ^= item.hashCode();
			}
			
			return hash;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof List) {
				List<?> list = (List<?>)o;
				
				if (list.size() == size()) {
					for (int i = 0; i < size(); i++) {
						if (!Objects.equals(get(i), list.get(i))) {
							return false;
						}
					}
					
					return true;
				}
			}
			
			return false;
		}
	}
	
}
