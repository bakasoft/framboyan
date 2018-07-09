package org.bakasoft.framboyan.junit;

import org.bakasoft.framboyan.Console;
import org.bakasoft.framboyan.Group;
import org.bakasoft.framboyan.Suite;
import org.junit.runner.RunWith;

@RunWith(FramboyanJUnitRunner.class)
abstract public class FramboyanJUnit extends Suite {

	public FramboyanJUnit() {
		// TODO fix this constructing
		super(new Console(), new Group(null, null, new Console(), false)); 
	}

}
