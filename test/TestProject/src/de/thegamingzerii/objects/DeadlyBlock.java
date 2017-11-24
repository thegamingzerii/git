package de.thegamingzerii.objects;

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
import de.thegamingzerii.states.GameState;

@SuppressWarnings("serial")
public class DeadlyBlock extends JPanel implements IInteract{

public static ArrayList<DeadlyBlock> allDeadlyBlocks =  new ArrayList<DeadlyBlock>();
	
	double x = 0;
	double y = 0;
	double width = 0;
	double height = 0;
	
	
	public DeadlyBlock(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		allDeadlyBlocks.add(this);
	}
	
	public Rectangle getCollisionSize() {
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
	}
	
	public String toString() {
		return "DeadlyBlock " + x + " " + y + " " + width+ " " + height;
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

	@Override
	public void interact(Boolean keyPressed) {
		GameState.player.damage(1);
		
	}

	@Override
	public boolean checkProximity(Rectangle rect) {
		
		return rect.intersects(getCollisionSize());
	}
	
	@SuppressWarnings("static-access")
	public void paint(Graphics2D g) {
		if(width < 2*Camera.scale || height < 2*Camera.scale) {
			if(width < 1 || height < 1){
				DeadlyBlock.allDeadlyBlocks.remove(this);
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
					BufferedImage image = ImageIO.read(new File("Assets/DeadlyBlock.png"));
					Image scaledImage = image.getScaledInstance((int)(width * Camera.scale), (int)(height * Camera.scale), image.SCALE_DEFAULT);
					g.drawImage(scaledImage, xUsable, yUsable, this);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
