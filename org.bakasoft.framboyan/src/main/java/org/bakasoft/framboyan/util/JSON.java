package org.bakasoft.framboyan.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

public class JSON {

	public static String stringify(Object value) {
		Object normalized = Normalizer.normalize(value);
		
		return Toolbox.bufferedPrinting(out -> {
			print_value(out, normalized, 0, new Stack<Object>());
		});
	}

	private static void print_value(PrintWriter out, Object value, int indent, Stack<Object> stack) {	
		if (value == null) {
			out.print("null");
		}
		else if (value instanceof Boolean) {
			out.print(value);
		}
		else if (value instanceof Number) {
			print_number(out, (Number)value);
		}
		else if (value instanceof String) {
			print_string(out, (String)value);
		}
		else if (value instanceof Class) {
			print_string(out, ((Class<?>)value).getName());
		}
		else if (stack.contains(value)) {
			print_ref(out, value);
		}
		else {
			stack.push(value);
			if (value instanceof List) {
				print_list(out, (List<?>) value, indent, stack);
			}
			else if (value instanceof Map) {
				print_map(out, (Map<?, ?>) value, indent, stack);
			}
			else {
				print_obj(out, value, indent, stack);
			}
			
			stack.pop();
		}
	}
	
	private static void print_number(PrintWriter out, Number value) {
		out.print(value);
	}

	private static void print_ref(PrintWriter out, Object value) {
		out.print("{ \"@id\": ");
		
		print_number(out, get_id(value));
		
		out.print(", \"@type\": ");
		
		print_string(out, get_type(value));
		
		out.print(" }");
	}

	private static Long get_id(Object value) {
		return value.hashCode() & 0xFFFFFFFFL;
	}

	private static String get_type(Object value) {
		return value.getClass().getName();
	}

	private static void print_string(PrintWriter out, String value) {
		out.print("\"");
		out.print(value); // TODO escape
		out.print("\"");
	}

	private static void print_list(PrintWriter out, List<?> list, int indent, Stack<Object> stack) {
		if (list.isEmpty()) {
			out.print("[]");
		}
		else {
			out.print('[');

			indent++;
			
			breakLine(out, indent);
			
			for (int i = 0; i < list.size(); i++) {
				if (i > 0) {
					out.print(',');
					breakLine(out, indent);		
				}
				
				print_value(out, list.get(i), indent, stack);
			}
			
			indent--;
			
			breakLine(out, indent);
			
			out.print(']');
		}
	}

	private static void print_map(PrintWriter out, Map<?, ?> map, int indent, Stack<Object> stack) {
		if (map.isEmpty()) {
			out.print("{}");
		}
		else {
			out.print('{');

			indent++;
			
			breakLine(out, indent);
			
			int i = 0;
			for(Entry<?, ?> entry : map.entrySet()) {
				Object key = entry.getKey();
				Object value = entry.getValue();
				
				if (i > 0) {
					out.print(',');
					breakLine(out, indent);
				}
				
				print_string(out, String.valueOf(key));
				
				out.print(": ");
				
				print_value(out, value, indent, stack);
				
				i++;
			};
			
			indent--;
			
			breakLine(out, indent);
			
			out.print('}');
		}
	}

	private static void print_obj(PrintWriter out, Object obj, int indent, Stack<Object> stack) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("@id", get_id(obj));
		map.put("@type", get_type(obj));
		
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			
			if (properties != null) {
				for (PropertyDescriptor property : properties) {
					Method method = property.getReadMethod();
					
					if (method != null && method.getParameterCount() == 0 && method.getReturnType() != Void.class) {
						String key = property.getName();
						Object value = method.invoke(obj);
						
						value = Normalizer.normalize(value);
						
						map.put(key, value);
					}
				}
			}
			
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// ignore 
		}
		
		print_map(out, map, indent, stack);
	}

	private static void breakLine(PrintWriter out, int indent) {
		out.println();
		
		for (int i = 0; i < indent; i++) {
			out.print("  ");
		}
	}

}
