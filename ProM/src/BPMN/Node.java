package BPMN;

/**
 * @(#) BPMN.Node.java
 */
public class Node
{
	public BPMN getOwnerBpmn() {
		return ownerBpmn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	String type= "";
	public void setOwnerBpmn(BPMN ownerBpmn) {
		this.ownerBpmn = ownerBpmn;
	}

	public Node(BPMN ownerBpmn) {
		this.ownerBpmn = ownerBpmn;
		ownerBpmn.getNodes().add(this);
	}

	private BPMN ownerBpmn;
	private SequenceFlow sourceFlow;
	
	private SequenceFlow targetFlow;

	public Node getNextNode(){
		return this.getTargetFlow().getTargetNode();
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

	@Override
	public String toString() {
		return "Node{" +
				"type='" + type + '\'' +
				'}';
	}
}
