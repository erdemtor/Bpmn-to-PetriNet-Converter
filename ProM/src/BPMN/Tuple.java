package BPMN;
import Petri.*;
/**
 * @(#) BPMN.Tuple.java
 */
public class Tuple
{
	private Petri petri;
	private Node current;

	public Petri getPetri( ) {
		return petri;
	}

	public void setPetri( Petri petri ) {
		this.petri = petri;
	}

	public Node getCurrent( ) {
		return current;
	}

	public void setCurrent( Node current ) {
		this.current = current;
	}

	public Tuple(Petri petri, Node current) {
		this.petri = petri;
		this.current = current;
	}
}
