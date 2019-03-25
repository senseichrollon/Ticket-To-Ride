package game.graphics.screens;

import java.awt.Color;
import java.awt.Graphics2D;

import game.graphics.engine.GraphicsPanel;
import game.graphics.input.MouseInput;
import game.graphics.util.MButton;

public class MenuScreen extends ScreenManager {

	private MouseInput input;
	private MButton playButton;
	
	public MenuScreen(MouseInput input) {
		this.input = input;
	}
	
	@Override
	public void update() {
		System.out.println("hi7");
		if(input.clicked()) {
			ScreenManager.switchScreen(ScreenManager.GAME);
		}
	}
	


	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, GraphicsPanel.WIDTH, GraphicsPanel.HEIGHT);
	}

}
