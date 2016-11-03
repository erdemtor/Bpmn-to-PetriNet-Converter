package BPMN;

import java.util.List;

/**
 * @(#) BPMN.BPMN.java
 */
public class BPMN {
	private List<Node> nodes;

	private List<SequenceFlow> flows;

	private Compound ownerCompound;

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<SequenceFlow> getFlows() {
		return flows;
	}

	public void setFlows(List<SequenceFlow> flows) {
		this.flows = flows;
	}

	public Compound getOwnerCompound() {
		return ownerCompound;
	}

	public void setOwnerCompound(Compound ownerCompound) {
		this.ownerCompound = ownerCompound;
	}
}

