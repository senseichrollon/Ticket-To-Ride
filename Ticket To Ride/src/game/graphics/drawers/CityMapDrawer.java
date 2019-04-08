package game.graphics.drawers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;

import game.entity.CityMap;
import game.graphics.util.ImageLoader;

public class CityMapDrawer {
	private CityMap map;
	private BufferedImage city;
	
	public CityMapDrawer(CityMap map) {
		this.map = map;
		city = ImageLoader.loadImage("resources/gameboard/ticket_to_ride_map.jpg");
		city = ImageLoader.resize(city, (int)(city.getWidth() * 1.3), (int)(city.getHeight() * 1.3));
	}
	
	
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(5));
		g.drawImage(city, 300, 0, null);
		g.drawRect(300, 0, city.getWidth(), city.getHeight());
		

	}
	
}
