
public class Track 
{
	private String cityOne;
	private String cityTwo;
	private String color;
	
	public Track(String cityOne, String cityTwo, String color) 
	{
		super();
		this.cityOne = cityOne;
		this.cityTwo = cityTwo;
		this.color = color;
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
	
	public String getOtherCity(String oth)
	{
		if(oth.equals(cityOne))
			return cityTwo;
		if(oth.equals(cityTwo))
			return cityOne;
		return "";
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	
	
}
