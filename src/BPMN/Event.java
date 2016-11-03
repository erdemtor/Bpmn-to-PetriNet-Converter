package BPMN;

/**
 * @(#) BPMN.Event.java
 */
public class Event extends Node
{
	enum Strings {
		ONE, TWO, THREE
	}

	public Event(String type) {
		this.type = type;
	}
}
