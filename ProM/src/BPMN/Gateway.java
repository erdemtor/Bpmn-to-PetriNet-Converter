package BPMN;

import java.util.List;

/**
 * @(#) BPMN.Gateway.java
 */
public class Gateway extends Node
{
	private List<SequenceFlow> sourceFlows;
	private List<SequenceFlow> targetFlows;
	private String type;

	public Gateway(BPMN ownerBpmn, String type) {
		super(ownerBpmn);
		this.type = type;
	}
	public Gateway(BPMN ownerBpmn) {
		super(ownerBpmn);

	}

	public String getType( ) {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}


	public List<SequenceFlow> getSourceFlows() {
		return sourceFlows;
	}

	public void setSourceFlows(List<SequenceFlow> sourceFlow) {
		this.sourceFlows = sourceFlow;
	}


	public List<SequenceFlow> getTargetFlows() {
		return targetFlows;
	}

	public void setTargetFlow(List<SequenceFlow> targetFlow) {
		this.targetFlows = targetFlow;
	}
}
