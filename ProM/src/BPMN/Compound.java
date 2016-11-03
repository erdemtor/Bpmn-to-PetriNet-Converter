package BPMN;

/**
 * @(#) BPMN.Compound.java
 */
public class Compound extends Task
{


	public Compound(String name, BPMN bpmn) {
		super(name, bpmn);
	}

	public BPMN getSubBpmn( ) {
		return subBpmn;
	}

	public void setSubBpmn( BPMN subBpmn ) {
		this.subBpmn = subBpmn;
	}

	private BPMN subBpmn;
	
	
}
