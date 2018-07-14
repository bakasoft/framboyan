package org.bakasoft.framboyan;

import java.util.Stack;

public class TestTool {

	public static void generateTargets(String input, Group root) {
		String[] items = input.split(" +");
	
		Stack<Group> stack = new Stack<Group>();
		
		stack.push(root);
		
		int id = 0;
		
		for (String item : items) {
			if (!item.isEmpty()) {
				Group parent = stack.peek();
				
				if ("n(".equals(item) || "x(".equals(item) || "f(".equals(item)) {
					Mode mode = (
							"n(".equals(item) ? Mode.NORMAL : 
							"x(".equals(item) ? Mode.PENDING : 
							"f(".equals(item) ? Mode.FOCUSED : null);
					Group group = new Group("Group" + (id++), mode);
					
					parent.addTarget(group);
					
					stack.push(group);
				}
				else if (")".equals(item)) {
					stack.pop();
				}
				else if ("ns".equals(item) || "xs".equals(item) || "fs".equals(item) || "ne".equals(item) || "xe".equals(item) || "fe".equals(item)) {
					Mode mode = (
							"ns".equals(item) || "ne".equals(item) ? Mode.NORMAL : 
							"xs".equals(item) || "xe".equals(item) ? Mode.PENDING : 
							"fs".equals(item) || "fe".equals(item) ? Mode.FOCUSED : null);
					Spec spec = new Spec("Spec" + (id++), () -> {
						if ("ne".equals(item) || "xe".equals(item) || "fe".equals(item)) {
							throw new RuntimeException();
						}
					}, mode);
					
					parent.addTarget(spec);
				}
				else {
					throw new RuntimeException("unknown instruction: '" + item + "'");
				}
			}	
		}
	}
	
}
