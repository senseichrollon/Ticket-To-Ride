package game.graphics.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import game.graphics.engine.GraphicsPanel;
import game.graphics.input.MouseInput;
import game.graphics.util.ImageLoader;
import game.graphics.util.MButton;
import game.main.GameState;

public class Endgame extends ScreenManager{

	private BufferedImage background;
	private int[][] data;
	private MButton exitButton;
	private MouseInput input;
	
	public Endgame(MouseInput input)
	{
		super();
		this.input = input;
		background = ImageLoader.loadImage("resources/gameboard/EndGameScreen.jpg");
		background = ImageLoader.resize(background, GraphicsPanel.WIDTH, GraphicsPanel.HEIGHT);
		exitButton = new MButton("Exit", new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 20), Color.GREEN, Color.orange);
		exitButton.setCenter(new Point(1650,75));
		exitButton.setShape(MButton.ellipse(200,100));
	}
	
	public void updateButton(boolean mousePressed, boolean mouseReleased, Point mouseLoc) {
		if(mousePressed)
			if(exitButton.checkContains(mouseLoc))
				exitButton.setPressed(true);
		if(mouseReleased)
			if(exitButton.checkContains(mouseLoc) && exitButton.isPressed())
				exitButton.setValidRelease(true);
		if(!mousePressed && !mouseReleased)
			if(!exitButton.checkContains(mouseLoc))
				exitButton.setPressed(false);
	}
	
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(background, 0, 0, null);
		draw((Graphics2D)g);
	}
	
	@Override
	public void update() 
	{
		updateButton(input.clicked(), input.released(), new Point(input.getX(), input.getY()));
		if(exitButton.isValidRelease()) {
			exitButton.setValidRelease(false);
			ScreenManager.switchScreen(MENU);
		}
	}
	
	public void setData(int[][] vals)
	{
		data = vals;
		sortData();
	}

	@Override
	public void draw(Graphics2D g) {
		if(data == null)
			return;
		g.drawImage(background, 0, 0, null);
		g.setFont(new Font("Arial", Font.BOLD, 60));
		
		exitButton.draw(g);
		
		for(int i = 0; i < data.length; i++) {
			g.drawString(GameState.PLAYER_NAMES[data[i][0]], 10, (380 + 180*i));
			g.drawString(String.valueOf(data[i][1]), 240, (430 + 180*i));
			g.drawString(String.valueOf(data[i][2]), 510, (430 + 180*i));
			g.drawString(String.valueOf(data[i][3]), 830, (430 + 180*i));
			g.drawString(String.valueOf(data[i][4]), 1180, (430 + 180*i));
			g.drawString(String.valueOf(data[i][5]), 1500, (430 + 180*i));
			g.drawString(String.valueOf(sumPts(data[i])), 1780, (430 + 180*i));
		}
	}
	
	private void sortData()
	{
		if(data == null)
			return;
		
		for(int i = 1; i < data.length; i++)
		{
			int[] check = data[i];
			int sumCheck = sumPts(check);
			int c = i-1;
			while(c >= 0 && sumPts(data[c]) < sumCheck)
			{
				data[c+1] = data[c]; 
				c--;
			}
			data[c+1] = check;
		}
	}
	
	private int sumPts(int[] vals)
	{
		return vals[1] + vals[2] - vals[3] + vals[4] + vals[5];
	}
	
	/*public static void main(String[] args)
	{
		JFrame jf = new JFrame("Test");
		Endgame eg = new Endgame();
		int[][] data = {{15, 15, 15, 15, 15, 15}, {15, 15, 15, 15, 15, 15}, {15, 15, 15, 15, 15, 15}, {15, 15, 15, 15, 15, 15}, {15, 15, 15, 15, 15, 15}, {15, 15, 15, 15, 15, 15}};
		eg.setData(data);
		jf.add(eg);
		jf.setSize(GraphicsPanel.WIDTH, GraphicsPanel.HEIGHT);
		jf.setVisible(true);
	}*/
}
