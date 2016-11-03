
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
        while(true) {
            if(current instanceof Event) {
                Event c  = (Event) current;
                if(c.getType().equals("end")) break;
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
                        Petri subPetriBranch =myTuple.getPetri();
                        subPetriBranch.firstPlace().setLABEL("");
                        outgoingPetriList.add(subPetriBranch);
                        lastJoin = myTuple.getCurrent();
                    }
                    PetriUtils.appendToPetri(result, outgoingPetriList, current.getType());
                    current = lastJoin;
                } else if (current.getType().contains("join")) {
                    return new Tuple(result, current);
                }
            }
            else {
                PetriUtils.convertAndAdd(result, current);
            }
            if (current instanceof Gateway) {
               Gateway casted = (Gateway) current;
                current = casted.getNextNode();
            }else {
                if(current.getTargetFlow() == null) break;
                current = current.getNextNode();
            }

        }
        Place last = PetriUtils.getLastPlace(result);
        if (last != null) {
          last.setLABEL("end");
        }
        return new Tuple(result, current);
    }


}

