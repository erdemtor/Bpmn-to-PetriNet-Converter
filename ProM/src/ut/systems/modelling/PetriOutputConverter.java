package ut.systems.modelling;
import Converter.PetriUtils;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.elements.*;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetImpl;

import java.util.HashMap;
import java.util.List;

/**
 * Created by arikan on 3.11.16.
 */
class PetriOutputConverter {
    private PetrinetImpl petrinet = new PetrinetImpl("ResultPetriNet");
    private HashMap<Petri.Place, Place> places = new HashMap<>();
    private HashMap<Petri.Transition, Transition> transitions = new HashMap<>();
    PetriOutputConverter(Petri.Petri petri) {
        Place p1 = petrinet.addPlace(PetriUtils.firstPlace(petri).getLABEL());
        places.put(PetriUtils.firstPlace(petri), p1);
        traverseStartingFrom(PetriUtils.firstPlace(petri), petrinet, p1);
    }

    PetrinetImpl getResultPetri() {
        return petrinet;
    }
    private void traverseStartingFrom(Petri.Place current, PetrinetImpl petrinet, Place p1){
        current.getOutgoingTransitions().forEach( t ->{
            Transition t1;
            if (transitions.containsKey(t)) {
                t1 = transitions.get(t);
            } else {
                t1 = petrinet.addTransition(t.getLabel());
                transitions.put(t, t1);
            }
            petrinet.addArc(p1, t1, 0);
            t.getTargetPlaces().forEach(p -> {
                Place p2;
                if (places.containsKey(p)) {
                    p2 = places.get(p);
                } else {
                    p2 = petrinet.addPlace(p.getLABEL());
                    places.put(p, p2);
                }
                petrinet.addArc(t1, p2, 0);
                traverseStartingFrom(p, petrinet, p2);
            });
        }) ;
    }
}
