package Objects;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Player extends JPanel{
	double x = 0;
	double y = 0;


	public Player() {
	}
	
	

	
	public void update(double delta) {
		x += 1 * delta;
		//y += 1 * delta;
	}
	
	public void render() {
		this.repaint();
	}
	
	
	public void keyReleased(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			y =  100;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			y = 300;
	}

	public void paint(Graphics2D g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int xUsable = (int)x;
		int yUsable = (int)y;
		try {
			BufferedImage image = ImageIO.read(new File("Assets/Player.png"));
			g.drawImage(image, xUsable, yUsable, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	
}
