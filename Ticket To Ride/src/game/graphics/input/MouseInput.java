package game.graphics.input;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {

	private int x, y;
	private boolean clicked;
	private boolean released;
	private boolean endGameHack;
	
	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		clicked = true;
		released = false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		clicked = false;
		released = true;
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
		return temp;
	}
	
	public boolean released() {
		boolean temp = released;
		released = false;
		return (temp);
	}
	

	
	public void mouseClicked(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		if(x > 1085 && x < 1095 && y > 213 && y < 223)
		{
			endGameHack = true;
			System.out.println("DuLuTh Is NoT a MaJoR cItY");
		}
	}
	
	public boolean duluthHack() {return endGameHack;}
	public void resetDuluth() {endGameHack = false;}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}
