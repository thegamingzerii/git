package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;


/**
 * 
 * @author Nick
 *
 *	frameSpeed: 60 = 1 sec
 */

@SuppressWarnings("serial")
public class SpriteSheet extends JPanel{
	double frameWidth;
	double frameHeight;
	double width;
	double height;
	String path;
	int frameSpeed;
	double counter = 0;
	int frameCount;
	
	SpriteSheet(String path, double frameWidth, double frameHeight, int frameSpeed, int frameCount, double scaling){
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.width = frameWidth * scaling;
		this.height = frameHeight * scaling;
		this.path  = path;
		this.frameSpeed = frameSpeed;
		this.frameCount = frameCount;
	}
	
	public void update(double delta) {
		counter += delta;
	}
	
	public void paint(Graphics2D g, double x, double y, int row) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int xUsable = (int) ((x - Game.camera.getCameraPos().getX()) * Camera.scale);
		int yUsable = (int)((y - Game.camera.getCameraPos().getY()) * Camera.scale);
		
		try {
			BufferedImage image = ImageIO.read(new File(path));
			Image rightPart = image.getSubimage((int) ((Math.floor(counter / frameSpeed)%frameCount) * frameWidth), (int) (row * frameHeight), (int)frameWidth, (int)frameHeight);
			Image scaledImage = rightPart.getScaledInstance((int)(width * Camera.scale), (int)(height * Camera.scale), image.SCALE_DEFAULT);
			g.drawImage(scaledImage, xUsable, yUsable, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
