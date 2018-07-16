package org.bakasoft.framboyan;

import org.bakasoft.framboyan.annotation.Describe;

@Describe("Group class")
public class GroupTest extends Framboyan {{

	describe("addTarget(Target) and getTargets() methods", () -> {
		it ("should manage the nested targets", () -> {
			Group group = new Group(null,null);
			
			expect(group.getTargets()).not.toBeNull();
			expect(group.getTargets()).toBeEmpty();
			
			Target subtarget = new Group(null, null);
			
			group.addTarget(subtarget);
			
			expect(group.getTargets().size()).toBe(1);
			expect(group.getTargets().get(0)).toEqual(subtarget);
		});
	});
	
	describe("isPending() method", () -> {
		it("should consider the mode and the targets", () -> {
			template((Mode mode, Boolean children, Boolean result) -> {
				Group group = new Group(null,mode);
				
				if (children) {
					group.addTarget(new Group(null, null));	
				}
				
				expect(group.isPending()).toBe(result);
			})
			//    Mode         Targets?  Pending?
			.test(null         , true  , false )
			.test(null         , false , true  )
			.test(Mode.FOCUSED , true  , false )
			.test(Mode.FOCUSED , false , true  )
			.test(Mode.NORMAL  , true  , false )
			.test(Mode.NORMAL  , false , true  )
			.test(Mode.PENDING , true  , true  )
			.test(Mode.PENDING , false , true  )
			;
		}); 
	});
	
	describe("isFocused() method", () -> {
		it("should consider only the mode", () -> {
			template((Mode mode, Boolean result) -> {
				Group group = new Group(null,mode);
				
				expect(group.isFocused()).toBe(result);
			})
			//    Mode           Focused?
			.test(null         , false )
			.test(Mode.FOCUSED , true  )
			.test(Mode.NORMAL  , false )
			.test(Mode.PENDING , false )
			;
		}); 
	});
	
	describe("isRoot() method", () -> {
		it("should detect subclasses", () -> {
			// TODO rename isRoot() to other name, is confusing
			expect(new Group(null, null).isRoot()).toBeFalse();
			expect(new Group(null, null) { /* subclass */ }.isRoot()).toBeTrue();
		});
	});
	
	describe("buildInto(Node)", () -> {
		it("should build the node as expected", () -> {
			Group root = new Group("root", Mode.NORMAL);
			
			root.addTarget(new Group(null, null));
			
			Node node = root.buildInto(null);
			
			expect(node.getParent()).toBeNull();
			expect(node.getAction()).toBeNull();
			expect(node.getChildren().size()).toBe(1);
			expect(node.getDescription()).toBe("root");
			expect(node.getConsole()).toBeNull();
			expect(node.getTarget()).toBe(root);
			expect(node.isFocused()).toBeFalse();
			expect(node.isPending()).toBeFalse();
		});
	});
	
	describe("toString() method", () -> {
		it ("should generated expected text", () -> {
			Group group = new Group("GROUP", null);
			
			expect(group.toString()).toBe("Group: GROUP");
		});
	});

}}