package game.graphics.animation;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.graphics.drawers.HandDrawer;
import game.graphics.screens.GameScreen;
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
		CardAnimator cardAnim = new CardAnimator(1750,760,(int)points[idx].getX(),(int)points[idx].getY(),img,500000000L,1,1,1);
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
		
		CardAnimator cardAnim = new CardAnimator(x1,y1,x2,y2,img,500000000L,1.25,0.65/(8.0/10),0.65/(8.0/10));
		cardAnim.start();
		animations.add(cardAnim);
		while (AnimationManager.animating()) {
			try {Thread.sleep(10);} catch (InterruptedException e) {}
		}
	}
	
	public static void faceDownCardAnimation(String color) {
		BufferedImage img = ImageLoader.loadImage("resources/traincards/backtrain.png");
		img = ImageLoader.resize(img, img.getWidth()/4, img.getHeight()/4);
		
		int x1 = 1750;
		int y1 = 760;
		int x2 = (int)HandDrawer.getCardPoint(color).getX()-100;
		int y2 = (int)HandDrawer.getCardPoint(color).getY()-20;
		

		
		CardAnimator cardAnim = new CardAnimator(x1,y1,x2,y2,img,500000000L,0.5,0.7,0.7);
		cardAnim.start();
		animations.add(cardAnim);
		while (AnimationManager.animating()) {
			try {Thread.sleep(10);} catch (InterruptedException e) {}
		}
	}
		
	public static void placeTrainsAnimation(String color, int count, int wildCount) {
		int x1 = 0,y1 = 0;
		BufferedImage img = null;

		if(!color.equals("")) {
			 x1 = (int)HandDrawer.getCardPoint(color).getX()-100;
			 y1 = (int)HandDrawer.getCardPoint(color).getY()-20;
			 img = HandDrawer.getCards().get(color);
			img = ImageLoader.resize(img, (int)(img.getWidth() * 0.65), (int)(img.getHeight() * 0.65));
		}
		int x2 = (int)HandDrawer.getCardPoint("wild").getX()-100;
		int y2 = (int)HandDrawer.getCardPoint("wild").getY()-20;
		
		int x3 = 1700;
		int y3 = 760;
		
		
		long ratio = (long)Math.max(count, wildCount);

		
		
		BufferedImage wild = HandDrawer.getCards().get("wild");
		wild = ImageLoader.resize(wild, (int)(wild.getWidth() * 0.65), (int)(wild.getHeight() * 0.65));
		for(int i = 0; i < Math.max(count, wildCount); i++) {
			if(i < count) {
				CardAnimator anim = new CardAnimator(x1,y1,x3,y3, img,500000000L,1,1.42,1.42);
				anim.start();
				animations.add(anim);
			}
			
			if(i < wildCount) {
				CardAnimator anim = new CardAnimator(x2,y2,x3,y3, wild,500000000L,1,1.42,1.42);
				anim.start();
				animations.add(anim);
			}
			try {Thread.sleep(200);} catch (InterruptedException e) {}
		}
		while(animating()) {
			try {Thread.sleep(10);} catch (InterruptedException e) {}
		}
		
	}
	
	public static void shuffle(String[] cards, boolean in) {
		try {
			Point[] points = new Point[5];
			BufferedImage[] img = new BufferedImage[5];
			int y = 20;
			for(int i = 0; i < 5; i++) {
				points[i] = new Point(1700,y);
				y += 130;
				img[i] = GameScreen.cards.get(cards[i]);
				img[i] = ImageLoader.resize(img[i], img[i].getWidth()*8/10, img[i].getHeight() * 8/10);
			}
			
			int x;
			if(in) {
				x = points[4].x;
				y = points[4].y;
			} else {
				x = 1700;
				y = 760;
			}

			ShuffleAnimator anim = new ShuffleAnimator(points,img,x,y,500000000L,in);
			anim.start();
			animations.add(anim);
			while(AnimationManager.animating())
				try {Thread.sleep(10);} catch (InterruptedException e) {}
			if(in) {
				CardAnimator cardAnim = new CardAnimator(x,y,1700,760,img[4],500000000L,0,1,1);
				cardAnim.start();
				animations.add(cardAnim);
				while(AnimationManager.animating())
					try {Thread.sleep(10);} catch (InterruptedException e) {}
			}
		} catch(Exception e) {
			
		}
	}
	
	public static boolean animating() {
		return animations.size() != 0;
	}
	
}