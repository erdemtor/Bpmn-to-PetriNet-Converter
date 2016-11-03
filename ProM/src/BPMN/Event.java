package BPMN;

/**
 * @(#) BPMN.Event.java
 */
public class Event extends Node
{
	public Event(String type, BPMN bpmn) {
		super(bpmn);
		this.type = type;
	}
}
