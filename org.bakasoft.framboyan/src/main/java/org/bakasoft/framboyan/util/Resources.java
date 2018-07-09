package org.bakasoft.framboyan.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Resources {
	
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
	
	public static String loadString(String resource) {
		URL url = ClassLoader.getSystemClassLoader().getResource(resource);
		
		if (url == null) {
			throw new RuntimeException("resource not found: " + resource); // TODO add message
		}
		
		return loadString(url);
	}

	public static String loadString(String resource, Class<?> origin) {
		URL url = origin.getResource(resource);

		if (url == null) {
			throw new RuntimeException("resource not found: " + resource); // TODO add message
		}
		
		return loadString(url);
	}
	
}
