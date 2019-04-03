package game.graphics.screens;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;

import game.graphics.drawers.CityMapDrawer;
import game.graphics.drawers.ContractCardDrawer;
import game.graphics.drawers.HandDrawer;
import game.graphics.engine.GraphicsPanel;
import game.graphics.util.ImageLoader;

public class GameScreen extends ScreenManager {
	
	private CityMapDrawer cMapDrawer;
	private ContractCardDrawer contractDrawer;
	private HandDrawer handDrawer;
	
	private Thread gameThread;
	private BufferedImage logo, govContract, trainContract;
	
	
	public GameScreen() {
		cMapDrawer = new CityMapDrawer();
		logo = ImageLoader.loadImage("resources/menuscreen/logo2.png");
		logo = ImageLoader.resize(logo, logo.getWidth()/3, logo.getHeight()/3);
	//	govContract = ImageLoader.loadImage(path)
		contractDrawer = new ContractCardDrawer();
		handDrawer = new HandDrawer();
	}
	
	
	@Override
	public void update() {
		if(!(contractDrawer.getParent() == GraphicsPanel.getPanel())) {
			GraphicsPanel.getPanel().add(contractDrawer);
		} 
	}
	 
	public void drawPiles(Graphics2D g) {
		g.fillRect(1525, 0, 500, 1080);
		
		
	}

	@Override
	public void draw(Graphics2D g) {
		handDrawer.draw(g);
		cMapDrawer.draw(g);
		Color c2 = Color.RED.darker();
		GradientPaint gp1 = new GradientPaint(0, 0, Color.orange, 0, (600), c2, true);
		Paint p = g.getPaint();
		g.setPaint(gp1);
		g.fillRect(0, 0, 298, 600);
		g.setPaint(p);
		g.drawImage(logo,10,0,null);
		
		
		 c2 = Color.YELLOW;
		 gp1 = new GradientPaint(0, 0,Color.ORANGE.darker(), 0, (600), c2, true);
		 g.setPaint(gp1);
		g.fillRect(0, 600, 298, 480);
		drawPiles(g);
//		g.setColor(Color.BLACK.darker().darker());
//		g.setFont(new Font("Serif", Font.BOLD+Font.ITALIC, 36));
//		g.drawString("Ticket to Ride", 0, 50);
	}

}
