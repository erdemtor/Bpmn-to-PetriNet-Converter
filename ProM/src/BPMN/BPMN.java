package BPMN;

import org.processmining.models.graphbased.directed.bpmn.elements.*;
import org.processmining.models.shapes.Gate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @(#) BPMN.BPMN.java
 */
public class BPMN {
    private List<Node> nodes = new ArrayList<>();

    private List<SequenceFlow> flows = new ArrayList<>();

    private Compound ownerCompound;

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return "BPMN{" +
                "flows=" + flows.toString() +
                ", nodes=" + nodes.toString() +
                '}';
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

    public void addNode(Node node) {
        nodes.add(node);
    }


}

