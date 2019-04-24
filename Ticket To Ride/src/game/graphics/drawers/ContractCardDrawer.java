package game.graphics.drawers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import game.entity.ContractCard;
import game.entity.Player;
import game.graphics.util.ImageLoader;

public class ContractCardDrawer extends JPanel implements AdjustmentListener {
	private int dx;
	private HashMap<ContractCard, BufferedImage> contractCards;
	private ArrayList<ContractCard> playerContracts;

	
	public ContractCardDrawer(ArrayList<ContractCard> cardList) {
		setBounds(398,900,1226,1000);
		setLayout(new BorderLayout());
		Scrollbar sb = new Scrollbar();
		
		sb.setBackground(Color.white);
		sb.setOrientation(Scrollbar.HORIZONTAL);
		add(sb, BorderLayout.SOUTH);
		sb.setSize(1226, 20);
		sb.addAdjustmentListener(this);
		
		
		contractCards = new HashMap<>();
		BufferedImage template = ImageLoader.loadImage("resources/contractcard/destcard.jpg");
		template = ImageLoader.resize(template, 1000, 1000);
		for(ContractCard c : cardList) {
			BufferedImage img = ImageLoader.getCopy(template);
			Graphics2D g = img.createGraphics();
			g.setColor(Color.black);
			String str = c.getCityOne() + "-" + c.getCityTwo();
			int size = str.length() > 23? 65: 80;
			g.setFont(new Font("TimesRoman", Font.BOLD, size));	
			g.drawString(str,120,205);
			g.setFont(new Font("TimesRoman", Font.BOLD, 120));
			g.drawString(Integer.toString(c.getPoints()), 770, 780);
			contractCards.put(c, img);
		}
		
	}
	
	public void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D)gg;
		g.translate(-dx, 0);
		Color c1 =  Color.RED;
		Color c2 = Color.ORANGE;
		GradientPaint gp1;
		Paint p = g.getPaint();
		for(int i = 0; i <= 5000; i+=200) {
			gp1 = new GradientPaint(i, 0, c1, i+200, (200), c2, false);
			g.setPaint(gp1);
			g.fillRect(i, 0, 200, 200);
			Color c = c1;
			c1 = c2;
			c2 = c;
		}
		g.setPaint(p);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD, 100));
		int x = 30;
		for(ContractCard card : playerContracts) {
			AffineTransform at = new AffineTransform();
			at.setToTranslation(x, 20);
			at.scale(0.17, 0.13);
			g.drawImage(contractCards.get(card),at,null);
			x += 200;
		}
		g.translate(dx, 0);
	}
	
	public HashMap<ContractCard,BufferedImage> getCardImages() {
		return contractCards;
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		dx = e.getAdjustable().getValue() * (Math.max(2, (playerContracts.size() * 5)/3));
	}
	
	public void setPlayerContracts(ArrayList<ContractCard> cards) {
		playerContracts = cards;
	}
}