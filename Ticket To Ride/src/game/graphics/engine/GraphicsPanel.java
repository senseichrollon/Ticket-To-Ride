package game.graphics.engine;
	
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.graphics.input.MouseInput;
import game.graphics.screens.ScreenManager;
import game.graphics.util.ImageLoader;

public class GraphicsPanel extends JPanel implements Runnable {
		
	public static final int WIDTH = 1920, HEIGHT = 1080;
	public static boolean animating;
	private static GraphicsPanel currentPanel;
	
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	
	public GraphicsPanel() {
		currentPanel = this;
		this.setSize(WIDTH, HEIGHT);
		MouseInput input = new MouseInput();
		ScreenManager.init(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Ticket to Ride");
		frame.setIconImage(ImageLoader.loadImage("resources/menuscreen/logo.png"));
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.add(new GraphicsPanel());
		frame.setVisible(true);
		
	}
	
	public void update() {
		ScreenManager.getCurrentScreen().update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ScreenManager.getCurrentScreen().draw((Graphics2D)g);
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
			} catch  (Exception e) {
			}
			repaint();
		}
	}
	
	public static GraphicsPanel getPanel() {
		return currentPanel;
	}
}
