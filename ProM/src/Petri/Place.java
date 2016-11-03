package Petri;

import java.util.ArrayList;
import java.util.List;

/**
 * @(#) Petri.Place.java
 */
public class Place {
	private List<Transition> outgoingTransitions= new ArrayList<>();;
	private List<Transition> incomingTransitions= new ArrayList<>();;
	private String LABEL;
	private int Token = 0;
	private String Type;
	private Petri ownerPetri;

	public Place(String LABEL, String type, Petri ownerPetri) {
		this.LABEL = LABEL;
		Type = type;
		this.ownerPetri = ownerPetri;
		ownerPetri.getPlaces().add(this);
	}


	public void setOutgoingTransitions(List<Transition> outgoingTransitions) {
		this.outgoingTransitions = outgoingTransitions;
	}

	public List<Transition> getIncomingTransitions() {
		return incomingTransitions;
	}

	public void setIncomingTransitions(List<Transition> incomingTransitions) {
		this.incomingTransitions = incomingTransitions;
	}


	public List<Transition> getOutgoingTransitions() {
		return outgoingTransitions;
	}


	public String getLABEL() {
		return LABEL;
	}

	public void setLABEL(String LABEL) {
		this.LABEL = LABEL;
	}

	public int getToken() {
		return Token;
	}

	public void setToken(int token) {
		Token = token;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public Petri getOwnerPetri() {
		return ownerPetri;
	}

	public void setOwnerPetri(Petri ownerPetri) {
		this.ownerPetri = ownerPetri;
	}

	@Override
	public String toString() {
		return "Place{" +
				"Type='" + Type + '\'' +
				", LABEL='" + LABEL + '\'' +
				'}';
	}
}
