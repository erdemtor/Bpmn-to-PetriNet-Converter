package BPMN;

/**
 * @(#) BPMN.SequenceFlow.java
 */
public class SequenceFlow
{
	
	private Node targetNode;
	
	private Node sourceNode;





	public Node getTargetNode( ) {
		return targetNode;
	}

	public void setTargetNode( Node targetNode ) {
		this.targetNode = targetNode;
	}

	public Node getSourceNode( ) {
		return sourceNode;
	}

	public void setSourceNode( Node sourceNode ) {
		this.sourceNode = sourceNode;
	}

	public Node getTarget( )
	{
		return null;
	}
	
	
}
