package game.entity;

public class Track {
	private String cityOne;
	private String cityTwo;
	private String trackColor1;
	private String trackColor2;
	private String playerColor1;
	private String playerColor2;
	private int id;

	public Track(int id, String cityOne, String cityTwo, String color) {
		this(id, cityOne, cityTwo, color, null);
	}
	
	public Track(int id, String cityOne, String cityTwo, String trackColor1, String trackColor2) {
		this.id = id;
		this.cityOne = cityOne;
		this.cityTwo = cityTwo;
		this.trackColor1 = trackColor1;
		this.trackColor2 = trackColor2;
		playerColor1 = null;
		playerColor2 = null;
	}

	public String getCityOne() {
		return cityOne;
	}

	public void setCityOne(String cityOne) {
		this.cityOne = cityOne;
	}

	public String getCityTwo() {
		return cityTwo;
	}

	public void setCityTwo(String cityTwo) {
		this.cityTwo = cityTwo;
	}

	public String getOtherCity(String oth) {
		if (oth.equals(cityOne))
			return cityTwo;
		if (oth.equals(cityTwo))
			return cityOne;
		return "";
	}

	public String getTrackColor1() {
		return trackColor1;
	}

	public void setTrackColor1(String trackColor1) {
		this.trackColor1 = trackColor1;
	}

	public String getTrackColor2() {
		return trackColor2;
	}

	public void setTrackColor2(String trackColor2) {
		this.trackColor2 = trackColor2;
	}

	public String getPlayerColor1() {
		return playerColor1;
	}

	public void setPlayerColor1(String playerColor1) {
		this.playerColor1 = playerColor1;
	}

	public String getPlayerColor2() {
		return playerColor2;
	}
	
	public boolean contains(String col)
	{
		if(trackColor1.equals(col) || (trackColor2 != null && trackColor2.equals(col)))
			return true;
		return false;
	}
	
	public boolean isFilled()
	{
		if(trackColor2 == null)
		{
			if(playerColor1 != null)
				return true;
		}
		else
		{
			if(playerColor1 != null && playerColor2 != null)
				return true;
		}
		return false;
	}
	
	public boolean occupyTrack(String pc, String colChoice)
	{
		if(trackColor2 == null)
		{
			if(playerColor1 == null)
			{
				playerColor1 = pc;
				return true;
			}
		}
		else
		{
			if(contains(colChoice))
			{
				if(trackColor1.equals(colChoice) && playerColor1 == null 
				&& (playerColor2 == null || !playerColor2.equals(pc))) 
				{
					playerColor1 = pc;
					return true;
				}
				else if(trackColor2.equals(colChoice) && playerColor2 == null 
				&& (playerColor1 == null || !playerColor1.equals(pc))) 
				{
					playerColor2 = pc;
					return true;
				}
			}
		}
		return false;
	}
	
	public int getID()
	{
		return id;
	}
	
	public boolean equals(Track other)
	{
		if(other.getID() == getID())
			return true;
		return false;
	}
}
