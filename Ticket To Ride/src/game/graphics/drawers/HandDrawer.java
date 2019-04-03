package game.graphics.drawers;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

import game.graphics.engine.GraphicsPanel;

public class HandDrawer {
	
	
	public void update() {
		
	}
	
	
	public void draw(Graphics2D g) {
		Color c2 = Color.RED.darker();
		GradientPaint gp1 = new GradientPaint(298, 720, Color.ORANGE, 1524, (920), c2, false);
		g.setPaint(gp1);
		g.fillRect(298, 720, 1226, 200);
	}	
	
}
