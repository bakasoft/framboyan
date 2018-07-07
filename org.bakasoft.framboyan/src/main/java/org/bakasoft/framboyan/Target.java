package org.bakasoft.framboyan;

public interface Target {
	
	public Group getParent();

	public Object getSubject();
	
	public boolean isPending();
	
}
