package game.testing;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.graphics.animation.CardAnimator;
import game.graphics.util.ImageLoader;

public class TestCardAnimationraphicsPanel extends JPanel implements Runnable, MouseListener {
		
	public static final int WIDTH = 1920, HEIGHT = 1080;
	public static boolean animating;
	private int x, y;
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	private CardAnimator anim;	
	private int x2;
	
	public TestCardAnimationraphicsPanel() {
		requestFocus();
		running = true;
		BufferedImage card = ImageLoader.loadImage("resources/traincards/backtrain.png");
		card = ImageLoader.resize(card, card.getWidth()/4, card.getHeight()/4);
		anim = new CardAnimator(20,20,900,200,card,5000000000L,1.25,1.5,1.5);
		thread = new Thread(this);
		thread.start();
		addMouseListener(this);
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Ticket to Ride");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	//		frame.setFocusable(true);
		frame.add(new TestCardAnimationraphicsPanel());
		frame.setVisible(true);
		
	}
	
	public void update() {
		if(!anim.hasStarted()) {
			anim.start();
		} else if(anim.canEnd()) {
			anim.stop();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D)gg;
		if(anim.hasStarted()) {
			anim.draw(g);
		}
		
		g.drawRect(20, 20, 50, 50);
		g.drawRect(900, 200, 50, 50);
		g.setFont(new Font("TimesRoman", Font.BOLD, 60));
		g.drawString(x + " " + y, x2, 1000);
	}

	@Override
	public void run() {
		long start, elapsed, wait;
		while (running) {
			start = System.nanoTime();
			update();
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			if (wait < 0)
				wait = 5;
			try {
				Thread.sleep(wait);
			} catch (Exception e) {
			}
			repaint();
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		x2 += 300;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
