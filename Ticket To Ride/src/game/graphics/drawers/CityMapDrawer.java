package game.graphics.drawers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
				in.next();
				in.next();
				int track = in.nextInt();
				boolean doubleTrack = in.nextBoolean();
				in.next();
				String color = in.next().toLowerCase();
				Point2D[] pts = new Point2D.Double[6];
				for (int j = 0; j < 3; j++) {
					pts[j] = new Point2D.Double((int) in.nextDouble(), (int) in.nextDouble());
				}
				if (doubleTrack) {
					String color2 = in.next();
					for (int j = 3; j < 6; j++) {
						pts[j] = new Point2D.Double(in.nextDouble(), in.nextDouble());
					}
				}

				TrackDrawer drawer = new TrackDrawer(pts, doubleTrack);
				tracks.put(id, drawer);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(5));
		g.drawImage(city, 400, 0, null);
		g.drawRect(400, 0, city.getWidth(), city.getHeight());
		drawTracks(g);
		g.drawLine(400, city.getHeight(), 400, GraphicsPanel.WIDTH);
		g.drawLine(city.getWidth() + 400, city.getHeight(), city.getWidth() + 400, GraphicsPanel.WIDTH);
	}

	public void drawTracks(Graphics2D g) {
		for (int i : tracks.keySet()) {
			Track track = CityMap.allTracks.get(i);
			if (track.getPlayerColor1() != null) {
				tracks.get(i).draw(g, stringToColor(track.getPlayerColor1()), false);
			}

			if (track.getPlayerColor2() != null) {
				tracks.get(i).draw(g, stringToColor(track.getPlayerColor2()), true);
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
		private boolean doubleTrack;
		private Point2D[] points;
		private Color c;

		public TrackDrawer(Point2D[] points, boolean doubleTrack) {
			this.doubleTrack = doubleTrack;
			this.points = doubleTrack ? points : Arrays.copyOfRange(points, 0, 3);
			c = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));

		}

		public void draw(Graphics2D g, Color c, boolean second) {
			g.setColor(c);
			AffineTransform at = new AffineTransform();
			at.setToTranslation(340, -55);
			at.scale(1.3, 1.3);
			Path2D path = new Path2D.Double();
			g.setStroke(new BasicStroke(10, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, new float[] { 43, 4 },
					22));
			if (!second) {
				path.moveTo(points[0].getX(), points[0].getY());
				path.quadTo(points[1].getX(), points[1].getY(), points[2].getX(), points[2].getY());

				path.transform(at);
				g.draw(path);
			} else {
				path.moveTo(points[3].getX(), points[3].getY());
				path.quadTo(points[4].getX(), points[4].getY(), points[5].getX(), points[5].getY());
				path.transform(at);
				g.draw(path);
			}
		}

		public Point2D.Double getClick(int num) {
			if (num == 0)
				return new Point2D.Double(((points[1].getX() * 1.3) + 340), ((points[1].getY() * 1.3) - 55));
			return new Point2D.Double(((points[4].getX() * 1.3) + 340), ((points[4].getY() * 1.3) - 55));

		}

		@Override
		public String toString() {
			return "TrackDrawer [doubleTrack=" + doubleTrack + ", points=" + Arrays.toString(points) + ", c=" + c + "]";
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
