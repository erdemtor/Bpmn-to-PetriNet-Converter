package Converter;

/**
 * Created by Erdem on 03-Nov-16.
 */
import BPMN.*;
import Petri.*;

/**
 * Created by Erdem on 03-Nov-16.
 */
public class Application {


    public static void main(String[] args) {
        BPMN bpmn = createFullBpmn();
        System.out.println();
        Petri p = Converter.convert(bpmn);
        System.out.println(p);

    }
    public static BPMN createFullBpmn(){
        BPMN bpmn = new BPMN();
        SequenceFlow lastFlow = initializer(bpmn);
        for (int i = 0; i <10 ; i++) {
            lastFlow = addNodeAndFlow(bpmn, lastFlow, i);
        }
        finalizer(bpmn, lastFlow);
        return bpmn;
    }

    public static SequenceFlow initializer(BPMN bpmn){
        Event start = new Event("start" ,bpmn);
        start.setSourceFlow(null);
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceNode(start);
        start.setTargetFlow(flow);
        bpmn.getNodes().add(start);
        bpmn.getFlows().add(flow);
        return flow;
    }
    public static void finalizer(BPMN bpmn, SequenceFlow lastFlow){
        Event end = new Event("end", bpmn);
        lastFlow.setTargetNode(end);
        end.setSourceFlow(lastFlow);
        bpmn.getNodes().add(end);
    }


    public static SequenceFlow addNodeAndFlow(BPMN bpmn, SequenceFlow lastFlow, int taskname){
        Simple task = new Simple(taskname+"",bpmn);
        lastFlow.setTargetNode(task);
        SequenceFlow newSequenceFlow = new SequenceFlow();
        task.setTargetFlow(newSequenceFlow);
        task.setSourceFlow(lastFlow);
        newSequenceFlow.setSourceNode(task);
        bpmn.getNodes().add(task);
        bpmn.getFlows().add(newSequenceFlow);
        return newSequenceFlow;
    }
}
