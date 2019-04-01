
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

import javax.swing.JPanel;

import game.graphics.engine.GraphicsPanel;

public class ContractCardDrawer extends JPanel implements AdjustmentListener {
	private int dx;

	
	public ContractCardDrawer() {
		setBounds(300,920,1250,1000);
		setLayout(new BorderLayout());
		Scrollbar sb = new Scrollbar();
		
		sb.setBackground(Color.white);
		sb.setOrientation(Scrollbar.HORIZONTAL);
		add(sb, BorderLayout.SOUTH);
		sb.setSize(1250, 20);
		sb.addAdjustmentListener(this);
	}
	
	public void paintComponent(Graphics gg) {
		Graphics2D g = (Graphics2D)gg;
		g.translate(-dx, 0);
		Color c2 = new Color(255,248,220);
		GradientPaint gp1 = new GradientPaint(0, 0, new Color(165,42,42).darker(), 5000, (200), c2, true);
		Paint p = g.getPaint();
		g.setPaint(gp1);
		g.fillRect(0, 0, 5000, 200);
	//	g.setPaint(p);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD, 100));
		g.drawString("fffffffffffffffffffffffffffffffffasdafsdafsfjipaojewf/p;jnfweqpjnfapfewnfp;jfn/pufn/apu.n/pur/fffffffffffffff",30 , 100);
		g.translate(dx, 0);
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		dx = e.getAdjustable().getValue() * 10;
	}	
}