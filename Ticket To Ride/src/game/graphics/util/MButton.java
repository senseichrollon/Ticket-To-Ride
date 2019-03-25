package game.graphics.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;

public class MButton {
	private String text;
	private Font font;
	
	private Color background;
	private Color foreground;

	private Point2D center;
	private Shape shape;
	
	private boolean pressed;
	private boolean cleared;
	
	public MButton(String text, Font font, Color background, Color foreground) {
		this.setText(text);
		this.setFont(font);
		this.setBackground(background);
		this.setForeground(foreground);
	}
	
	public void setCenter(Point2D center) {
		this.center = center;
	}
	
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public boolean isCleared() {
		return cleared;
	}

	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}
	
	public void draw(Graphics2D g) {
		
	}
}
