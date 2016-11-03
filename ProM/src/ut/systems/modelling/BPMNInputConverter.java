package ut.systems.modelling;

import BPMN.*;
import BPMN.Gateway;
import BPMN.Event;
import BPMN.Node;
import BPMN.SequenceFlow;
import de.hpi.bpt.process.GatewayType;
import org.processmining.models.graphbased.directed.bpmn.BPMNDiagram;
import org.processmining.models.graphbased.directed.bpmn.BPMNNode;
import org.processmining.models.graphbased.directed.bpmn.elements.*;
import org.processmining.models.shapes.Gate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by arikan on 3.11.16.
 */
public class BPMNInputConverter {
    private HashMap<BPMNNode, Node> map = new HashMap<BPMNNode, Node>();
    private HashMap<BPMNNode, Node> tempMapforSubProcess = new HashMap<BPMNNode, Node>();
    private BPMN bpmn;
    private BPMNDiagram input;
    BPMNInputConverter(BPMNDiagram inputBPMN) {
        bpmn = new BPMN();
        this.input = inputBPMN;
        Collection<Flow> inputFlows = input.getFlows();
        inputFlows.stream().filter(f -> f.getParent() == null).forEach(f -> {
            SequenceFlow sequenceFlow = new SequenceFlow(bpmn);
            handleSource(f, sequenceFlow, bpmn, map);
            handleTarget(f, sequenceFlow, bpmn, map);
        });
        arrangeGatewayTypes(bpmn);
    }


    private void arrangeGatewayTypes(BPMN bpmn) {
        bpmn.getNodes().stream().filter(n -> n instanceof Gateway).filter(g -> ((Gateway)g).getTargetFlows().size() > 1).forEach(splitGate -> splitGate.setType(splitGate.getType() + "-split"));
        bpmn.getNodes().stream().filter(n -> n instanceof Gateway).filter(g -> ((Gateway)g).getSourceFlows().size() > 1).forEach(splitGate -> splitGate.setType(splitGate.getType() + "-split"));
    }

    BPMN getResultBPMN() {
        return bpmn;
    }

    private void handleSource(Flow f, SequenceFlow sequenceFlow, BPMN bpmn, HashMap<BPMNNode, Node> map) {
        if (f.getSource() instanceof org.processmining.models.graphbased.directed.bpmn.elements.Gateway) {
            Gateway g;
            if (map.containsKey(f.getSource())) g = (Gateway) map.get(f.getSource());
            else {
                g = new Gateway(bpmn);
                map.put(f.getSource(), g);
            }
            if (((org.processmining.models.graphbased.directed.bpmn.elements.Gateway) f.getSource()).getGatewayType() == org.processmining.models.graphbased.directed.bpmn.elements.Gateway.GatewayType.PARALLEL) {
                g.setType("and");
            }
            if (((org.processmining.models.graphbased.directed.bpmn.elements.Gateway) f.getSource()).getGatewayType() == org.processmining.models.graphbased.directed.bpmn.elements.Gateway.GatewayType.DATABASED) {
                g.setType("or");
            }
            g.setOwnerBpmn(bpmn);
            g.getTargetFlows().add(sequenceFlow);
            sequenceFlow.setSourceNode(g);
        }
        else if (f.getSource() instanceof SubProcess) {
            Compound c;
            if (map.containsKey(f.getSource())) c = (Compound) map.get(f.getSource());
            else {
                c = new Compound(f.getSource().getLabel(), bpmn);
                map.put(f.getSource(), c);
            }
            Collection<Flow> subFlows = input.getFlows((SubProcess) f.getSource());
            BPMN subBPMN = new BPMN();
            tempMapforSubProcess = new HashMap<BPMNNode, Node>();
            subFlows.forEach((flow) -> {
                SequenceFlow subSF = new SequenceFlow(subBPMN);
                handleSource(flow, subSF, subBPMN, tempMapforSubProcess);
                handleTarget(flow, subSF, subBPMN, tempMapforSubProcess);
            });
            c.setSubBpmn(subBPMN);
            c.setTargetFlow(sequenceFlow);
            sequenceFlow.setSourceNode(c);
        }
        else if (f.getSource() instanceof Activity) {
            Task task;
            if (map.containsKey(f.getSource())) task = (Task)map.get(f.getSource());
            else {
                task = new Task(f.getSource().getLabel(), bpmn);
                map.put(f.getSource(), task);
            }
            task.setOwnerBpmn(bpmn);
            task.setTargetFlow(sequenceFlow);
            sequenceFlow.setSourceNode(task);
        }
        else if (f.getSource() instanceof org.processmining.models.graphbased.directed.bpmn.elements.Event) {
            Event event;
            if (map.containsKey(f.getSource())) event = (Event) map.get(f.getSource());
            else {
                event = new Event("start", bpmn);
                map.put(f.getSource(), event);
            }
            event.setOwnerBpmn(bpmn);
            event.setTargetFlow(sequenceFlow);
            sequenceFlow.setSourceNode(event);
        }
    }

