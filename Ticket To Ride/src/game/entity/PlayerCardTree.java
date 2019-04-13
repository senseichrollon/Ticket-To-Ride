package game.entity;

import java.util.ArrayList;

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

	public boolean hasEnough(CardNode tree, String color, int cnt) {
		if (tree == null)
			return false;
		int c = tree.getColor().compareTo(color);
		if (c == 0) {
			if ((tree.getCount() - cnt) > 0)
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

	public ArrayList<CardNode> remove(String color, int c) {
		ArrayList<CardNode> cards = new ArrayList<CardNode>();
		CardNode wildCard;
		CardNode temp = getCard(root, color);
		if (temp == null) {
			return cards;
		}
		int numLeft = temp.getCount() - c;
		if (numLeft > 0) {
			temp.setCount(numLeft);
			cards.add(new CardNode(color, c))
			return cards;
		} else if (numLeft < 0) {
			wildCard = getCard(root, "wild");
			if (wildCard == null)
				return cards;
			int wildCardLeft = wildCard.getCount() - (-1 * numLeft);
			if (wildCardLeft < 0) {
				return cards;
			} else if (wildCardLeft > 0) {
				wildCard.setCount(wildCardLeft);
				cards.add(new CardNode("wild", numLeft));
				numLeft = 0;
			}
		}
		if (numLeft == 0) {
			cards.add(new CardNode(color, c));
			temp.setCount(0);
			return cards;
		}
		return cards;
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
