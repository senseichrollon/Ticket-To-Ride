package game.graphics.screens;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import game.entity.Player;
import game.graphics.engine.GraphicsPanel;
import game.graphics.util.ImageLoader;

public class Endgame extends ScreenManager{

	private BufferedImage background;
	private Player[] players;
	
	public Endgame()
	{
		background = ImageLoader.loadImage("resources/gamedata/End game summary.jpg");
		background = ImageLoader.resize(background, GraphicsPanel.WIDTH, GraphicsPanel.HEIGHT);
	}
	
	@Override
	public void update() {}
	
	public void setPlayers(Player[] ps)
	{
		players = ps;
		sortPlayers();
	}

	@Override
	public void draw(Graphics2D g) {
		if(players == null)
			return;
		g.drawImage(background, 0, 0, null);
		
		
	}
	
	private void sortPlayers()
	{
		if(players == null)
			return;
		
		for(int i = 1; i < players.length; i++)
		{
			Player check = players[i];
			int c = i-1;
			while(c >= 0 && players[c].getPoints() < check.getPoints())
			{
				players[c+1] = players[c]; 
				c--;
			}
			players[c+1] = check;
		}
		
		for(Player i: players)
			System.out.println(i.getPoints());
	}
	
	public static void main(String[] args) {
		Endgame eg = new Endgame();
		Player[] add = new Player[3];
		Player p = new Player("Bob", "Green");
		p.setPoints(100);
		add[0] = p;
		p = new Player("Dob", "Been");
		p.setPoints(100);
		add[1] = p;
		p = new Player("Job", "een");
		p.setPoints(100);
		add[2] = p;
		eg.setPlayers(add);
	}
}
