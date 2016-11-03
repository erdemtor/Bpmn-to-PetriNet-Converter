package ut.systems.modelling;

import BPMN.*;
import BPMN.Gateway;
import BPMN.Event;
import org.processmining.models.graphbased.directed.bpmn.BPMNDiagram;
import org.processmining.models.graphbased.directed.bpmn.elements.*;
import org.processmining.models.shapes.Gate;

import java.util.Collection;
import java.util.List;

/**
 * Created by arikan on 3.11.16.
 */
public class BPMNInputConverter {
    private BPMN bpmn;
    private BPMNDiagram input;
    BPMNInputConverter(BPMNDiagram inputBPMN) {
        bpmn = new BPMN();
        this.input = inputBPMN;
        Collection<Flow> inputFlows = input.getFlows();
        inputFlows.stream().filter(f -> f.getParent() == null).forEach(f -> {
            SequenceFlow sequenceFlow = new SequenceFlow(bpmn);
            handleSource(f, sequenceFlow, bpmn);
            handleTarget(f, sequenceFlow, bpmn);
        });
        arrangeGatewayTypes(bpmn);
    }

    private void arrangeGatewayTypes(BPMN bpmn) {
        bpmn.getNodes().stream().filter(n -> n instanceof Gateway).filter(g -> ((Gateway)g).getTargetFlows().size() > 1).forEach(splitGate -> splitGate.setType(splitGate.getType() + "-split"));
        bpmn.getNodes().stream().filter(n -> n instanceof Gateway).filter(g -> ((Gateway)g).getSourceFlows().size() > 1).forEach(splitGate -> splitGate.setType(splitGate.getType() + "-split"));
    }

    public BPMN getResultBPMN() {
        return bpmn;
    }

    private void handleTarget(Flow f, SequenceFlow sequenceFlow, BPMN bpmn) {
        if (f.getTarget() instanceof org.processmining.models.graphbased.directed.bpmn.elements.Gateway) {
            Gateway g = new Gateway(bpmn);
            if (((org.processmining.models.graphbased.directed.bpmn.elements.Gateway) f.getSource()).getGatewayType() == org.processmining.models.graphbased.directed.bpmn.elements.Gateway.GatewayType.PARALLEL) {
                g.setType("and");
            }
            if (((org.processmining.models.graphbased.directed.bpmn.elements.Gateway) f.getSource()).getGatewayType() == org.processmining.models.graphbased.directed.bpmn.elements.Gateway.GatewayType.DATABASED) {
                g.setType("or");
            }
            g.getSourceFlows().add(sequenceFlow);
            sequenceFlow.setTargetNode(g);
        }
        else if (f.getTarget() instanceof Activity) {
            Task task = new Task(f.getTarget().getLabel(), bpmn);
            task.setSourceFlow(sequenceFlow);
            sequenceFlow.setTargetNode(task);
        }
        else if (f.getTarget() instanceof SubProcess) {
            Compound c = new Compound(f.getTarget().getLabel(), bpmn);
            Collection<Flow> subFlows = input.getFlows((SubProcess) f.getTarget());
            BPMN subBPMN = new BPMN();
            subFlows.forEach((flow) -> {
                SequenceFlow subSF = new SequenceFlow(subBPMN);
                handleSource(flow, subSF, subBPMN);
                handleTarget(flow, subSF, subBPMN);
            });
            arrangeGatewayTypes(subBPMN);
            c.setSubBpmn(subBPMN);
            sequenceFlow.setTargetNode(c);
        }
        else if (f.getTarget() instanceof org.processmining.models.graphbased.directed.bpmn.elements.Event) {
            Event event = new Event("end", bpmn);
            event.setSourceFlow(sequenceFlow);
            sequenceFlow.setTargetNode(event);
        }
    }

    private void handleSource(Flow f, SequenceFlow sequenceFlow, BPMN bpmn) {
        if (f.getSource() instanceof org.processmining.models.graphbased.directed.bpmn.elements.Gateway) {
            Gateway g = new Gateway(bpmn);
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
        else if (f.getSource() instanceof Activity) {
            Task task = new Task(f.getSource().getLabel(), bpmn);
            task.setOwnerBpmn(bpmn);
            task.setTargetFlow(sequenceFlow);
            sequenceFlow.setSourceNode(task);
        }
        else if (f.getSource() instanceof SubProcess) {
            Compound c = new Compound(f.getSource().getLabel(), bpmn);
            Collection<Flow> subFlows = input.getFlows((SubProcess) f.getSource());
            BPMN subBPMN = new BPMN();
            subFlows.forEach((flow) -> {
                SequenceFlow subSF = new SequenceFlow(subBPMN);
                handleSource(flow, subSF, subBPMN);
                handleTarget(flow, subSF, subBPMN);
            });
            c.setSubBpmn(subBPMN);
            sequenceFlow.setSourceNode(c);
        }
        else if (f.getSource() instanceof org.processmining.models.graphbased.directed.bpmn.elements.Event) {
            Event event = new Event("start", bpmn);
            event.setOwnerBpmn(bpmn);
            event.setTargetFlow(sequenceFlow);
            sequenceFlow.setSourceNode(event);
        }
    }

}
