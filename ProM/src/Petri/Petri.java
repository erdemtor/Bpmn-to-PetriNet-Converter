package Petri;

import java.util.List;

/**
 * @(#) Petri.Petri.java
 */
public class Petri
{
	private List<Place> places;
	private List<Transition> transitions;
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

	

	
	
}
