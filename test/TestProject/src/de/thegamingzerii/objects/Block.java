package de.thegamingzerii.objects;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;

public class Block extends JPanel implements ICollision{
	
	public static ArrayList<Block> allBlocks =  new ArrayList<Block>();
	
	double x = 0;
	double y = 0;
	double width = 0;
	double height = 0;
	
	
	public Block(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		allBlocks.add(this);
	}
	
	
	public void paint(Graphics2D g) {

		if(width < 2*Camera.scale || height < 2*Camera.scale) {
			if(width < 1 || height < 1){
				Block.allBlocks.remove(this);
			}
		}else {
			if(onScreen()) {
				super.paint(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				int xUsable = (int) ((x - Game.camera.getCameraPos().getX()) * Camera.scale);
				int yUsable = (int)((y - Game.camera.getCameraPos().getY()) * Camera.scale);
				
				try {
					BufferedImage image = ImageIO.read(new File("Assets/Block.png"));
					Image scaledImage = image.getScaledInstance((int)(width * Camera.scale), (int)(height * Camera.scale), image.SCALE_DEFAULT);
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
					g2d.drawImage(scaledImage, xUsable, yUsable, this);			
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		

	}


	@Override
	public boolean checkcollision(Rectangle rect) {
		return rect.intersects(getCollisionSize());
	}


	@Override
	public double getXAxis() {
		// TODO Auto-generated method stub
		return x;
	}


	@Override
	public double getYAxis() {
		// TODO Auto-generated method stub
		return y;
	}


	@Override
	public void setX(int x) {
		this.x = x;
		
	}


	@Override
	public void setY(int y) {
		this.y = y;
		
	}


	@Override
	public Rectangle getCollisionSize() {
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
	}
	
	public String toString() {
		return "Block " + x + " " + y + " " + width+ " " + height;
	}


	@Override
	public boolean onScreen() {
		if(x > Game.camera.getX() + Game.camera.getWidth())
			return false;
		if(y > Game.camera.getY() + Game.camera.getHeight())
			return false;
		if(x + width < Game.camera.getX())
			return false;
		if(y + height < Game.camera.getY())
			return false;
		return true;
	}


	
}

