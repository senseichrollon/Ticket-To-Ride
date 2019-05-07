package game.ai;

import java.util.ArrayList;

public class Node implements Comparable<Node> {
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

	public ArrayList<Node> getChildren() {
		return children;
	}
	

	@Override
	public int compareTo(Node m) {
		return 0;
//		return Double.compare(UCT(), m.UCT());
	}
}