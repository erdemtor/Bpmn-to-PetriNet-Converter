package Petri;

import java.util.List;

/**
 * @(#) Petri.Transition.java
 */
public class Transition
{
    private List<Place> sourcePlaces;
    private List<Place> targetPlaces;
    private String Label;
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