    private void handleTarget(Flow f, SequenceFlow sequenceFlow, BPMN bpmn, HashMap<BPMNNode, Node> map) {
        if (f.getTarget() instanceof org.processmining.models.graphbased.directed.bpmn.elements.Gateway) {
            Gateway g;
            if (map.containsKey(f.getTarget())) g = (Gateway) map.get(f.getTarget());
            else {
                g = new Gateway(bpmn);
                map.put(f.getTarget(), g);
            }
            if (((org.processmining.models.graphbased.directed.bpmn.elements.Gateway) f.getTarget()).getGatewayType() == org.processmining.models.graphbased.directed.bpmn.elements.Gateway.GatewayType.PARALLEL) {
                g.setType("and");
            }
            if (((org.processmining.models.graphbased.directed.bpmn.elements.Gateway) f.getTarget()).getGatewayType() == org.processmining.models.graphbased.directed.bpmn.elements.Gateway.GatewayType.DATABASED) {
                g.setType("or");
            }
            g.getSourceFlows().add(sequenceFlow);
            sequenceFlow.setTargetNode(g);
        }
        else if (f.getTarget() instanceof SubProcess) {
            Compound c;
            if (map.containsKey(f.getTarget())) c = (Compound) map.get(f.getTarget());
            else {
                c = new Compound(f.getTarget().getLabel(), bpmn);
                map.put(f.getTarget(), c);
            }
            Collection<Flow> subFlows = input.getFlows((SubProcess) f.getTarget());
            BPMN subBPMN = new BPMN();
            tempMapforSubProcess = new HashMap<BPMNNode, Node>();
            subFlows.forEach((flow) -> {
                SequenceFlow subSF = new SequenceFlow(subBPMN);
                handleSource(flow, subSF, subBPMN, tempMapforSubProcess);
                handleTarget(flow, subSF, subBPMN, tempMapforSubProcess);
            });
            arrangeGatewayTypes(subBPMN);
            c.setSubBpmn(subBPMN);
            c.setSourceFlow(sequenceFlow);
            sequenceFlow.setTargetNode(c);
        }
        else if (f.getTarget() instanceof Activity) {
            Task task;
            if (map.containsKey(f.getTarget())) task = (Task)map.get(f.getTarget());
            else {
                task = new Task(f.getTarget().getLabel(), bpmn);
                map.put(f.getTarget(), task);
            }
            task.setSourceFlow(sequenceFlow);
            sequenceFlow.setTargetNode(task);
        }

        else if (f.getTarget() instanceof org.processmining.models.graphbased.directed.bpmn.elements.Event) {
            Event event;
            if (map.containsKey(f.getTarget())) event = (Event) map.get(f.getTarget());
            else {
                 event = new Event("end", bpmn);
                map.put(f.getTarget(), event);
            }
            event.setSourceFlow(sequenceFlow);
            sequenceFlow.setTargetNode(event);
        }
    }

}
