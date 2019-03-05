
public class CardNode
{
	private String color;
	private int count;
	private CardNode left;
	private CardNode right;

	public CardNode() 
	{
		left = null;
		right = null;
	}
	
	public CardNode(String str, int c)
	{
		count = c;
		color = str;
		left = null;
		right = null;
	}

	public CardNode(String value, CardNode left, CardNode right)
	{      
		color = value;      
		this.left = left;      
		this.right = right;   
	} 
	
	public String getColor()
	{
		return color;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public CardNode getLeft()
	{
		return left;
	}
	
	public CardNode getRight()
	{
		return right;
	}
	
	public void setColor(String color)
	{
		this.color = color;
	}
	
	public void setLeft(CardNode left)
	{
		this.left = left;
	}
	
	public void setRight(CardNode right)
	{
		this.right = right;
	}
	
	public void setCount(int c)
	{
		count += c;
	}
}
