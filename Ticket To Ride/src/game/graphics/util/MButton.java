package game.graphics.util;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
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
	private boolean validRelease;
	
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
	
	public static Shape roundedRect(int width, int height, int borderRadius) {
		Path2D path = new Path2D.Double();
		Arc2D arc = new Arc2D.Double();
		path.moveTo(-width / 2 + borderRadius, -height / 2);
		arc.setArcByCenter(width / 2 - borderRadius, -height / 2 + borderRadius, borderRadius, 90, -90, Arc2D.OPEN);
		path.append((Arc2D)arc.clone(), true);
		arc.setArcByCenter(width / 2 - borderRadius, height / 2 - borderRadius, borderRadius, 0, -90, Arc2D.OPEN);
		path.append((Arc2D)arc.clone(), true);
		arc.setArcByCenter(-width / 2 + borderRadius, height / 2 - borderRadius, borderRadius, 270, -90, Arc2D.OPEN);
		path.append((Arc2D)arc.clone(), true);
		arc.setArcByCenter(-width / 2 + borderRadius, -height / 2 + borderRadius, borderRadius, 180, -90, Arc2D.OPEN);
		path.append((Arc2D)arc.clone(), true);
		path.closePath();
		return path;
	}
	
	public static Shape ellipse(int width, int height) {
		Ellipse2D shape = new Ellipse2D.Double(0, 0, width, height);
		return shape;
	}
	
	public boolean checkContains(Point2D p) {
		Point2D p2 = (Point2D) p.clone();
		p2.setLocation(p2.getX() - center.getX(), p2.getY() - center.getY());
		return shape.contains(p2);
	}
	
	public void draw(Graphics2D gg) {
		Graphics2D g = (Graphics2D) gg.create();
		if (!cleared) {
			AffineTransform old = g.getTransform();
			
			g.translate(center.getX() - 5, center.getY() + 5);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g.setColor(Color.black);
			g.fill(shape);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			
			if (!pressed) 
				g.translate(5, -5);
			
			
			Color c2 = background.darker().darker().darker().darker();
			GradientPaint gp1 = new GradientPaint(0, 0, background, 0, (int)(shape.getBounds2D().getHeight() / 1.5), c2, true);
			if (pressed) {
				gp1 = new GradientPaint(0, 0, background.darker().darker(), 0, (int)(shape.getBounds2D().getHeight() / 1.5), background, true);
			}
			g.setPaint(gp1);
			g.fill(shape);
			
			g.setColor(Color.black);
			g.setStroke(new BasicStroke(3));
			g.draw(shape);
			g.setStroke(new BasicStroke(1));
			
			g.setFont(font);
			int width = g.getFontMetrics().stringWidth(text);
			int height = g.getFontMetrics().getHeight();
			g.setColor(foreground);
			g.drawString(text, (int)(-width / 2 + shape.getBounds().getWidth()/2), (int) (-height / 2 + g.getFontMetrics().getAscent() + shape.getBounds().getHeight()/2));
			
		} else {
			g.translate(center.getX(), center.getY());
		}
		if (!pressed) {
			g.setColor(background);
			g.setStroke(new BasicStroke(3));
			g.draw(shape);
			g.setStroke(new BasicStroke(1));
		}
	}

	public boolean isValidRelease() {
		return validRelease;
	}

	public void setValidRelease(boolean validRelease) {
		this.validRelease = validRelease;
	}
}
