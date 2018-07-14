package org.bakasoft.framboyan.diff;

import java.io.PrintWriter;

import org.bakasoft.framboyan.util.Toolbox;

public class Diff {

	public static void print(PrintWriter out, String actual, String expected) {
		String[] actualLines = Toolbox.split(actual, '\n');
		String[] expectedLines = Toolbox.split(expected, '\n');
		int maxLength = Math.max(actualLines.length, expectedLines.length);
		
		for (int i = 0; i < maxLength; i++) {
			String actualLine = (i < actualLines.length ? actualLines[i] : null);
			String expectedLine = (i < expectedLines.length ? expectedLines[i] : null);
			
			if (actualLine != null && expectedLine == null) {
				out.println("+ " + actualLine);
			}
			else if (actualLine == null && expectedLine != null) {
				out.println("- " + expectedLine);
			}
			else if (actualLine.equals(expectedLine)) {
				out.println("  " + actualLine);
			}
			else {
				out.println("- " + expectedLine);
				out.println("+ " + actualLine);
			}
		}
	}

}
