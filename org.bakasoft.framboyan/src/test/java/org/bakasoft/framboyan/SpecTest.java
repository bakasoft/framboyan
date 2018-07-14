package org.bakasoft.framboyan;

public class SpecTest extends Framboyan {{

	describe(Spec.class, () -> {		
		describe("constructor", () -> {
			it("should populate the properties", () -> {
				Object subject = "subject";
				Action action = () -> {};
				Mode mode = Mode.NORMAL;
				Spec spec = new Spec(subject, action, mode);
				
				expect(spec.getDescription()).toBe(subject);
				expect(spec.getAction()).toBe(action);
				expect(spec.isPending()).toBeFalse();
				expect(spec.isFocused()).toBeFalse();
			});
		});
		
		describe("isPending() and isFocused() methods", () -> {
			it("should take the correct value taking into account all factors", () -> {
				template((Mode parentMode, Action action, Mode mode, Boolean pending, Boolean focused) -> {
					Group parent = new Group(null, parentMode);
					Spec spec = new Spec(null, action, mode);
					
					parent.addTarget(spec);
					
					Node nodeRoot = new Node();
					Node nodeParent = parent.buildInto(nodeRoot);
					Node nodeSpec = nodeParent.getChildren().get(0);
					
					expect(nodeSpec.isPending()).toBe(pending);
					expect(nodeSpec.isFocused()).toBe(focused);
				})
				//    Parent       | Action   | Mode         | Pending | Focused
				
				// Normal specs
				.test(null         , () -> {} , null         , false   , false   )
				.test(null         , () -> {} , Mode.NORMAL  , false   , false   )
				.test(Mode.NORMAL  , () -> {} , null         , false   , false   )
				.test(Mode.NORMAL  , () -> {} , Mode.NORMAL  , false   , false   )

				// Pending specs
				.test(null         , () -> {} , Mode.PENDING , true    , false   )
				.test(null         , null     , null         , true    , false   )
				.test(null         , null     , Mode.NORMAL  , true    , false   )
				.test(null         , null     , Mode.PENDING , true    , false   )
				.test(Mode.NORMAL  , () -> {} , Mode.PENDING , true    , false   )
				.test(Mode.NORMAL  , null     , null         , true    , false   )
				.test(Mode.NORMAL  , null     , Mode.NORMAL  , true    , false   )
				.test(Mode.NORMAL  , null     , Mode.PENDING , true    , false   )
				.test(Mode.PENDING , () -> {} , null         , true    , false   )
				.test(Mode.PENDING , () -> {} , Mode.NORMAL  , true    , false   )
				.test(Mode.PENDING , () -> {} , Mode.PENDING , true    , false   )
				.test(Mode.PENDING , null     , null         , true    , false   )
				.test(Mode.PENDING , null     , Mode.NORMAL  , true    , false   )
				.test(Mode.PENDING , null     , Mode.PENDING , true    , false   )
				
				// Focused specs
				.test(null         , () -> {} , Mode.FOCUSED   , false   , true    )
				.test(Mode.NORMAL  , () -> {} , Mode.FOCUSED   , false   , true    )
				.test(Mode.FOCUSED   , () -> {} , null         , false   , true    )
				.test(Mode.FOCUSED   , () -> {} , Mode.NORMAL  , false   , true    )
				.test(Mode.FOCUSED   , () -> {} , Mode.FOCUSED   , false   , true    )
				
				// Pending and Focused specs
				.test(null         , null     , Mode.FOCUSED   , true    , true    )
				.test(Mode.NORMAL  , null     , Mode.FOCUSED   , true    , true    )
				.test(Mode.PENDING , () -> {} , Mode.FOCUSED   , true    , true    )
				.test(Mode.PENDING , null     , Mode.FOCUSED   , true    , true    )
				.test(Mode.FOCUSED   , () -> {} , Mode.PENDING , true    , true    )
				.test(Mode.FOCUSED   , null     , null         , true    , true    )
				.test(Mode.FOCUSED   , null     , Mode.NORMAL  , true    , true    )
				.test(Mode.FOCUSED   , null     , Mode.PENDING , true    , true    )
				.test(Mode.FOCUSED   , null     , Mode.FOCUSED   , true    , true    )
				;
			});
			
		});
		
		describe("toString() method", () -> {
			it ("should generated expected text", () -> {
				Group group = new Group("GROUP", null);
				Spec spec = new Spec("SPEC", null, null);
				
				expect(group.toString()).toBe("Group: GROUP");
				expect(spec.toString()).toBe("Spec: SPEC");
			});
		});
	});
	
}}