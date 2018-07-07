package org.bakasoft.framboyan.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.bakasoft.framboyan.Group;
import org.bakasoft.framboyan.Spec;
import org.bakasoft.framboyan.Target;

public class Toolbox {

	public static String joinSubjects(Target target) {
		ArrayList<String> subjects = new ArrayList<>();
		Target current = target;
		
		while(current != null) {
			Object subject = current.getSubject();
			
			if (subject != null) {
				subjects.add(subject.toString());	
			}
			
			current = current.getParent();
		}
		
		StringBuilder result = new StringBuilder();
		
		for (int i = subjects.size() - 1; i >= 0; i--) {
			if (result.length() > 0) {
				result.append(' ');
			}
			
			result.append(subjects.get(i));
		}
		
		// avoid empty subjects
		if (result.length() == 0) {
			result.append(String.valueOf(target));
		}
		
		return result.toString();
	}

	public static List<Spec> listSpecs(Target target) {
		ArrayList<Spec> specs = new ArrayList<>();
		
		listSpecs(target, specs);
		
		return specs;
	}
	
	private static void listSpecs(Target target, List<Spec> specs) {
		if (target instanceof Spec) {
			specs.add((Spec)target);
		}
		else if (target instanceof Group) {
			Group group = (Group)target;
			
			for (Target subitem : group.getItems()) {
				listSpecs(subitem, specs);
			}	
		}
		else {
			throw new RuntimeException(); // TODO not supported class	
		}
	}
	
	public static String trimEnd(String text) {
		if (text == null) {
			return "";
		}
		
		int lastIndex = text.length() - 1;
		int i = lastIndex;
		
		while (i >= 0 && Character.isWhitespace(text.charAt(i))) {
			i--;
		}
		
		if (i <= 0) {
			return "";
		}
		else if (i != lastIndex) {
			return text.substring(0, i + 1);
		}
		
		return text;
	}

	public static String getStackTrace(Throwable error) {
		String encoding = "UTF-8";
		
		try(
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			PrintStream printStream = new PrintStream(buffer, true, encoding);
		) {
			error.printStackTrace(printStream);
			
			return new String(buffer.toByteArray(), encoding);	
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
