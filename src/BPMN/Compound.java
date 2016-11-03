package BPMN;

/**
 * @(#) BPMN.Compound.java
 */
public class Compound extends Task
{
	public Compound(String name) {
		super(name);
	}

	public BPMN getSubBpmn( ) {
		return subBpmn;
	}

	public void setSubBpmn( BPMN subBpmn ) {
		this.subBpmn = subBpmn;
	}

	private BPMN subBpmn;
	
	
}
