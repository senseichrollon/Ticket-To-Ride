package game.graphics.drawers;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

import game.graphics.engine.GraphicsPanel;

public class HandDrawer {
	
	
	public void update() {
		
	}
	
	
	public void draw(Graphics2D g) {
		Color c2 = new Color(222,184,135).darker();
		GradientPaint gp1 = new GradientPaint(298, 720, new Color(222,184,135), 1524, (920), c2, true);
		g.setPaint(gp1);
		g.fillRect(298, 720, 1226, 200);
	}	
	
}
