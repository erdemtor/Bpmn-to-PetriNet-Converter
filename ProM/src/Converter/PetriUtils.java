package Converter;

import BPMN.*;
import Petri.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Erdem on 03-Nov-16.
 */
public class PetriUtils {

    public static void appendToPetri(Petri result, List<Petri> ptrList, String splitType) {
        if (splitType.equals("xor-split")) {
            Place lastPlace =  getLastPlace(result);
            for(Petri tempPetri : ptrList){
                lastPlace.getOutgoingTransitions().addAll(firstPlace(tempPetri).getOutgoingTransitions());
            }
            Place joinPlace = new Place("XORJoinPlace","", result);
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
                andSplitTransition.getTargetPlaces().add(firstPlace(subPetri));
            }
            Transition joinTransition = new Transition("ANDJOIN",result);
            Place afterJoin = new Place("afterJoin","",result);
            joinTransition.getTargetPlaces().add(afterJoin);
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
            Petri smallPetri = Controller.convertToPetri(((Compound) current).getSubBpmn());
            petri.setPlaces(Stream.concat(petri.getPlaces().stream(), smallPetri.getPlaces().stream()).collect(Collectors.toList()));
            petri.setTransitions(Stream.concat(petri.getTransitions().stream(), smallPetri.getTransitions().stream()).collect(Collectors.toList()));
            Transition connector = new Transition("invisible", petri);
            connector.getTargetPlaces().add( firstPlace(smallPetri));
            getLastPlace(petri).getOutgoingTransitions().add(connector);
            petri.getTransitions().add(connector);
        } else {
            String label= current.toString();
            if(current instanceof Task) label = ((Task) current).getName();
            Transition tra = new Transition(label,petri);
            Place plc = new Place(current.toString(),"", petri);
            tra.getTargetPlaces().add(plc);
            getLastPlace(petri).getOutgoingTransitions().add(tra);
        }
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
    public static Place firstPlace(Petri petri){
        List<Place> placesWithIncoming = petri.getTransitions().stream()
                .map(transition -> transition.getTargetPlaces())
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
        return petri.getPlaces().stream().filter(place -> !placesWithIncoming.contains(place)).findFirst().get();
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
