package game.graphics.screens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.ai.AIPlayer;
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

	public static LinkedHashMap<String, BufferedImage> cards;

	private GameState game;
	private CityMapDrawer cMapDrawer;
	private ContractCardDrawer contractDrawer;
	private HandDrawer handDrawer;

	private Thread gameThread;
	private BufferedImage logo, govContract, trainContract;

	private InputManager input;
	private boolean init;
	private boolean running;
	

	public GameScreen(MouseInput in) {
		input = new InputManager(in);
		logo = ImageLoader.loadImage("resources/menuscreen/logo2.png");
		logo = ImageLoader.resize(logo, logo.getWidth() / 3, logo.getHeight() / 3);
		govContract = ImageLoader.loadImage("resources/contractcard/ticket_card_back.jpg");
		trainContract = ImageLoader.loadImage("resources/traincards/backtrain.png");
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
		AnimationManager.init();
		handDrawer.setTree(game.getPlayers()[game.getCurrentPlayer()].getCards());
		
		init = false;
	}
	@Override
	public void run() {
		initGame();
		running = true;
		for(int i = 0; i < game.getPlayers().length; i++) {
			if(game.getPlayers()[game.getCurrentPlayer()] instanceof AIPlayer) {
				try {Thread.sleep(500);} catch (InterruptedException e) {	}
				AIPlayer player = (AIPlayer) game.getPlayers()[game.getCurrentPlayer()];
				player.startGameMove();
			} else {
				requestGovContract(5);
			}
			input.reset();
			try {Thread.sleep(1000);} catch (InterruptedException e) {	}
			game.updatePlayer();

		}
		while (game.hasWinner() <= 4) {
			if(game.hasWinner() == 1)
			{
				JOptionPane.showMessageDialog(new JFrame("Game Ending"),
						"A player now has 3 or less train cards! Everyone gets one last turn now!", 
						"Game Ending", JOptionPane.PLAIN_MESSAGE);
			}if(game.getPlayers()[game.getCurrentPlayer()] instanceof AIPlayer) {
				try {Thread.sleep(500);} catch (InterruptedException e) {	}
				AIPlayer player = (AIPlayer) game.getPlayers()[game.getCurrentPlayer()];
				player.makeMove();
			} else {
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
							int index = input.requestTrainCardSelection(coords,game.getNumCardsDrawn(),upTrains,game);
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
						//System.out.println(game.getPlayers()[game.getCurrentPlayer()].getTrainColor());
						input.reset();
						Track track = game.getBoard().getTrack(id > 100? id-1000:id);
						HashMap<String,Integer> cards = input.requestCards(track, id>100, HandDrawer.getCards(),game.getPlayers()[game.getCurrentPlayer()]);
						int wildCount = 0;
						String color = "";
						int colorCount = 0;
						
						for(String s : cards.keySet()) {
							if(s.equals("wild")) {
								wildCount = cards.get(s);
							} else if(cards.get(s) != 0) {
								color = s;
								colorCount = cards.get(s);
							}
						}
						game.placeTrack(track, color, colorCount, wildCount, id>100);
						break;
					}
					case 3: {
						requestGovContract(3);
					}
				}
			}
			input.reset();
			try {Thread.sleep(1000);} catch (InterruptedException e){e.printStackTrace();}
			game.updatePlayer();
		}		
		GraphicsPanel.getPanel().remove(contractDrawer);
		ScreenManager.switchEndGame(game.endGame(), game);
		running = false;
	}
	
	public void requestGovContract(int n) {
		ContractCard[] cards = game.drawContracts(n);
		BufferedImage[] img = new BufferedImage[cards.length];
		for(int i = 0; i < cards.length; i++) {
			img[i] = ImageLoader.getCopy(contractDrawer.getCardImages().get(cards[i]));
		}
		ArrayList<Integer> keep = input.requestGovernmentContract(cards, img);
		if(keep == null)
		{
			game.endGameDebug();
			keep = input.requestGovernmentContract(cards, img);
		}
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
//		Collections.sort(sorted, (a,b) -> Integer.compare(b.getPoints(), a.getPoints()));
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 15));
		g.drawString(String.format("%8s   %8s %8s   %14s    %14s","","Points","Trains","Train Cards","Contract Cards"), 20, 80);
		int y = 150;
		for(Player p : sorted) {
			g.setColor(p == players[game.getCurrentPlayer()]?Color.MAGENTA:Color.BLACK);
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
		Color c2 = new Color(126, 249, 255);
		GradientPaint gp1 = new GradientPaint(0, 0, Color.YELLOW, 0, (600), c2, true);
		Paint p = g.getPaint();
		g.setPaint(gp1);
		g.fillRect(0, 0, 398, 400);
		g.setPaint(p);
		g.drawImage(logo, 10, 0, null);
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(5));
		g.drawLine(0, 390, 398, 390);

		c2 = new Color(126, 249, 255);
		gp1 = new GradientPaint(0, 0, Color.YELLOW, 0, (600), c2, true);
		g.setPaint(gp1);
		g.fillRect(0, 400, 398, 680);
	 	drawPiles(g);
		cMapDrawer.draw(g);
		input.draw(g);
		drawLeaderBoard(g);
		AnimationManager.draw(g);
	}
	
	public boolean running() {
		return running;
	}
}