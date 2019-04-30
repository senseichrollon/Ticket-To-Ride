package game.graphics.animation;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.graphics.drawers.HandDrawer;
import game.graphics.util.ImageLoader;

public class AnimationManager {
	private static ArrayList<Animator> animations;
	
	public static void init() {
		animations = new ArrayList<Animator>();
	}
	
	public static void update() {
		for(int i = 0; i < animations.size(); i++) {
			if(animations.get(i).canEnd()) {
				animations.get(i).stop();
				animations.remove(i);
				i--;
			}
		}
	}
	
	public static void draw(Graphics2D g) {
		for(int i = 0; i < animations.size(); i++) {
			animations.get(i).draw(g);
		}
	}
	
	public static void replaceTrainsAnimation(int idx,String color) {
		Point[] points = new Point[5];
		int y = 20;
		for(int i = 0; i < 5; i++) {
			points[i] = new Point(1700,y);
			y += 130;
		}
		BufferedImage img = ImageLoader.loadImage("resources/traincards/backtrain.png");
		img = ImageLoader.resize(img, img.getWidth()/4, img.getHeight()/4);
		CardAnimator cardAnim = new CardAnimator(1750,760,(int)points[idx].getX(),(int)points[idx].getY(),img,1000000000L,3,1,1);
		cardAnim.start();
		animations.add(cardAnim);
	}
	
	public static void addTrainCardAnimation(int idx,String color) {
		BufferedImage img = HandDrawer.getCards().get(color);
		img = ImageLoader.resize(img, (img.getWidth() * 8)/10, (img.getHeight() * 8)/10);
		Point[] points = new Point[5];
		int y = 20;
		for(int i = 0; i < 5; i++) {
			points[i] = new Point(1700,y);
			y += 130;
		}
		
		int x1 = (int)points[idx].getX();
		int y1 = (int)points[idx].getY();
		
		int x2 = (int)HandDrawer.getCardPoint(color).getX()-100;
		int y2 = (int)HandDrawer.getCardPoint(color).getY()+50;
		
		CardAnimator cardAnim = new CardAnimator(x1,y1,x2,y2,img,1000000000L,2.25,0.65/(8.0/10),0.65/(8.0/10));
		cardAnim.start();
		animations.add(cardAnim);
	}
	
	public static void faceDownCardAnimation(String color) {
		BufferedImage img = ImageLoader.loadImage("resources/traincards/backtrain.png");
		img = ImageLoader.resize(img, img.getWidth()/4, img.getHeight()/4);
		
		int x1 = 1750;
		int y1 = 760;
		int x2 = (int)HandDrawer.getCardPoint(color).getX()-100;
		int y2 = (int)HandDrawer.getCardPoint(color).getY()-20;
		

		
		CardAnimator cardAnim = new CardAnimator(x1,y1,x2,y2,img,1000000000L,0.5,0.7,0.7);
		cardAnim.start();
		animations.add(cardAnim);
	}
	
	public static void returnGovernmentContractAnimation() {
		
	}
	
	public static boolean animating() {
		return animations.size() != 0;
	}
	
}
