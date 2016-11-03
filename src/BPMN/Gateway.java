package BPMN;

/**
 * @(#) BPMN.Gateway.java
 */
public class Gateway extends Node
{
	private String type;

	public String getType( ) {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}
}
