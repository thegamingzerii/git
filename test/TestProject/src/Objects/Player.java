package Objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.thegamingzerii.maingame.PaintBox;

public class Player extends JPanel{
	double x = 0;
	double y = 0;

	public Player() {
		
	}
	
	public void update(double delta) {
		x += 1 * delta;
		y += 1 * delta;
	}
	
	public void render() {
		this.repaint();
	}

	public void paint(Graphics2D g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int xUsable = (int)x;
		int yUsable = (int)y;
		g2d.fillRect(xUsable, yUsable, 30, 60);
	}

	
}
