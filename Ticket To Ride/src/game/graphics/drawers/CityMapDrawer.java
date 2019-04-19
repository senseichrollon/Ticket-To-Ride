package game.graphics.drawers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import game.entity.CityMap;
import game.graphics.util.ImageLoader;

public class CityMapDrawer {
	private CityMap map;
	private BufferedImage city;
	private HashMap<Integer, TrackDrawer> tracks;
	
	public CityMapDrawer(CityMap map) {
		this.map = map;
		city = ImageLoader.loadImage("resources/gameboard/ticket_to_ride_map.jpg");
		city = ImageLoader.resize(city, (int)(city.getWidth() * 1.3), (int)(city.getHeight() * 1.3));
		tracks = new HashMap<>();
		try {
			Scanner in = new Scanner(new File("resources/gamedata/GPathData.ttr"));
			int n = in.nextInt();
			for(int i = 0; i < n; i++) {
				int id = in.nextInt();
				in.next(); in.next();
				int track = in.nextInt();
				boolean doubleTrack = in.nextBoolean();
				in.next();
				String color = in.next().toLowerCase();
				Point[] pts = new Point[6];
				for(int j = 0; j < 3; j++) {
					pts[j] = new Point((int)in.nextDouble(), (int)in.nextDouble());
				}
				if(doubleTrack) {
					String color2 = in.next();
					for(int j = 3; j < 6; j++) {
						pts[j] = new Point((int)in.nextDouble(), (int)in.nextDouble());
					}
				}
				
				TrackDrawer drawer = new TrackDrawer(pts,doubleTrack);
				tracks.put(id,drawer);
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(5));
		g.drawImage(city, 400, 0, null);
		g.drawRect(400, 0, city.getWidth(), city.getHeight());
		drawTracks(g);
	}
	
	
	public void drawTracks(Graphics2D g) {
		for(int i : tracks.keySet()) {
			tracks.get(i).draw(g, Color.CYAN);
		}
	}
	
	class TrackDrawer {
		private boolean doubleTrack;
		private Point[] points;
		
		public TrackDrawer(Point[] points, boolean doubleTrack) {
			this.doubleTrack = doubleTrack;
			this.points = doubleTrack?points:Arrays.copyOfRange(points, 0,3);

		}
		public void draw(Graphics2D g, Color c) {
			g.setColor(c);
			AffineTransform at = new AffineTransform();
			at.setToTranslation(340, -55);
			at.scale(1.3, 1.3);
			Path2D path = new Path2D.Double();
			path.moveTo(points[0].getX(), points[0].getY());
			path.quadTo(points[1].getX(), points[1].getY(), points[2].getX(), points[2].getY());
			g.setStroke(new BasicStroke(20, BasicStroke.CAP_BUTT, 
					BasicStroke.JOIN_BEVEL, 0f, 
					new float[]{50, 6}, 
					22));
			path.transform(at);
			g.draw(path);
			
			if(doubleTrack) {
				path = new Path2D.Double();
				path.moveTo(points[3].getX(), points[3].getY());
				path.quadTo(points[4].getX(), points[4].getY(), points[5].getX(), points[5].getY());
				path.transform(at);
				g.draw(path);
			}
		}
	}
	
}
