package game.ai;

import java.util.ArrayList;

public class Node {
	private State state;
	private Node parent;
	private ArrayList<Node> children;
	
	
	public Node(State state) {
		this.setState(state);
	}


	public State getState() {
		return state;
	}


	public void setState(State state) {
		this.state = state;
	}


	public Node getParent() {
		return parent;
	}


	public void setParent(Node parent) {
		this.parent = parent;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}
}
