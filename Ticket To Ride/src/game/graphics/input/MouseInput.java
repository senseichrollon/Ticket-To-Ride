package game.graphics.input;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {

	private int x, y;
	private boolean clicked;
	private boolean released;
	
	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		clicked = true;
		released = false;
		System.out.println("hi");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		clicked = false;
		released = true;
		System.out.println("hi2");
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
		clicked = false;
		released = false;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean clicked() {
		boolean temp = clicked;
		clicked = false;
		System.out.println(temp);
		return temp;
	}
	
	public boolean released() {
		boolean temp = released;
		released = false;
		System.out.println(temp);
		return (temp);
	}
	

	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}
