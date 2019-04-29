package game.graphics.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.IntStream;

import game.entity.ContractCard;
import game.entity.Deck;
import game.entity.Player;
import game.entity.Track;
import game.graphics.animation.AnimationManager;
import game.graphics.drawers.CityMapDrawer;
import game.graphics.drawers.ContractCardDrawer;
import game.graphics.drawers.HandDrawer;
import game.graphics.drawers.TrainBackGround;
import game.graphics.engine.GraphicsPanel;
import game.graphics.input.InputManager;
import game.graphics.input.MouseInput;
import game.graphics.util.ImageLoader;
import game.main.GameState;

public class GameScreen extends ScreenManager implements Runnable {

	private GameState game;
	private CityMapDrawer cMapDrawer;
	private ContractCardDrawer contractDrawer;
	private HandDrawer handDrawer;

	private Thread gameThread;
	private BufferedImage logo, govContract, trainContract;
	private  LinkedHashMap<String, BufferedImage> cards;

	private InputManager input;
	private boolean init;

	public GameScreen(MouseInput in) {
		input = new InputManager(in);
		logo = ImageLoader.loadImage("resources/menuscreen/logo2.png");
		logo = ImageLoader.resize(logo, logo.getWidth() / 3, logo.getHeight() / 3);
		govContract = ImageLoader.loadImage("resources/contractcard/ticket_card_back.jpg");
		trainContract = ImageLoader.loadImage("resources/traincards/backtrain.png");
		AnimationManager.init();
	}
	
