package game.graphics.screens;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.TreeSet;

import game.entity.Deck;
import game.entity.Player;
import game.graphics.drawers.CityMapDrawer;
import game.graphics.drawers.ContractCardDrawer;
import game.graphics.drawers.HandDrawer;
import game.graphics.engine.GraphicsPanel;
import game.graphics.util.ImageLoader;
import game.main.GameState;

public class GameScreen extends ScreenManager {
	
	private GameState game;
	private CityMapDrawer cMapDrawer;
	private ContractCardDrawer contractDrawer;
	private HandDrawer handDrawer;
	
	private Thread gameThread;
	private BufferedImage logo, govContract, trainContract;
	private LinkedHashMap<String, BufferedImage> cards;
	
	
	public GameScreen() {
		try {
			game = new GameState();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cMapDrawer = new CityMapDrawer(game.getBoard());
		logo = ImageLoader.loadImage("resources/menuscreen/logo2.png");
		logo = ImageLoader.resize(logo, logo.getWidth()/3, logo.getHeight()/3);
		govContract = ImageLoader.loadImage("resources/contractcard/ticket_card_back.jpg");
		trainContract = ImageLoader.loadImage("resources/traincards/backtrain.png");
		contractDrawer = new ContractCardDrawer(game.getDeck().getContractCards());
		
		cards = new LinkedHashMap<>();
		
		String[] colors = {"black","blue", "green", "orange", "purple", "red", "white", "yellow","wild"};
		for(String color : colors) {
			if(color.equals("wild"))
				cards.put(color, ImageLoader.loadImage("resources/traincards/" + color + ".png"));
			else
				cards.put(color, ImageLoader.loadImage("resources/traincards/" + color +  ".jpg"));
		}
	
		handDrawer = new HandDrawer(cards);
		handDrawer.setTree(game.getPlayers()[game.getCurrentPlayer()].getCards());
	}
	
	
	@Override
	public void update() {
		if(!(contractDrawer.getParent() == GraphicsPanel.getPanel())) {
			GraphicsPanel.getPanel().add(contractDrawer);
		}
		handDrawer.setTree(game.getPlayers()[game.getCurrentPlayer()].getCards());
		contractDrawer.setPlayerContracts(game.getPlayers()[game.getCurrentPlayer()].getContracts());
	}
	 
	public void drawPiles(Graphics2D g) {
		g.fillRect(1625, 0, 500, 1080);
		g.drawImage(govContract, 1700, 900, (govContract.getWidth() * 2)/3,(govContract.getHeight()*2)/3,null);
		AffineTransform at = new AffineTransform();
		at.setToTranslation(1900, 760);
		at.rotate(Math.PI/2);
		at.scale(0.25, 0.25);
		g.drawImage(trainContract,at,null);
		
		Deck deck = game.getDeck();
		int y = 20;
		
		for(String s : deck.getUpCards()) {
			g.drawImage(cards.get(s),1700,y,(cards.get(s).getWidth() * 8)/10,(cards.get(s).getHeight() * 8)/10,null);
			y += 130;
		}
	}
	
	private void drawLeaderBoard(Graphics2D g) {
		Player[] players = game.getPlayers();
		TreeSet<Player> order = new TreeSet<>((a,b) -> (Integer.compare(b.getPoints(), a.getPoints())));
		Arrays.stream(players).forEach(n -> order.add(n));
		
		int num = 1;
		for(Player )
	}

	@Override
	public void draw(Graphics2D g) {
		handDrawer.draw(g);
		cMapDrawer.draw(g);
		Color c2 = Color.RED.darker();
		GradientPaint gp1 = new GradientPaint(0, 0, Color.orange, 0, (600), c2, true);
		Paint p = g.getPaint();
		g.setPaint(gp1);
		g.fillRect(0, 0, 398, 400);
		g.setPaint(p);
		g.drawImage(logo,10,0,null);
		
		
		 c2 = Color.YELLOW;
		 gp1 = new GradientPaint(0, 0,Color.ORANGE.darker(), 0, (600), c2, true);
		 g.setPaint(gp1);
		g.fillRect(0, 400, 398, 680);
		drawPiles(g);
//		g.setColor(Color.BLACK.darker().darker());
//		g.setFont(new Font("Serif", Font.BOLD+Font.ITALIC, 36));
//		g.drawString("Ticket to Ride", 0, 50);
	}

}
