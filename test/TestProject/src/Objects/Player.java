package Objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thegamingzerii.utility.Constantes;


public class Player extends JPanel{
	double x = 0;
	double y = 0;
	double xAcc = 0;
	double yAcc = 0;
	double height = 200;
	double width = 100;

	public Player() {
	}
	
	

	
	public void update(double delta) {
		if(yAcc < Constantes.GRAVITY) {
			yAcc += 0.5;
		}
		
		
		x += xAcc * delta;
		if(! (y > Constantes.height - height) || yAcc < 0) 
			y += yAcc * delta;
		
	}
	public void render() {
		this.repaint();
	}
	
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			xAcc += 10;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			xAcc -= 10;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			xAcc -= 10;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			xAcc += 10;
		if (e.getKeyCode() == KeyEvent.VK_SPACE) 
			yAcc = -50;
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
			Image scaledImage = image.getScaledInstance((int)width, (int)height, image.SCALE_DEFAULT);
			g.drawImage(scaledImage, xUsable, yUsable, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	
}