	public void startGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void initGame() {
		init = true;
		try {
			game = new GameState();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cMapDrawer = new CityMapDrawer(game.getBoard());
		contractDrawer = new ContractCardDrawer(game.getDeck().getContractCards());

		cards = new LinkedHashMap<>();

		String[] colors = { "black", "blue", "green", "orange", "purple", "red", "white", "yellow", "wild" };
		for (String color : colors) {
			if (color.equals("wild"))
				cards.put(color, ImageLoader.loadImage("resources/traincards/" + color + ".png"));
			else
				cards.put(color, ImageLoader.loadImage("resources/traincards/" + color + ".jpg"));

		}

		handDrawer = new HandDrawer(cards);
		handDrawer.setTree(game.getPlayers()[game.getCurrentPlayer()].getCards());
		init = false;
	}
	@Override
	public void run() {
		initGame();
		for(int i = 0; i < game.getPlayers().length; i++) {
			requestGovContract();
			input.reset();
			try {Thread.sleep(1000);} catch (InterruptedException e) {	}
			game.updatePlayer();

		}
		while (game.hasWinner() <= 4) {
			int num = input.requestTypeOfTurn(game);
			input.reset();
			switch (num) {
				case 1: {
					Rectangle2D.Double[] coords = new Rectangle2D.Double[6];
					int y = 20;
					String[] upTrains = game.getDeck().getUpCards();
					for(int i = 0; i < 5; i++) {
						BufferedImage img = cards.get(upTrains[i]);
						coords[i] = new Rectangle2D.Double(1700,y,(img.getWidth()*8)/10,(img.getHeight()*8)/10);
						y+= 130;
					}
					coords[5] = new Rectangle2D.Double(1900- trainContract.getHeight()/4,760,(trainContract.getHeight())/4,(trainContract.getWidth())/4);
					while(game.getNumCardsDrawn() < 2) {
						int index = input.requestTrainCardSelection(coords,game.getNumCardsDrawn(),upTrains);
						if(index < 5)
							game.drawFaceUpCard(index);
						else
							game.drawFaceDownCard();
						input.reset();
					}

					break;
				}
				case 2: {
					int id = input.requestTrack(cMapDrawer.getDrawMap(), game.getPlacableTracks());
					input.reset();
					Track track = game.getBoard().getTrack(id > 100? id-1000:id);
					HashMap<String,Integer> cards = input.requestCards(track, id>100, handDrawer.getCards(),game.getPlayers()[game.getCurrentPlayer()]);
					int wildCount = 0;
					String color = "";
					int colorCount = 0;
					
					for(String s : cards.keySet()) {
						if(s.equals("wild")) {
							wildCount = cards.get(s);
						} else {
							color = s;
							colorCount = cards.get(s);
						}
					}
					game.placeTrack(track, color, colorCount, wildCount, id>100);
					break;
				}
				case 3: {
					requestGovContract();
				}
			}
			input.reset();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			game.updatePlayer();
		}
		
		int[][] mat = game.endGame();
	}
	
	public void requestGovContract() {
		ContractCard[] cards = game.drawContracts();
		BufferedImage[] img = new BufferedImage[cards.length];
		for(int i = 0; i < cards.length; i++) {
			img[i] = ImageLoader.getCopy(contractDrawer.getCardImages().get(cards[i]));
		}
		ArrayList<Integer> keep = input.requestGovernmentContract(cards, img);
		ArrayList<ContractCard> retCards = new ArrayList<ContractCard>();
		ArrayList<ContractCard> keepCards = new ArrayList<ContractCard>();
		for(int i = 0; i < cards.length; i++) {
			if(keep.contains(i))
				keepCards.add(cards[i]);
			else
				retCards.add(cards[i]);
		}

		game.setContracts(keepCards);
		game.returnContracts(retCards);
		
	}

	@Override
	public void update() {
		if(init) {
			TrainBackGround.update();
			return;
		}
		input.update();
		if (!(contractDrawer.getParent() == GraphicsPanel.getPanel())) {
			GraphicsPanel.getPanel().add(contractDrawer);
		}
		handDrawer.setTree(game.getPlayers()[game.getCurrentPlayer()].getCards());
		contractDrawer.setPlayerContracts(game.getPlayers()[game.getCurrentPlayer()].getContracts());
		AnimationManager.update();
	}

	public void drawPiles(Graphics2D g) {
		g.fillRect(1625, 0, 500, 1080);
		g.drawImage(govContract, 1700, 900, (govContract.getWidth() * 2) / 3, (govContract.getHeight() * 2) / 3, null);
		AffineTransform at = new AffineTransform();
		at.setToTranslation(1900, 760);
		at.rotate(Math.PI / 2);
		at.scale(0.25, 0.25);
		g.drawImage(trainContract, at, null);

		Deck deck = game.getDeck();
		int y = 20;

		for (String s : deck.getUpCards()) {
			if(s != null)
			g.drawImage(cards.get(s), 1700, y, (cards.get(s).getWidth() * 8) / 10, (cards.get(s).getHeight() * 8) / 10,
					null);
			y += 130;
		}
	}
	
	
	
	public void drawLeaderBoard(Graphics2D g) {
		Player[] players = game.getPlayers();
		ArrayList<Player> sorted = new ArrayList<>();
		IntStream.range(0, players.length).forEach(i -> sorted.add(players[i]));
		Collections.sort(sorted, (a,b) -> Integer.compare(b.getPoints(), a.getPoints()));
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 15));
		g.drawString(String.format("%8s   %8s %8s   %14s    %14s","","Points","Trains","Train Cards","Contract Cards"), 20, 80);
		int y = 150;
		for(Player p : sorted) {
			g.setColor(p == players[game.getCurrentPlayer()]?Color.WHITE:Color.BLACK);
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
		if(init) {
			TrainBackGround.draw(g);
			g.setFont(new Font("TimesRoman", Font.BOLD, 60));
			g.drawString("Loading game", 800, 650);
			return;
		}
		handDrawer.draw(g);
		Color c2 = Color.RED.darker();
		GradientPaint gp1 = new GradientPaint(0, 0, Color.orange, 0, (600), c2, true);
		Paint p = g.getPaint();
		g.setPaint(gp1);
		g.fillRect(0, 0, 398, 400);
		g.setPaint(p);
		g.drawImage(logo, 10, 0, null);

		c2 = Color.YELLOW;
		gp1 = new GradientPaint(0, 0, Color.ORANGE.darker(), 0, (600), c2, true);
		g.setPaint(gp1);
		g.fillRect(0, 400, 398, 680);
	 	drawPiles(g);
		cMapDrawer.draw(g);
		input.draw(g);
		drawLeaderBoard(g);
		AnimationManager.draw(g);
		
	}
	

}
