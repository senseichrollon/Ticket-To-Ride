
public class PlayerCardTree 
{
	private CardNode root;
	
	public PlayerCardTree()
	{
		root = null;
	}
	
	public CardNode add(String str, int c)
	{
		CardNode card = new CardNode(str, c);
		root = addHelper(root, card);
		return root;
	}
	
	public CardNode addHelper(CardNode tree, CardNode card)
	{
		if (tree == null)
		{
			tree = card;
			return tree;
		}
		if (tree.getColor().equals(card.getColor()))
		{
			int c = tree.getCount() + card.getCount();
			tree.setCount(c);
		}
		else if (tree.getColor().compareTo(card.getColor()) > 0)
		{
			tree.setLeft(addHelper(tree.getLeft(), card));
		}
		else
		{
			tree.setRight(addHelper(tree.getRight(), card));
		}
		return tree;
	}
	
	public boolean hasEnough(CardNode tree, String color, int cnt)
	{
		if (tree == null)
			return false;
		int c = tree.getColor().compareTo(color);
		if (c == 0)
		{
			if ((tree.getCount() - cnt) > 0)
				return true;
			else
				return false;
		}
		else if (c > 0)
		{
			return hasEnough(tree.getLeft(), color, cnt);
		}
		else
		{
			return hasEnough(tree.getRight(), color, cnt);
		}
	}
	
	public CardNode getCard(CardNode tree, String color)
	{
		if (tree == null)
			return null;
		int c = tree.getColor().compareTo(color);
		if (c == 0)
		{
			return tree;
		}
		else if (c > 0)
		{
			return getCard(tree.getLeft(), color);
		}
		else
		{
			return getCard(tree.getRight(), color);
		}
	}
	
	public CardNode remove(String color, int c)
	{
		CardNode temp = getCard(root, color);
		if (temp == null)
		{
			return null;
		}
		int numLeft = temp.getCount() - c;
		if (numLeft > 0)
		{
			temp.setCount(numLeft);
			return new CardNode(color, c);
		}
		else if (numLeft < 0)
		{
			CardNode wildCard = getCard(root, "wild");
			if (wildCard == null)
				return null;
			else
				
		}
	}
}
