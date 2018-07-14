package org.bakasoft.framboyan.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reflection {

	public static List<String> findClassNames() {
		ArrayList<String> classNames = new ArrayList<>();
		String classpath = System.getProperty("java.class.path");
		String[] classpathEntries = classpath.split(File.pathSeparator);
		
		for (String classpathEntry : classpathEntries) {
			File file = new File(classpathEntry);
			
			if (file.isDirectory()) {
				find_class_names(file.toPath(), classNames);
			}
		}
		
		return classNames;
	}

	private static void find_class_names(Path rootDirectory, ArrayList<String> classNames) {
		String rootStr = rootDirectory.toString().toLowerCase();
		
		try {
			Files.walk(rootDirectory)
				.filter(Files::isRegularFile)
				.map(Path::toString)
				.filter(path -> path.toLowerCase().endsWith(".class"))
				.map(path -> path.substring(rootStr.length() + 1, path.length() - 6))
				.map(path -> path.replace(File.separatorChar, '.'))
				.forEach(className -> {
					classNames.add(className);
				});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<Class<? extends T>> findAssignableTo(Class<T> superClass) {
		ArrayList<Class<? extends T>> list = new ArrayList<>();
		
		for (String className : findClassNames()) {
			try {
				Class<?> klass = Class.forName(className);
				
				if (superClass.isAssignableFrom(klass)) {
					list.add(klass.asSubclass(superClass));
				}
			} 
			catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		
		return list;
	}
	
	public static <T> List<T> createInstances(List<Class<? extends T>> types) {
		ArrayList<T> list = new ArrayList<>();
		
		types.forEach(type -> {
			T obj = createInstance(type);
			
			list.add(obj);
		});
			
		return list;
	}
	
	public static boolean hasEmptyConstructor(Class<?> type) {
		// TODO check for more cases
		return !type.isInterface() 
				&& !Modifier.isAbstract(type.getModifiers())
				&& Arrays.stream(type.getConstructors()).anyMatch(ctr -> ctr.getParameterCount() == 0);
	}

	public static <T> T createInstance(Class<? extends T> type) {
		try {
			Constructor<? extends T> ctr = type.getConstructor();
			
			return ctr.newInstance();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e); // TODO message "no empty constructor"
		} catch (InstantiationException e) {
			throw new RuntimeException(e); // TODO message "is interface, abstract or something like that"
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e); // TODO message "not accessible constructor"
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e); // TODO message "an error inside the constructor"
		} catch (SecurityException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T createInstance(Class<?> type, Class<? extends T> superType) {
		if(!superType.isAssignableFrom(type)) {
			throw new RuntimeException("Not supported type: " + type);
		}
		
		Class<? extends T> suiteClass = type.asSubclass(superType);
		
		return createInstance(suiteClass);
	}

	public static Class<?> getCallerClass() {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();

		if (elements.length >= 4) {
			String className = elements[3].getClassName();
			
			try {
				return Class.forName(className);
			} 
			catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		
		return null;
	}
	
}
