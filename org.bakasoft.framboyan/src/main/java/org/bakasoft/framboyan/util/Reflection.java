package org.bakasoft.framboyan.util;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Reflection {

	public static <T> List<Class<? extends T>> findSubclasses(Class<T> superClass) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		
		return findSubclasses(superClass, classLoader);
			
	}
	
	public static <T> List<T> createInstances(Class<T> superClass) {
		return createInstances(findSubclasses(superClass));
	}
	
	public static <T> List<T> createInstances(Class<T> superClass, ClassLoader classLoader) {
		return createInstances(findSubclasses(superClass, classLoader));
	}

	public static <T> List<Class<? extends T>> findSubclasses(Class<T> superClass, ClassLoader classLoader) {
		ArrayList<Class<? extends T>> list = new ArrayList<>();
		
		if (classLoader != null) {
			URL url = classLoader.getResource(".");
			
			if (url != null) {
				Path root;
				
				try {
					root = Paths.get(url.toURI());
				} catch (URISyntaxException e) {
					throw new RuntimeException(e);
				}
				
				String rootStr = (root.toString() + "/").toLowerCase();
				
				try {
					Files.walk(root)
						.filter(Files::isRegularFile)
						.map(Path::toString)
						.filter(path -> path.toLowerCase().startsWith(rootStr))
						.map(path -> path.substring(rootStr.length()))
						.filter(path -> path.toLowerCase().endsWith(".class"))
						.map(path -> path.substring(0, path.length() - 6))
						.map(path -> path.replace('/', '.'))
						.map(path -> {
							try {
								return classLoader.loadClass(path);
							} catch (ClassNotFoundException e) {
								throw new RuntimeException(e);
							}
						})
						.filter(type -> superClass.isAssignableFrom(type))
						.forEach(type -> {
							list.add(type.asSubclass(superClass));
						});
				} catch (IOException e) {
					throw new RuntimeException(e);
				}	
			}	
		}
		
		return list;
	}
	
	public static <T> List<T> createInstances(List<Class<? extends T>> types) {
		ArrayList<T> list = new ArrayList<>();
		
		types.forEach(type -> {
			T obj = createDefaultInstance(type);
			
			list.add(obj);
		});
			
		return list;
	}

	public static <T> T createDefaultInstance(Class<? extends T> type) {
		try {
			Constructor<? extends T> ctr = type.getConstructor();
			
			return ctr.newInstance();
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T createDefaultInstance(Class<?> type, Class<? extends T> superType) {
		if(!superType.isAssignableFrom(type)) {
			throw new RuntimeException("Not supported type: " + type);
		}
		
		Class<? extends T> suiteClass = type.asSubclass(superType);
		
		return createDefaultInstance(suiteClass);
	}
	
}
