package BPMN;

/**
 * @(#) BPMN.Node.java
 */
public class Node
{

	private SequenceFlow sourceFlow;
	
	private SequenceFlow targetFlow;



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
