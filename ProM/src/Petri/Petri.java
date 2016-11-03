package Petri;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Place> placesWithIncoming = transitions.stream()
                .map(transition -> transition.getTargetPlaces())
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
       return places.stream().filter(place -> !placesWithIncoming.contains(place)).findFirst().get();
    }



}
