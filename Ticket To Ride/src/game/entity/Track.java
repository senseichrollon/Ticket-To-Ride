package game.entity;

public class Track {
	private String cityOne;
	private String cityTwo;
	private String color1;
	private String color2;
	private int id;

	public Track(int id, String cityOne, String cityTwo, String color) {
		this(id, cityOne, cityTwo, color, null);
	}
	
	public Track(int id, String cityOne, String cityTwo, String color1, String color2) {
		this.id = id;
		this.cityOne = cityOne;
		this.cityTwo = cityTwo;
		this.color1 = color1;
		this.color2 = color2;
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

	public String getColorOne() {
		return color1;
	}

	public void setColorOne(String color) {
		this.color1 = color;
	}
	
	public String getColorTwo() {
		return color2;
	}

	public void setColorTwo(String color) {
		this.color2 = color;
	}

}
