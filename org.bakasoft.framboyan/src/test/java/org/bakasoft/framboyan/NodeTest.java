package org.bakasoft.framboyan;

import org.bakasoft.framboyan.annotation.Describe;

@Describe("class Node")
public class NodeTest extends Framboyan {{	
	
	it("addNode(Node) should validate the parent", () -> {
		Node node1 = new Node();
		Node node2 = new Node(node1, null, null, null, false, false, null);
		Node node3 = new Node(node2, null, null, null, false, false, null);
		
		node1.addNode(node2);
		
		expect(() -> {
			node1.addNode(node3);
		}).toThrowException();
	});
	
	it("getChildren() should not allow to add children", () -> {
		Node node = new Node();
		
		expect(() -> {
			node.getChildren().add(new Node());	
		}).toThrow(UnsupportedOperationException.class);
	});

	describe("run(boolean) method", () -> {
		
		it("should detect pending case", () -> {
			Node nodeNoAction = new Node(new Node(), null, null, null, false, false, null);	
			Node nodePending = new Node(new Node(), null, null, () -> {}, true, false, null);
			Node nodeNoFocused = new Node(new Node(), null, null, () -> {}, false, false, null);	
			
			expect(nodeNoAction.run(false).isPending()).toBeTrue();
			expect(nodeNoAction.run(true).isPending()).toBeTrue();
			
			expect(nodePending.run(false).isPending()).toBeTrue();
			expect(nodePending.run(true).isPending()).toBeTrue();
			
			expect(nodeNoFocused.run(false).isPending()).toBeFalse();
			expect(nodeNoFocused.run(true).isPending()).toBeTrue();
		});
		
		it("should detect successful case", () -> {
			Node nodeNoPending = new Node(new Node(), null, null, () -> {}, false, false, null);
			Node nodeFocused = new Node(new Node(), null, null, () -> {}, false, true, null);	

			expect(nodeNoPending.run(false).isSuccessful()).toBeTrue();
			expect(nodeNoPending.run(true).isSuccessful()).toBeFalse(); // not focused
			
			expect(nodeFocused.run(false).isSuccessful()).toBeTrue();
			expect(nodeFocused.run(true).isSuccessful()).toBeTrue();
		});
		
		it("should detect error case", () -> {
			Exception exception = new Exception();
			AssertionError error = new AssertionError();
			
			Node nodeException = new Node(new Node(), null, null, () -> { throw exception; }, false, false, null);
			Node nodeError = new Node(new Node(), null, null, () -> { throw error; }, false, false, null);

			expect(nodeException.run(false).getError()).toEqual(exception);
			expect(nodeException.run(true).getError()).toBeNull();
			
			expect(nodeError.run(false).getError()).toEqual(error);
			expect(nodeError.run(true).getError()).toBeNull();
		});
		
		it("should detect console output", () -> {
			Console console = new Console();
			Node nodeNoConsole = new Node(new Node(), null, null, () -> {}, false, false, null);
			Node nodeConsole = new Node(new Node(), null, null, () -> { 
				console.log("TEST");
			}, false, false, console);

			expect(nodeConsole.run(false).getOutput()).toEqual("TEST" + System.lineSeparator());
			expect(nodeConsole.run(true).getOutput()).toBeEmpty();
			expect(nodeNoConsole.run(true).getOutput()).toBeNull();
		});
	
	});
	
	describe("isPending(), isFocused() and getConsole() methods", () -> {
		it("should consider the true and not-null values of the parent", () -> {
			Node parent = new Node(null, null, null, null, true, true, new Console());
			Node node = new Node(parent, null, null, null, false, false, null);
			
			expect(node.isPending()).toBeTrue();
			expect(node.isFocused()).toBeTrue();
			expect(node.getConsole()).not.toBeNull();
		});	
		
		it("should ignore the false and null values of the parent", () -> {
			Node parent = new Node(null, null, null, null, false, false, null);
			Node node1 = new Node(parent, null, null, null, true, true, new Console());
			Node node2 = new Node(parent, null, null, null, false, false, null);
			
			expect(node1.isPending()).toBeTrue();
			expect(node1.isFocused()).toBeTrue();
			expect(node1.getConsole()).not.toBeNull();
			
			expect(node2.isPending()).toBeFalse();
			expect(node2.isFocused()).toBeFalse();
			expect(node2.getConsole()).toBeNull();
		});	
		
		it("should work without parent", () -> {
			Node node1 = new Node(null, null, null, null, true, true, new Console());
			Node node2 = new Node(null, null, null, null, false, false, null);
			
			expect(node1.isPending()).toBeTrue();
			expect(node1.isFocused()).toBeTrue();
			expect(node1.getConsole()).not.toBeNull();
			
			expect(node2.isPending()).toBeFalse();
			expect(node2.isFocused()).toBeFalse();
			expect(node2.getConsole()).toBeNull();
		});	
	});
	
