
package Converter;

/**
 * Created by Erdem on 03-Nov-16.
 */

import BPMN.*;
import Petri.*;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    public static Petri convert(BPMN bpmn){
        return recConvert(bpmn.getStartEvent()).getPetri();
    }
    public static Tuple recConvert(Node current) {
        Petri result = new Petri();
        new Place(current.toString(),"start",result);
        while(!(current instanceof Event && current.getType().equals("end"))) {
            if(current instanceof Event) {
                current = current.getNextNode();
                continue;
            }
            if (current instanceof Gateway) {
                if (current.getType().contains("split")) {
                    List<Petri> outgoingPetriList = new ArrayList<>();
                    Node lastJoin = null;
                    for (SequenceFlow flow : ((Gateway) current).getTargetFlows()) {
                        Node next = flow.getTargetNode();
                        Tuple myTuple = recConvert(next);
                        outgoingPetriList.add(myTuple.getPetri());
                        lastJoin = myTuple.getCurrent();
                    }
                    appendToPetri(result, outgoingPetriList, current.getType());
                    current = lastJoin;
                }
                if (current.getType().contains("join")) {
                    return new Tuple(result, current);
                }
            }
            else {
                convertAndAdd(result, current);
            }
            current = current.getNextNode();
        }
        new Place(current.toString(),"end",result);
        return new Tuple(result, current);
    }

    public static void appendToPetri(Petri result, List<Petri> ptrList, String splitType) {
        if (splitType.equals("xor-split")) {
            for(Petri tempPetri : ptrList){
                getLastPlace(result).getOutgoingTransitions().addAll(getStartPlace(tempPetri).getOutgoingTransitions());
            }
            Place joinPlace = new Place("JoinPlace","", result);
            for(Petri tempPetri : ptrList){
                result.getPlaces().addAll(tempPetri.getPlaces());
                result.getTransitions().addAll(tempPetri.getTransitions());
                removeLastPlace(tempPetri);
                getLastTransition(tempPetri).getTargetPlaces().add(joinPlace);
            }
        }
        if (splitType.equals("and-split")) {
            Transition andSplitTransition = new Transition("ANDSPLIT",result);
            getLastPlace(result).getOutgoingTransitions().add(andSplitTransition);
            for(Petri subPetri : ptrList){
                andSplitTransition.getTargetPlaces().add(subPetri.firstPlace());
            }
            Transition joinTransition = new Transition("ANDJOIN",result);
            for(Petri subPetri : ptrList){
                result.getPlaces().addAll(subPetri.getPlaces());
                result.getTransitions().addAll(subPetri.getTransitions());
                getLastPlace(subPetri).getOutgoingTransitions().add(joinTransition);
            }
        }
    }

    public static void convertAndAdd(Petri petri, Node current) {
        if(current == null) return;
        if (current instanceof Compound) {
            Petri smallPetri = Converter.convert(current.getOwnerBpmn());
            petri.getPlaces().addAll(smallPetri.getPlaces());
            petri.getTransitions().addAll(smallPetri.getTransitions());
            removeLastPlace(petri);
            getLastTransition(petri).getTargetPlaces().add(getStartPlace(smallPetri));
        } else {
            Transition tra = new Transition(current.toString(),petri);
            Place plc = new Place(current.toString(),"", petri);
            tra.getTargetPlaces().add(plc);
            getLastPlace(petri).getOutgoingTransitions().add(tra);
        }
    }
    public static Place getStartPlace(Petri petri) {
        for(Place plc : petri.getPlaces()) {
            if (plc.getType().equals("start")){
                return plc;
            }
        }
        return null;
    }
    public static Place getLastPlace(Petri petri){
        for(Place plc : petri.getPlaces()) {
            if (plc.getOutgoingTransitions().size() == 0)	return plc;
        }
        return null;
    }

    public static Transition getLastTransition(Petri petri){
        for(Transition tra : petri.getTransitions()) {
            if (tra.getTargetPlaces().size() == 0)	return tra;
        }
        return null;
    }

    public static void removeLastPlace(Petri petri){
        Place last = getLastPlace(petri);
        petri.getPlaces().remove(last);
        for(Transition tra : petri.getTransitions()) {
            if (tra.getTargetPlaces().contains(last)) {
                tra.getTargetPlaces().remove(last);
            }
        }
    }
}

