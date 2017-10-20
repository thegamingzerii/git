package Objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.thegamingzerii.utility.CollisionChecker;


public class Player extends GravityObject implements ICollision{
	
	boolean moveRight = false;
	boolean moveLeft = false;
	boolean inAir = false;
	boolean jumpPressed = false;
	double jumpTimer = 0;

	public Player() {
	}
	
	

	
	public void update(double delta) {
		move(delta);
		
		
			
		
	}
		
	
	public void move(double delta) {
		super.gravity(delta);
		if(jumpTimer > -1) {
			jumpTimer -= delta;
		}
		
		if(jumpPressed && jumpTimer > 0) {
			ySpeed = - 10;
		}
		
		
		
		if(!moveRight && !moveLeft) {
			xAcc = 0;
			xSpeed -= xSpeed * 0.1 * delta;
			if(!(xSpeed > 0.2 || xSpeed < -0.2)) {
				xSpeed = 0;
			}
		}else {
			if(moveRight)
				if(xAcc < 0.5) {
					xAcc += 0.25*delta;
				}
			
			if(moveLeft)
				if(xAcc > -0.5) {
					xAcc -= 0.25*delta;
				}
		}
		
		xSpeed += xAcc;
		if(xSpeed > 10)
			xSpeed = 10;
		if(xSpeed < -10) {
			xSpeed = -10;
		}
		x += xSpeed * delta;
		if(CollisionChecker.CheckAllCollisions(this)) {
			x-= xSpeed * delta;
		}

		
		
		y += 1;
		if(CollisionChecker.CheckAllCollisions(this)) {
			inAir = false;
		}else {
			inAir =  true;
		}
		y -= 1;
		
		if(y > 2000) {
			y = 0;
			x = 0;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			moveLeft = false;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moveRight = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jumpPressed = false;
		}
			
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT && !moveLeft)
			moveLeft = true;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !moveRight)
			moveRight = true;
		if (e.getKeyCode() == KeyEvent.VK_SPACE && !inAir) {
			jumpPressed = true;
			jumpTimer = 20;
		}
			
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
