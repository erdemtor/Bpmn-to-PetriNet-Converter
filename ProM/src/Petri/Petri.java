package Petri;

import java.util.ArrayList;
import java.util.List;

/**
 * @(#) Petri.Petri.java
 */
public class Petri
{
    private List<Place> places = new ArrayList<>();
    private List<Transition> transitions = new ArrayList<>();
    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public List<Place> getPlaces( ) {
        return places;
    }

    public void setPlaces(List<Place> places ) {
        this.places = places;
    }

    public List<Transition> getTransitions( ) {
        return transitions;
    }


    public Place firstPlace(){
        return places.stream()
                .filter(p -> p.getType().equals("start"))
                .findFirst()
                .get();
    }


}
