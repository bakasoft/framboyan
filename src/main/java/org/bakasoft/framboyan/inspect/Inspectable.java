package org.bakasoft.framboyan.inspect;

public interface Inspectable {

  void inspectWith(Inspector output);

  default String inspect() {
    StringBuilder output = new StringBuilder();
    Inspector inspector = new Inspector(output);

    inspectWith(inspector);

    return output.toString();
  }

}
