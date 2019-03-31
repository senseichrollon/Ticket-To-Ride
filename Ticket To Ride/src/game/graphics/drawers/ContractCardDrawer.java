package game.graphics.drawers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;

import game.graphics.engine.GraphicsPanel;

public class ContractCardDrawer extends JPanel implements AdjustmentListener {
	private int dx;

	
	public ContractCardDrawer() {
		setBounds(1000,1000,1000,1000);
		GraphicsPanel.getPanel().add(this);
		setLayout(new BorderLayout());
		Scrollbar sb = new Scrollbar();
		
		sb.setBackground(Color.green);
		sb.setOrientation(Scrollbar.HORIZONTAL);
		add(sb, BorderLayout.SOUTH);
		sb.setSize(1000, 100);
		sb.addAdjustmentListener(this);
	}
	
	public void paintComponent(Graphics gg) {
		Graphics2D g = (Graphics2D)gg;
		g.translate(-dx, 0);
		g.setColor(Color.GREEN);
		g.drawRect(0, 0, 1000, 1000);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD, 30));
		g.drawString("fffffffffffffffffffffffffffffffffasdafsdafsfjipaojewf/p;jnfweqpjnfapfewnfp;jfn/pufn/apu.n/pur/fffffffffffffff", 10, 10);
		g.translate(dx, 0);
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		dx = e.getAdjustable().getValue() * 10;
	}	
}