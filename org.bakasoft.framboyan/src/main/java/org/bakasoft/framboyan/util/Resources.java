package org.bakasoft.framboyan.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Resources {
	
	public static Map<String, Object> loadJsonMap(String resource) {
		String json = loadString(resource);
		
		return JSON.parseMap(json);
	}

	public static List<Object> loadJsonList(URL url) {
		String json = loadString(url);
		
		return JSON.parseList(json);
	}

	public static List<Object> loadJsonList(String resource) {
		String json = loadString(resource);
		
		return JSON.parseList(json);
	}
	
	public static String loadString(String resource) {
		URL url = getURL(resource);
		
		return loadString(url);
	}

	public static String loadString(String resource, Class<?> origin) {
		URL url = getURL(resource, origin);
		
		return loadString(url);
	}

	public static URL getURL(String resource) {
		return getURL(resource, null);
	}
	
	public static String loadString(URL url) {
		try {
			Path path = Paths.get(url.toURI());
			byte[] data = Files.readAllBytes(path);
		
			return new String(data);
		}
		catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e); // TODO add message
		}
	}
	
	public static URL getURL(String resource, Class<?> type) {
		URL url = null;
		
		if (type == null) {
			type = Reflection.getCallerClass(Resources.class);	
		}
		
		if (type != null) {
			url = type.getResource(resource);
		}
		
		if (url == null) {
			throw new RuntimeException("resource not found: " + resource); // TODO add message
		}

		return url;
	}
	
}
