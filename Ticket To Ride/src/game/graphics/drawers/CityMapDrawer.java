package game.graphics.drawers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import game.entity.CityMap;
import game.entity.Track;
import game.graphics.engine.GraphicsPanel;
import game.graphics.util.ImageLoader;

public class CityMapDrawer {
	private CityMap map;
	private BufferedImage city;
	private HashMap<Integer, TrackDrawer> tracks;

	public CityMapDrawer(CityMap map) {
		this.map = map;
		city = ImageLoader.loadImage("resources/gameboard/ticket_to_ride_map.jpg");
		city = ImageLoader.resize(city, (int) (city.getWidth() * 1.3), (int) (city.getHeight() * 1.3));
		tracks = new HashMap<>();
		try {
			Scanner in = new Scanner(new File("resources/gamedata/GPathData.ttr"));
			int n = in.nextInt();
			for (int i = 0; i < n; i++) {
				int id = in.nextInt();
				int doubleTrack = in.nextInt();
				int trackLen = in.nextInt();
				System.out.println(id + " " + doubleTrack + " " + trackLen);
				Point2D[][] pts = new Point2D[2][trackLen * 2];
				for(int j = 0; j < doubleTrack; j++) {
					pts[j] = new Point2D[trackLen * 2];
					for(int k = 0; k < trackLen * 2; k+=2) {
						pts[j][k] = new Point2D.Double(in.nextInt(), in.nextInt());
						pts[j][k+1] = new Point2D.Double(in.nextInt(), in.nextInt());
					}
				}
				tracks.put(id, new TrackDrawer(pts[0],pts[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawImage(city, 400, 0, null);
		g.drawRect(400, 0, city.getWidth(), city.getHeight());
		drawTracks(g);
		g.setStroke(new BasicStroke(5));
		g.setColor(Color.BLACK);
		g.drawLine(400, city.getHeight(), 400, GraphicsPanel.WIDTH);
		g.drawLine(city.getWidth() + 400, city.getHeight(), city.getWidth() + 400, GraphicsPanel.WIDTH);
	}

	public void drawTracks(Graphics2D g) {
		for (int i : tracks.keySet()) {

			Track track = CityMap.allTracks.get(i);
			if (track.getPlayerColor1() != null) {
				tracks.get(i).draw(g, stringToColor(track.getPlayerColor1()), false);
//				tracks.get(i).draw(g, null, false);
			}

			if (track.getPlayerColor2() != null) {
				tracks.get(i).draw(g, stringToColor(track.getPlayerColor2()), true);
//				tracks.get(i).draw(g, null, true);
			}
		}
	}

	public LinkedHashMap<Track, TrackDrawer> getDrawMap() {
		LinkedHashMap<Track, TrackDrawer> drawMap = new LinkedHashMap<>();
		List<ArrayList<Track>> fullMap = map.getFullMap();
		for (ArrayList<Track> tt : fullMap) {
			for (Track track : tt) {
				drawMap.put(track, tracks.get(track.getID()));
			}
		}
		return drawMap;
	}

	public class TrackDrawer {
		private Point2D[] firstTrack, secondTrack;
		private Color c;

		public TrackDrawer(Point2D[] firstTrack, Point2D[] secondTrack) {
			this.firstTrack = firstTrack;
			this.secondTrack = secondTrack;
			c = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
		}

		public void draw(Graphics2D g, Color c, boolean second) {
			Point2D[] pts = (second)?secondTrack:firstTrack;
			g.setColor(c);
			g.setStroke(new BasicStroke(9));
			for(int i = 0; i < pts.length; i+=2) {
				int x1 = (int)pts[i].getX();
				int y1 = (int)pts[i].getY();
				int x2 = (int)pts[i+1].getX();
				int y2 = (int)pts[i+1].getY();
				g.drawLine(x1, y1, x2, y2);
			}
		}

		public Point2D.Double getClick(int num) {
			Point2D[] pts = (num == 1)?secondTrack:firstTrack;
			Point2D one = pts[pts.length/2-1];
			Point2D two = pts[pts.length/2];
			return new Point2D.Double((one.getX() + two.getX())/2,(one.getY() + two.getY())/2);
		}
	}

	public Color stringToColor(String color) {
		Color c = null;
		switch (color) {
			case "blue": {
				c = Color.BLUE;
				break;
			}
			case "purple": {
				c = new Color(148, 0, 211);
				break;
			}
			case "green": {
				c = Color.GREEN.darker();
				break;
			}
			case "yellow": {
				c = Color.YELLOW;
				break;
			}
		}
		return c;
	}

}
