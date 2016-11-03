package ut.systems.modelling;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.elements.*;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetImpl;

import java.util.List;

/**
 * Created by arikan on 3.11.16.
 */
public class PetriOutputConverter {
    private PetrinetImpl petrinet = new PetrinetImpl("ResultPetriNet");

    PetriOutputConverter(Petri.Petri petri) {
        Place p1 = petrinet.addPlace(petri.firstPlace().getLABEL());
        traverseStartingFrom(petri.firstPlace(), petrinet, p1);
    }

    PetrinetImpl getResultPetri() {
        return petrinet;
    }
    private void traverseStartingFrom(Petri.Place current, PetrinetImpl petrinet, Place p1){
        current.getOutgoingTransitions().forEach( t ->{
            Transition t1 = petrinet.addTransition(t.getLabel());
            petrinet.addArc(p1, t1, 0);
            t.getTargetPlaces().forEach(place -> {
                Place p2 = petrinet.addPlace(place.getLABEL());
                petrinet.addArc(t1, p2, 0);
                traverseStartingFrom(place, petrinet, p2);
            });
        }) ;
    }
}
