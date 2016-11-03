package BPMN;

/**
 * @(#) BPMN.SequenceFlow.java
 */
public class SequenceFlow
{
	public BPMN getOwnerBpmn() {
		return ownerBpmn;
	}

	public void setOwnerBpmn(BPMN ownerBpmn) {
		this.ownerBpmn = ownerBpmn;
	}

	public SequenceFlow(BPMN ownerBpmn) {
		this.ownerBpmn = ownerBpmn;
		ownerBpmn.getFlows().add(this);
	}

	private BPMN ownerBpmn;
	private Node targetNode;
	
	private Node sourceNode;


	public Node getTargetNode() {
		return targetNode;
	}

	public void setTargetNode( Node targetNode ) {
		this.targetNode = targetNode;
	}

	public Node getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode( Node sourceNode ) {
		this.sourceNode = sourceNode;
	}

	
}
