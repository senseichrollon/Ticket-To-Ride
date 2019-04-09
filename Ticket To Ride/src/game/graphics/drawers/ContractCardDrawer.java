
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
		setBounds(398,920,1226,1000);
		setLayout(new BorderLayout());
		Scrollbar sb = new Scrollbar();
		
		sb.setBackground(Color.white);
		sb.setOrientation(Scrollbar.HORIZONTAL);
		add(sb, BorderLayout.SOUTH);
		sb.setSize(1226, 20);
		sb.addAdjustmentListener(this);
	}
	
	public void paintComponent(Graphics gg) {
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