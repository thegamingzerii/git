package Objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * 
 * @author Nick
 *
 *	frameSpeed: 60 = 1 sec
 */

@SuppressWarnings("serial")
public class SpriteSheet extends JPanel{
	double width;
	double height;
	String path;
	int frameSpeed;
	double counter = 0;
	int frameCount;
	
	SpriteSheet(String path, double frameWidth, double frameHeight, int frameSpeed, int frameCount){
		this.width = frameWidth;
		this.height = frameHeight;
		this.path  = path;
		this.frameSpeed = frameSpeed;
		this.frameCount = frameCount;
	}
	
	public void update(double delta) {
		counter += delta;
	}
	
	public void paint(Graphics2D g, double x, double y) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int xUsable = (int)x;
		int yUsable = (int)y;
		
		try {
			BufferedImage image = ImageIO.read(new File(path));
			Image rightPart = image.getSubimage((int) ((Math.floor(counter / frameSpeed)%frameCount) * width), 0, (int)width, (int)height);
			Image scaledImage = rightPart.getScaledInstance(64, 64, image.SCALE_DEFAULT);
			g.drawImage(scaledImage, xUsable, yUsable, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