	describe("containFocus() method", () -> {
		it("should detect when the node NOT contain focused children at 1st level", () -> {
			Node parent = new Node();
			
			parent.addNode(new Node(parent, null, null, null, false, false, null));
			parent.addNode(new Node(parent, null, null, null, false, false, null));
			parent.addNode(new Node(parent, null, null, null, false, false, null));
			
			expect(parent.containFocus()).toBeFalse();
		});
		it("should detect when the node contains focused children at 1st level", () -> {
			Node parent = new Node();
			
			parent.addNode(new Node(parent, null, null, null, false, false, null));
			parent.addNode(new Node(parent, null, null, null, false, true, null));
			parent.addNode(new Node(parent, null, null, null, false, false, null));
			
			expect(parent.containFocus()).toBeTrue();
		});	
		it("should detect when the node NOT contain focused children at 2st level", () -> {
			Node parent = new Node();
			Node child1 = new Node(parent, null, null, null, false, false, null);
			Node child2 = new Node(parent, null, null, null, false, false, null);
			
			parent.addNode(child1);
			parent.addNode(child2);
			
			child1.addNode(new Node(child1, null, null, null, false, false, null));
			child1.addNode(new Node(child1, null, null, null, false, false, null));
			
			child2.addNode(new Node(child2, null, null, null, false, false, null));
			child2.addNode(new Node(child2, null, null, null, false, false, null));
			
			expect(parent.containFocus()).toBeFalse();
		});	
		it("should detect when the node contains focused children at 2st level", () -> {
			Node parent = new Node();
			Node child1 = new Node(parent, null, null, null, false, false, null);
			Node child2 = new Node(parent, null, null, null, false, false, null);
			
			parent.addNode(child1);
			parent.addNode(child2);
			
			child1.addNode(new Node(child1, null, null, null, false, false, null));
			child1.addNode(new Node(child1, null, null, null, false, false, null));
			
			child2.addNode(new Node(child2, null, null, null, false, false, null));
			child2.addNode(new Node(child2, null, null, null, false, true, null));
			
			expect(parent.containFocus()).toBeTrue();
		});	
	});
	
	it("findNodes() method should filter the children as expected", () -> {
		Node parent = new Node();
		Node child1 = new Node(parent, null, null, null, false, false, null);
		Node child2 = new Node(parent, null, null, null, false, false, null);
		Node child1_1;
		Node child1_2;
		Node child2_1;
		Node child2_2;
		
		parent.addNode(child1);
		parent.addNode(child2);
		
		child1.addNode(child1_1 = new Node(child1, null, null, null, false, false, null));
		child1.addNode(child1_2 = new Node(child1, null, null, null, false, true, null));
		
		child2.addNode(child2_1 = new Node(child2, null, null, null, false, false, null));
		child2.addNode(child2_2 = new Node(child2, null, null, null, false, true, null));
		
		expect(parent.findNodes(node -> node.isFocused())).toContain(child1_2, child2_2);
		expect(parent.findNodes(node -> node.isFocused())).not.toContain(child1_1, child2_1);
	});
	
	it("joinDescription() should generate the description as expected", () -> {
		Node node0 = new Node(null, null, "0", null, false, false, null);
		Node node1 = new Node(node0, null, "1", null, false, false, null);
		Node node2 = new Node(node1, null, "2", null, false, false, null);
		Node node3 = new Node(node2, null, "3", null, false, false, null);
		
		expect(node3.joinDescriptions(node0)).toEqual("1 2 3");
	});
}}
