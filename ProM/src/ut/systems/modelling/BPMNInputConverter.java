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
    BPMNInputConverter(BPMNDiagram input) {
        bpmn = new BPMN();
        Collection<Flow> inputFlows = input.getFlows();
        for (Flow f : inputFlows) {
            SequenceFlow sequenceFlow = new SequenceFlow(bpmn);

            handleSource(f, sequenceFlow);
            handleTarget(f, sequenceFlow);
        }
    }

    private void handleTarget(Flow f, SequenceFlow sequenceFlow) {
        if (f.getTarget() instanceof org.processmining.models.graphbased.directed.bpmn.elements.Gateway) {
            Gateway g = new Gateway(bpmn);
            if (((org.processmining.models.graphbased.directed.bpmn.elements.Gateway) f.getSource()).getGatewayType() == org.processmining.models.graphbased.directed.bpmn.elements.Gateway.GatewayType.PARALLEL) {
                g.setType("AND");
            }
            if (((org.processmining.models.graphbased.directed.bpmn.elements.Gateway) f.getSource()).getGatewayType() == org.processmining.models.graphbased.directed.bpmn.elements.Gateway.GatewayType.DATABASED) {
                g.setType("OR");
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

        }
        else if (f.getTarget() instanceof org.processmining.models.graphbased.directed.bpmn.elements.Event) {
            Event event = new Event("end", bpmn);
            event.setSourceFlow(sequenceFlow);
            sequenceFlow.setTargetNode(event);
        }
    }

    private void handleSource(Flow f, SequenceFlow sequenceFlow) {
        if (f.getSource() instanceof org.processmining.models.graphbased.directed.bpmn.elements.Gateway) {
            Gateway g = new Gateway();
            if (((org.processmining.models.graphbased.directed.bpmn.elements.Gateway) f.getSource()).getGatewayType() == org.processmining.models.graphbased.directed.bpmn.elements.Gateway.GatewayType.PARALLEL) {
                g.setType("AND");
            }
            if (((org.processmining.models.graphbased.directed.bpmn.elements.Gateway) f.getSource()).getGatewayType() == org.processmining.models.graphbased.directed.bpmn.elements.Gateway.GatewayType.DATABASED) {
                g.setType("OR");
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

        }
        else if (f.getSource() instanceof org.processmining.models.graphbased.directed.bpmn.elements.Event) {
            Event event = new Event("start", bpmn);
            event.setOwnerBpmn(bpmn);
            event.setTargetFlow(sequenceFlow);
            sequenceFlow.setSourceNode(event);
        }
    }

    private void arrangeGateways(BPMNDiagram input) {
        Collection<org.processmining.models.graphbased.directed.bpmn.elements.Gateway> inputGateways = input.getGateways();
        Collection<Flow> inputFlows = input.getFlows();
        for (org.processmining.models.graphbased.directed.bpmn.elements.Gateway g : inputGateways) {
            Gateway gateway = new Gateway();
            if (g.getGatewayType() == org.processmining.models.graphbased.directed.bpmn.elements.Gateway.GatewayType.PARALLEL) {// and
                for (Flow f : inputFlows) {
                    if (f.getTarget() == g) {
                        SequenceFlow inFlow = new SequenceFlow();
                    }
                }
            }
            System.out.println(g.toString());
        }
    }

    private void arrangeFlows(BPMNDiagram input) {

    }

    private void arrangeNodes(BPMNDiagram input) {

    }
}
