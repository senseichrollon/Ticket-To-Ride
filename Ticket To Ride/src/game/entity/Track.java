package game.entity;

public class Track implements Comparable<Track> {
	private int cityOne;
	private int cityTwo;
	private String trackColor1;
	private String trackColor2;
	private String playerColor1;
	private String playerColor2;
	private int length;
	private int id;

	public Track(int id, int cityOne, int cityTwo, int length, String color) {
		this(id, cityOne, cityTwo, length, color, null);
	}

	public Track(int id, int cityOne, int cityTwo, int length, String trackColor1, String trackColor2) {
		this.id = id;
		this.cityOne = cityOne;
		this.cityTwo = cityTwo;
		this.trackColor1 = trackColor1;
		this.trackColor2 = trackColor2;
		this.length = length;
		playerColor1 = null;
		playerColor2 = null;
	}

	public int getCityOne() {
		return cityOne;
	}

	public void setCityOne(int cityOne) {
		this.cityOne = cityOne;
	}

	public int getCityTwo() {
		return cityTwo;
	}

	public void setCityTwo(int cityTwo) {
		this.cityTwo = cityTwo;
	}

	public int getOtherCity(int oth) {
		if (oth == cityOne)
			return cityTwo;
		if (oth == cityTwo)
			return cityOne;
		return -1;
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

	public String getPlayerColor2() {
		return playerColor2;
	}

	public boolean containsTrackCol(String col) {
		if (trackColor1.equals(col) || (trackColor2 != null && trackColor2.equals(col)))
			return true;
		return false;
	}

	public boolean containsPlayerCol(String col) {
		if ((playerColor1 != null && playerColor1.equals(col)) || (playerColor2 != null && trackColor2.equals(col)))
			return true;
		return false;
	}

	public boolean isFilled() {
		if (trackColor2 == null) {
			if (playerColor1 != null)
				return true;
		} else {
			if (playerColor1 != null && playerColor2 != null)
				return true;
		}
		return false;
	}

	public boolean occupyTrack(String pc, String colChoice, int side) {
		if (trackColor2 == null) {
			if (playerColor1 == null) {
				playerColor1 = pc;
				return true;
			}
		} 
		else if(trackColor1.equals("gray") && trackColor2.equals("gray")){
			if(containsPlayerCol(pc))
				return false;
			if(side == 1 && playerColor1 == null){
				playerColor1 = pc;
				return true;
			}
			else if(side == 2 && playerColor2 == null){
				playerColor2 = pc;
				return true;
			}
			return false;
		}
		else {
			if (containsTrackCol(colChoice)) {
				if (trackColor1.equals(colChoice) && playerColor1 == null
						&& (playerColor2 == null || !playerColor2.equals(pc))) {
					playerColor1 = pc;
					return true;
				} else if (trackColor2.equals(colChoice) && playerColor2 == null
						&& (playerColor1 == null || !playerColor1.equals(pc))) {
					playerColor2 = pc;
					return true;
				}
			}
		}
		return false;
	}

	public boolean isDoubleTrack() {
		if (trackColor2 != null)
			return true;
		return false;
	}

	public int getID() {
		return id;
	}

	public boolean equals(Track other) {
		if (other.getID() == getID())
			return true;
		return false;
	}

	public int compareTo(Track oth) {
		return getID() - oth.getID();
	}

	public int getLength() {
		return length;
	}

	public String toString() {
		return id + " " + cityOne + " " + cityTwo + " " + trackColor1 + " " + trackColor2 + " " + length;
	}
}
