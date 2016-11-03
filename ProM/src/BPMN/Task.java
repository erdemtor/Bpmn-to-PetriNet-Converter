package BPMN;

/**
 * @(#) BPMN.Task.java
 */
public class Task extends Node
{
	private String name;

	public String getName( ) {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public Task(String name) {
		this.name = name;
	}
}
