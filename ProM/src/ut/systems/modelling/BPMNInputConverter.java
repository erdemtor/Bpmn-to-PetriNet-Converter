package ut.systems.modelling;

import BPMN.*;
import org.processmining.models.graphbased.directed.bpmn.BPMNDiagram;
import org.processmining.models.graphbased.directed.bpmn.elements.Gateway;
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
        arrangeGateways(input);
        arrangeFlows(input);
        arrangeNodes(input);
    }

    private void arrangeGateways(BPMNDiagram input) {
        Collection<Gateway> inputGateways = input.getGateways();
        for (Gateway g : inputGateways) {

            System.out.println(g.toString());
        }
    }

    private void arrangeFlows(BPMNDiagram input) {

    }

    private void arrangeNodes(BPMNDiagram input) {

    }
}
