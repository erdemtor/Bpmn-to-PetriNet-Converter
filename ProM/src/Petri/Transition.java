package Petri;

import java.util.ArrayList;
import java.util.List;

/**
 * @(#) Petri.Transition.java
 */
public class Transition
{
    private List<Place> sourcePlaces = new ArrayList<>();
    private List<Place> targetPlaces= new ArrayList<>();;
    private String Label;

    public Transition(String label, Petri ownerPetri) {
        Label = label;
        this.ownerPetri = ownerPetri;
    }

    private Petri ownerPetri;

    public List<Place> getSourcePlaces() {
        return sourcePlaces;
    }

    public void setSourcePlaces(List<Place> sourcePlaces) {
        this.sourcePlaces = sourcePlaces;
    }

    public List<Place> getTargetPlaces() {
        return targetPlaces;
    }

    public void setTargetPlaces(List<Place> targetPlaces) {
        this.targetPlaces = targetPlaces;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public Petri getOwnerPetri() {
        return ownerPetri;
    }

    public void setOwnerPetri(Petri ownerPetri) {
        this.ownerPetri = ownerPetri;
    }
}
