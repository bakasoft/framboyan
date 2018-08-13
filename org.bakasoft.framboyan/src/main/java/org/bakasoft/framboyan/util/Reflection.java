package org.bakasoft.framboyan.util;

import org.bakasoft.framboyan.exceptions.CreatingInstanceException;
import org.bakasoft.framboyan.exceptions.MissingConstructorException;
import org.bakasoft.framboyan.exceptions.NotSupportedClassException;

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

	public static boolean isCompatible(Class<?> type, Object obj) {
		if (type == null) {
			return false;
		}
		else if (obj == null) {
			return !type.isPrimitive();
		}
		
		return type.isAssignableFrom(obj.getClass());
	}
	
	public static Constructor<?> getConstructor(Class<?> type, Object... args) {
		for (Constructor<?> ctr : type.getConstructors()) {
			Class<?>[] paramTypes = ctr.getParameterTypes();
			
			if (paramTypes.length == args.length) {
				for (int i = 0; i < args.length; i++) {
					if (!isCompatible(paramTypes[i], args[i])) {
						continue;
					}
				}
				
				return ctr;
			}
		}
		
		return null;
	}

	public static <T> T createInstance(Class<? extends T> type, Object... args) {
		Constructor<?> ctr = getConstructor(type, args);
		
		if (ctr == null) {
			throw new MissingConstructorException(type, args);
		}
		
		try {	
			Object obj = ctr.newInstance(args);
			
			return type.cast(obj);
		} catch (InstantiationException e) {
			throw new CreatingInstanceException(e);
		} catch (IllegalAccessException e) {
			throw new CreatingInstanceException(e);
		} catch (InvocationTargetException e) {
			Throwable realException = e.getCause();
			if (realException instanceof RuntimeException) {
				throw (RuntimeException)realException;
			}
			else {
				throw new CreatingInstanceException(realException);
			}
		} catch (SecurityException | IllegalArgumentException e) {
			throw new CreatingInstanceException(e);
		}
	}

	public static <T> T createInstanceAs(Class<?> type, Class<? extends T> superType) {
		if(!superType.isAssignableFrom(type)) {
			throw new NotSupportedClassException(type, superType);
		}
		
		Class<? extends T> suiteClass = type.asSubclass(superType);
		
		return createInstance(suiteClass);
	}

	public static Class<?> getCallerClass(Class<?> excludeClass) {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		String excludedClassName = excludeClass.getName();
		
		for(int i = 2; i < elements.length; i++) {
			String className = elements[i].getClassName();
			
			if (!className.equals(excludedClassName)) {
				try {
					return Class.forName(className);
				} 
				catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}	
			}
		}
		
		return null;
	}
	
}
