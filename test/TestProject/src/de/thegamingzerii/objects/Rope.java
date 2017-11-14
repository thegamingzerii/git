package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.states.GameState;

public class Rope extends JPanel implements IInteract{

	private Line2D line;
	private double length;
	private double originalX;
	private double blocked = 0;
	private double xSpeedMult;
	private double xAcc;
	private boolean swinging = false;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	private double swingTimer = 0;
	public static ArrayList<Rope> allRopes = new ArrayList<Rope>();
	
	public Rope(double x, double y, double length) {
		line = new Line2D.Double(x, y, x, y + length);
		allRopes.add(this);
		this.length = length;
		this.originalX = x;
	}
	
	public void interactionBlocked(int time) {
	
		blocked = time;
	}
	
	@Override
	public void interact() {
		if(blocked <= 0 && !GameState.player.hanging) {
			GameState.player.rope = this;
			GameState.player.hanging = true;
			swinging = true;
			swingTimer = 0;
			xSpeedMult = Math.abs(GameState.player.xSpeed / 12);
			if(GameState.player.xSpeed < 0)
				swingTimer = Math.PI;
		}
		
		
	}

	@Override
	public boolean checkProximity(Rectangle rect) {
		// TODO Auto-generated method stub
		return line.intersects(rect);
	}

	@Override
	public boolean onScreen() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public void recieveMovement(boolean direction, boolean released) {
		if(released) {
			if(direction)
				moveLeft = false;
			else
				moveRight = false;
		}else {
			if(direction) {
				moveLeft = true;
				moveRight = false;
			}
			else {
				moveRight = true;
				moveLeft = false;
			}
		}
	}
	
	public void update(double delta) {
		blocked -= delta;
		if(swinging)
			swingTimer += delta * 0.05;
			
			
		
		
		
		double x1 = line.getX1();
		double x2 = originalX + Math.sin(swingTimer) * this.length*0.7 * xSpeedMult;
		double y1 = line.getY1();
		double length = this.length;
		double y2 = Math.sqrt(Math.pow(length, 2) - Math.pow(x2 - x1, 2) ) + y1;
		line.setLine(x1, y1, x2, y2);
		if(swinging) {
			if(moveLeft && Math.sin(swingTimer) - Math.sin(swingTimer+0.1) > 0 && xSpeedMult <= 1) {
				xSpeedMult += xSpeedMult * 0.003;
			}
			if(moveRight && Math.sin(swingTimer) - Math.sin(swingTimer+0.1) < 0 && xSpeedMult <= 1) {
				xSpeedMult += xSpeedMult * 0.003;
			}
			if(moveLeft && Math.sin(swingTimer) - Math.sin(swingTimer+0.1) < 0) {
				xSpeedMult -= xSpeedMult * 0.01;
			}
			if(moveRight && Math.sin(swingTimer) - Math.sin(swingTimer+0.1) > 0) {
				xSpeedMult -= xSpeedMult * 0.01;
			}
		}
			
		
		if(GameState.player.hanging && GameState.player.rope == this) {
			xSpeedMult -= xSpeedMult * 0.001;
			GameState.player.changePos(x2, y2);
			blocked = 30;
		}
		else
			xSpeedMult -= xSpeedMult * 0.01;
		
		if(xSpeedMult < 0.02)
			xSpeedMult = 0;
			
	}
	
	public void letGo() {
		GameState.player.xSpeed = 10 *  (Math.cos(swingTimer));
		GameState.player.xAcc = 0;
		GameState.player.ySpeed = -20 *  Math.sin(swingTimer);
	}
	
	public void paint(Graphics2D g) {
		super.paint(g);
		int xUsable = (int) ((line.getX1() - Game.camera.getCameraPos().getX()) * Camera.scale);
		int yUsable = (int)((line.getY1() - Game.camera.getCameraPos().getY()) * Camera.scale);
		int xEndUsable = (int) ((line.getX2() - Game.camera.getCameraPos().getX()) * Camera.scale);
		int yEndUsable = (int)((line.getY2() - Game.camera.getCameraPos().getY()) * Camera.scale);
		Line2D lin = new Line2D.Float(xUsable, yUsable, xEndUsable, yEndUsable);
		BufferedImage image;
		try {
			image = ImageIO.read(new File("Assets/Rope.png"));
			BufferedImage rightPart = image.getSubimage(0, 0, 8, (int)length/2);
			
			BufferedImage finalImage = rotate(rightPart);
			Image scaledImage = finalImage.getScaledInstance((int)(16 * Camera.scale), (int)(length * Camera.scale), image.SCALE_DEFAULT);
			
			g.drawImage(scaledImage, xUsable, yUsable, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        g.draw(lin);
	}
	
	


	public BufferedImage rotate(BufferedImage image)
	{
	  /*
	   * Affline transform only works with perfect squares. The following
	   *   code is used to take any rectangle image and rotate it correctly.
	   *   To do this it chooses a center point that is half the greater
	   *   length and tricks the library to think the image is a perfect
	   *   square, then it does the rotation and tells the library where
	   *   to find the correct top left point. The special cases in each
	   *   orientation happen when the extra image that doesn't exist is
	   *   either on the left or on top of the image being rotated. In
	   *   both cases the point is adjusted by the difference in the
	   *   longer side and the shorter side to get the point at the 
	   *   correct top left corner of the image. NOTE: the x and y
	   *   axes also rotate with the image so where width > height
	   *   the adjustments always happen on the y axis and where
	   *   the height > width the adjustments happen on the x axis.
	   *   
	   */
	  AffineTransform xform = new AffineTransform();

	  if (image.getWidth() > image.getHeight())
	  {
	    xform.setToTranslation(0.5 * image.getWidth(), 0.5 * image.getWidth());
	    xform.rotate(0.87);

	    int diff = image.getWidth() - image.getHeight();

	    switch (50)
	    {
	    case 90:
	      xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth() + diff);
	      break;
	    case 180:
	      xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth() + diff);
	      break;
	    default:
	      xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth());
	      break;
	    }
	  }
	  else if (image.getHeight() > image.getWidth())
	  {
	    xform.setToTranslation(0.5 * image.getHeight(), 0.5 * image.getHeight());
	    xform.rotate(0.87);

	    int diff = image.getHeight() - image.getWidth();

	    switch (50)
	    {
	    case 180:
	      xform.translate(-0.5 * image.getHeight() + diff, -0.5 * image.getHeight());
	      break;
	    case 270:
	      xform.translate(-0.5 * image.getHeight() + diff, -0.5 * image.getHeight());
	      break;
	    default:
	      xform.translate(-0.5 * image.getHeight(), -0.5 * image.getHeight());
	      break;
	    }
	  }
	  else
	  {
	    xform.setToTranslation(0.5 * image.getWidth(), 0.5 * image.getHeight());
	    xform.rotate(0.87);
	    xform.translate(-0.5 * image.getHeight(), -0.5 * image.getWidth());
	  }

	  AffineTransformOp op = new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR);

	  BufferedImage newImage =new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
	  return op.filter(image, newImage);
	}
	
	
	public String toString() {
		return "Rope " + line.getX1() + " " + line.getY1() + " " + length;
	}
	
	public double getXAxis() {
		return line.getX1();
	}
	public double getYAxis() {
		return line.getY1();
	}
	

}
