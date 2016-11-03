package Converter;

/**
 * Created by Erdem on 03-Nov-16.
 */
import BPMN.*;
import Petri.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erdem on 03-Nov-16.
 */
public class Application {


    public static void main(String[] args) {
        BPMN bpmn = createFullBpmn();
        System.out.println();
        Petri p = Converter.convert(bpmn);
        System.out.println();

    }

    public static BPMN createFullBpmn(){
        BPMN bpmn = new BPMN();
        SequenceFlow lastFlow = initializer(bpmn);
        lastFlow = addNodeAndFlow(bpmn, lastFlow, 1);
        lastFlow = addNodeAndFlow(bpmn, lastFlow, 2);
        List<SequenceFlow> outFlows = addOutGateway(bpmn,lastFlow,2,"and-split");
        int i =1;
        List<SequenceFlow> joiningFlows = new ArrayList<>();
        for (SequenceFlow f: outFlows) {
            lastFlow = addNodeAndFlow(bpmn,f, 30 +i);
            lastFlow = addNodeAndFlow(bpmn,lastFlow, 40 + i);
            joiningFlows.add(lastFlow);
            i++;
        }
        lastFlow = addJoinGateway(bpmn,joiningFlows,"and-join");
        lastFlow = addNodeAndFlow(bpmn, lastFlow, 3);
        finalizer(bpmn, lastFlow);
        return bpmn;
    }

    public static SequenceFlow initializer(BPMN bpmn){
        Event start = new Event("start" ,bpmn);
        start.setSourceFlow(null);
        SequenceFlow flow = new SequenceFlow(bpmn);
        flow.setSourceNode(start);
        start.setTargetFlow(flow);
        return flow;
    }

    public static SequenceFlow addJoinGateway(BPMN bpmn, List<SequenceFlow> lastFlows, String type){
        Gateway join = new Gateway(bpmn, type);
        for (SequenceFlow lastFlow: lastFlows) {
            join.getSourceFlows().add(lastFlow);
            lastFlow.setTargetNode(join);
        }
        SequenceFlow out = new SequenceFlow(bpmn);
        join.getTargetFlows().add(out);
        out.setSourceNode(join);
        return out;
    }

    public static List<SequenceFlow> addOutGateway(BPMN bpmn, SequenceFlow lastFlow, int outNumber, String type){
        Gateway andSplit = new Gateway(bpmn, type);
        lastFlow.setTargetNode(andSplit);
        andSplit.setSourceFlow(lastFlow);
        List<SequenceFlow> outgoingFlows = new ArrayList<>();
        for (int i = 0; i <outNumber ; i++) {
            SequenceFlow outFlow = new SequenceFlow(bpmn);
            outFlow.setSourceNode(andSplit);
            outgoingFlows.add(outFlow);
        }
        andSplit.setTargetFlows(outgoingFlows);
        return outgoingFlows;
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
        SequenceFlow newSequenceFlow = new SequenceFlow(bpmn);
        task.setTargetFlow(newSequenceFlow);
        task.setSourceFlow(lastFlow);
        newSequenceFlow.setSourceNode(task);
        return newSequenceFlow;
    }
}
