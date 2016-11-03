package BPMN;

/**
 * @(#) BPMN.Node.java
 */
public class Node
{
	private BPMN ownerBpmn;
	
	private SequenceFlow sourceFlow;
	
	private SequenceFlow targetFlow;

	public BPMN getOwnerBpmn( ) {
		return ownerBpmn;
	}

	public void setOwnerBpmn( BPMN ownerBpmn ) {
		this.ownerBpmn = ownerBpmn;
	}

	public SequenceFlow getSourceFlow( ) {
		return sourceFlow;
	}

	public void setSourceFlow( SequenceFlow sourceFlow ) {
		this.sourceFlow = sourceFlow;
	}

	public SequenceFlow getTargetFlow( ) {
		return targetFlow;
	}

	public void setTargetFlow( SequenceFlow targetFlow ) {
		this.targetFlow = targetFlow;
	}
}
