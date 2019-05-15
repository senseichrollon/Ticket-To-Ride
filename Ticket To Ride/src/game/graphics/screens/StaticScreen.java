package game.graphics.screens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.IntStream;

import game.entity.Player;
import game.graphics.drawers.CityMapDrawer;
import game.graphics.drawers.ContractCardDrawer;
import game.graphics.engine.GraphicsPanel;
import game.graphics.input.InputManager;
import game.graphics.input.MouseInput;
import game.graphics.util.ImageLoader;
import game.graphics.util.MButton;
import game.main.GameState;

public class StaticScreen extends ScreenManager{

	private GameState game;
	private CityMapDrawer cMapDrawer;
	private ContractCardDrawer contractDrawer;
	private BufferedImage logo;
	private MouseInput input;
	private MButton egButton;
	private MButton spButton;
	private int currPlayer;

	public StaticScreen(MouseInput in) {
		input = in;
		egButton = new MButton("View Points", new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 20), Color.GREEN, Color.orange);
		egButton.setCenter(new Point(100,550));
		egButton.setShape(MButton.ellipse(200,100));
		
		spButton = new MButton("Switch Player", new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 20), Color.GREEN, Color.orange);
		spButton.setCenter(new Point(100, 700));
		spButton.setShape(MButton.ellipse(200,100));
		
		currPlayer = 0;
	}

	@Override
	public void update() {
		updateButton(input.clicked(), input.released(), new Point(input.getX(), input.getY()));
		if (!(contractDrawer.getParent() == GraphicsPanel.getPanel())) {
			GraphicsPanel.getPanel().add(contractDrawer);
		}
		
		if(egButton.isValidRelease()) {
			egButton.setValidRelease(false);
			GraphicsPanel.getPanel().remove(contractDrawer);
			ScreenManager.switchScreen(END);
		}
		if(spButton.isValidRelease())
		{
			spButton.setValidRelease(false);
			currPlayer++;
		}
		contractDrawer.setPlayerContracts(game.getPlayers()[currPlayer%4].getContracts());
		contractDrawer.repaint();
	}
	
	public void drawLeaderBoard(Graphics2D g) {
		Player[] players = game.getPlayers();
		ArrayList<Player> sorted = new ArrayList<>();
		IntStream.range(0, players.length).forEach(i -> sorted.add(players[i]));
//		Collections.sort(sorted, (a,b) -> Integer.compare(b.getPoints(), a.getPoints()));
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 15));
		g.drawString(String.format("%8s   %8s %8s   %14s    %14s","","Points","Trains","Train Cards","Contract Cards"), 20, 80);
		int y = 150;
		for(Player p : sorted) {
			g.setColor(p == players[currPlayer%4]?Color.WHITE:Color.BLACK);
			g.drawString(String.format("%-8s     %4d    %8d      %12d   %21d",p.getName(), p.getPoints(),p.getTrains(),p.getCards().getNumCards(),p.getContracts().size()), 20, y);
			Color c = null;
			switch(p.getTrainColor()) {
				case "blue": {
					c = Color.BLUE;
					break;
				}
				case "purple": {
					c = new Color(148,0,211);
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
			g.setColor(c);
			g.fill(new Ellipse2D.Double(360,y-20,30,30));
			y+= 70;
		}
	}
	@Override
	public void draw(Graphics2D g) {
		if(game == null)
			return;
		
		Color c2 = Color.CYAN.darker().darker();
		GradientPaint gp1 = new GradientPaint(0, 0, Color.CYAN, 0, (600), c2, true);
		Paint p = g.getPaint();
		g.setPaint(gp1);
		g.fillRect(0, 0, 398, 400);
		g.fillRect(0, 400, 398, 680);
		g.fillRect(1625, 0, 500, 1080);
		g.fillRect(398, 720, 1226, 200);
		g.setPaint(p);
		g.drawImage(logo, 10, 0, null);
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(5));
		g.drawLine(0, 390, 398, 390);
		
		cMapDrawer.draw(g);
		drawLeaderBoard(g);
		
		egButton.draw(g);
		spButton.draw(g);
	}
	
	public void setGameState(GameState game)
	{
		this.game = game;
		init();
	}
	
	public void updateButton(boolean mousePressed, boolean mouseReleased, Point mouseLoc) {
		MButton[] buttons = {egButton, spButton};
		if(mousePressed)
			for(MButton b : buttons)
				if(b.checkContains(mouseLoc))
					b.setPressed(true);
		if(mouseReleased)
			for(MButton b : buttons)
				if(b.checkContains(mouseLoc) && b.isPressed())
					b.setValidRelease(true);
		if(!mousePressed && !mouseReleased)
			for(MButton b : buttons)
				if(!b.checkContains(mouseLoc))
					b.setPressed(false);
	}
	
	public void init() {
		logo = ImageLoader.loadImage("resources/menuscreen/logo2.png");
		logo = ImageLoader.resize(logo, logo.getWidth() / 3, logo.getHeight() / 3);
		cMapDrawer = new CityMapDrawer(game.getBoard());
		contractDrawer = new ContractCardDrawer(game.getDeck().getContractCards());
		contractDrawer.setPlayerContracts(game.getPlayers()[game.getCurrentPlayer()].getContracts());
	}
}