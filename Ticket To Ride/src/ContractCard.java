

public class ContractCard {
	private String cityOne, cityTwo;
	private int points;

	public ContractCard(String c1, String c2, int p) {
		cityOne = c1;
		cityTwo = c2;
		points = p;
	}

	public String getCityOne() {
		return cityOne;
	}

	public String getCityTwo() {
		return cityTwo;
	}

	public int getPoints() {
		return points;
	}

	public void setCityOne(String c1) {
		cityOne = c1;
	}

	public void setCityTwo(String c2) {
		cityTwo = c2;
	}

	public void setPoints(int p) {
		points = p;
	}

	public String toString() {
		String str = cityOne + " - " + cityTwo;
		return str;
	}
}
