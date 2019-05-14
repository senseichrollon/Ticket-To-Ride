package game.entity;

import java.util.HashMap;

public class PlayerCardTree {
	private CardNode root;

	public PlayerCardTree() {
		root = null;
	}

	public CardNode add(String str, int c) {
		CardNode card = new CardNode(str, c);
		root = addHelper(root, card);
		return root;
	}

	public CardNode addHelper(CardNode tree, CardNode card) {
		if (tree == null) {
			tree = card;
			return tree;
		}
		if (tree.getColor().equals(card.getColor())) {
			int c = tree.getCount() + card.getCount();
			tree.setCount(c);
		} else if (tree.getColor().compareTo(card.getColor()) > 0) {
			tree.setLeft(addHelper(tree.getLeft(), card));
		} else {
			tree.setRight(addHelper(tree.getRight(), card));
		}
		return tree;
	}
	
	public boolean hasEnough(String color, int cnt) {
		if(color.equals("gray")) {
			String[] colors = {"black", "blue", "green", "orange", "purple", "red", "white", "yellow"};
			for(String c : colors) {
				if(hasEnough(c,cnt)) {
					return true;
				}
			}
			return false;
		}
		CardNode node = getCard(color);
		if(node.getCount() >= cnt)
			return true;
		else
			return hasEnough(root,"wild",cnt-node.getCount());
	}
	
	private boolean hasEnough(CardNode tree, String color, int cnt) {
		if (tree == null)
			return false;
		int c = tree.getColor().compareTo(color);
		if (c == 0) {
			if ((tree.getCount() - cnt) >= 0)
				return true;
			else
				return false;
		} else if (c > 0) {
			return hasEnough(tree.getLeft(), color, cnt);
		} else {
			return hasEnough(tree.getRight(), color, cnt);
		}
	}

	public CardNode getCard(String color) {
		return getCard(root, color);
	}

	private CardNode getCard(CardNode tree, String color) {
		if (tree == null)
			return null;
		int c = tree.getColor().compareTo(color);
		if (c == 0) {
			return tree;
		} else if (c > 0) {
			return getCard(tree.getLeft(), color);
		} else {
			return getCard(tree.getRight(), color);
		}
	}

	
	public void remove(String color, int cnt, int wildCnt) {
		CardNode card = getCard(color);
		if(card != null)
			card.setCount(card.getCount()-cnt);
		CardNode wild = getCard("wild");
		wild.setCount(wild.getCount()-wildCnt);
	}
	
	public int getNumCards() {
		String[] colors = {"black", "blue", "green", "orange", "purple", "red", "white", "yellow","wild"};
		int total = 0;
		for(String s : colors) {
			total += getCard(s).getCount();
		}
		return total;
	}
	
	public CardNode getMax() {
		String[] colors = {"black", "blue", "green", "orange", "purple", "red", "white", "yellow"};
		CardNode ret = null;
		int max = Integer.MIN_VALUE;
		for(String s : colors) {
			CardNode node = getCard(s);
			if(node.getCount() > max) {
				ret = node;
				max = node.getCount();
			}
		}
		return ret;
	}

	public String toString() {
		return inOrder(root);
	}

	public String inOrder(CardNode card) {
		String str = "";
		if (card != null) {
			str += inOrder(card.getLeft());
			str += card + "\n";
			str += inOrder(card.getRight());
		}
		return str;
	}
}
