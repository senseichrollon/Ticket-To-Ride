package game.graphics.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
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
		
		shape = roundedRect(100,100,100);
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
	
	public static Shape roundedRect(double width, double height, double radius) {
		Path2D shape = new Path2D.Double();
		shape.moveTo(0, 0);
		shape.lineTo(width - radius, 0);
		shape.curveTo(width, 0, width, 0, width, radius);
		shape.lineTo(width, height - radius);
		shape.curveTo(width, height, width, height, width - radius, height);
        shape.lineTo(0, height);
        AffineTransform at = new AffineTransform();
        at.translate(900, 900);
        shape.transform(at);
        shape. closePath();
		return shape;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(foreground);
		g.draw(shape);
	}
}
