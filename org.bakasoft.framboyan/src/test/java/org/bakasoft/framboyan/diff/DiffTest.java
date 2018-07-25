package org.bakasoft.framboyan.diff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bakasoft.framboyan.Framboyan;
import org.bakasoft.framboyan.util.Reflection;
import org.bakasoft.framboyan.util.Resources;

public class DiffTest extends Framboyan {{
	for (String file : new String[] {
		"CloseToDiff.json",
		"CompareDiff.json",
		"CompareListDiff.json",
		"CompareNumberDiff.json",
		"CompareStringDiff.json",
		"ContainDiff.json",
		"ContainListDiff.json",
		"ContainStringDiff.json",
		"EmptyDiff.json",
		"EndWithDiff.json",
		"EndWithListDiff.json",
		"EndWithStringDiff.json",
		"EqualDiff.json",
		"InstanceOfDiff.json",
		"MatchDiff.json",
		"SameClassDiff.json",
		"SimilarDiff.json",
		"StartWithDiff.json",
		"StartWithListDiff.json",
		"StartWithStringDiff.json",
	}) {
		Map<?, ?> map = Resources.loadJsonMap(file);
		String className = String.valueOf(map.get("class"));
		List<?> cases = (List<?>)map.get("cases");
		Class<?> diffClass;
		try {
			diffClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new AssertionError(e);
		}
		
		class DiffCase {
			public Diff diff;
			public List<?> arguments;
			public Boolean result;
			public String expectMessage;
			public String notExpectMessage;
			public String actualValue;
			public String expectedValue;
			public String difference;
		}
		
		ArrayList<DiffCase> diffCases = new ArrayList<>();
		
		for (Object testCaseObj : cases) {
			Map<?, ?> testCase = (Map<? ,?>)testCaseObj;
			
			DiffCase diffCase = new DiffCase();
			diffCase.arguments = (List<?>)testCase.get("arguments");
			diffCase.diff = (Diff)Reflection.createInstance(diffClass, diffCase.arguments.toArray());
			diffCase.result = (Boolean)testCase.get("result");
			diffCase.expectMessage = (String)testCase.get("expectMessage");
			diffCase.notExpectMessage = (String)testCase.get("notExpectMessage");
			diffCase.actualValue = (String)testCase.get("actualValue");
			diffCase.expectedValue = (String)testCase.get("expectedValue");	
			diffCase.difference =  (String)testCase.get("difference");
			
			diffCases.add(diffCase);
		}
		
		describe("class " + diffClass.getSimpleName() + " (" + file + ")", () -> {

			if (diffCases.stream().anyMatch(diffCase -> diffCase.result != null)) {
				it("method test() should return the expected value", () -> {
					for (DiffCase diffCase : diffCases) {
						if (diffCase.result != null) {
							console.log("using arguments: " + diffCase.arguments);

							expect(diffCase.diff.test()).toBe(diffCase.result);
						}
					}
				});
			}
			
			if (diffCases.stream().anyMatch(diffCase -> diffCase.expectMessage != null)) {
				it("method generateExpectMessage() should return the expected text", () -> {
					for (DiffCase diffCase : diffCases) {
						if (diffCase.expectMessage != null) {
							console.log("using arguments: " + diffCase.arguments);
							
							expect(diffCase.diff.generateExpectMessage()).toBe(diffCase.expectMessage);	
						}
					}
				});
			}
			
			if (diffCases.stream().anyMatch(diffCase -> diffCase.notExpectMessage != null)) {
				it("method generateNotExpectMessage() should return the expected text", () -> {
					for (DiffCase diffCase : diffCases) {
						if (diffCase.notExpectMessage != null) {
							console.log("using arguments: " + diffCase.arguments);
							
							expect(diffCase.diff.generateNotExpectMessage()).toBe(diffCase.notExpectMessage);
						}
					}
				});
			}
			
			if (diffCases.stream().anyMatch(diffCase -> diffCase.actualValue != null)) {
				it("method generateActualValue() should return the expected text", () -> {
					for (DiffCase diffCase : diffCases) {
						if (diffCase.actualValue != null) {
							console.log("using arguments: " + diffCase.arguments);
							
							expect(diffCase.diff.generateActualValue()).toBe(diffCase.actualValue);	
						}
					}
				});
			}
			
			if (diffCases.stream().anyMatch(diffCase -> diffCase.expectedValue != null)) {
				it("method generateExpectedValue() should return the expected text", () -> {
					for (DiffCase diffCase : diffCases) {
						if (diffCase.expectedValue != null) {
							console.log("using arguments: " + diffCase.arguments);
							
							expect(diffCase.diff.generateExpectedValue()).toBe(diffCase.expectedValue);	
						}
					}
				});
			}
			
			if (diffCases.stream().anyMatch(diffCase -> diffCase.difference != null)) {
				it("method generateDifference() should return the expected text", () -> {
					for (DiffCase diffCase : diffCases) {
						if (diffCase.difference != null) {
							console.log("using arguments: " + diffCase.arguments);
							
							expect(diffCase.diff.generateDifference()).toBe(diffCase.difference);	
						}
					}
				});
			}
		});
	}
}}