package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.thegamingzerii.utility.CollisionChecker;


@SuppressWarnings("serial")
public class Player extends GravityObject implements ICollision{
	
	boolean moveRight = false;
	boolean moveLeft = false;
	boolean inAir = false;
	boolean jumpPressed = false;
	double jumpTimer = 0;
	String path = "Assets/Player.png";
	SpriteSheet sprite;
	int moveDirection = 0;

	public Player(double width, double height) {
		super(width, height);
		sprite =  new SpriteSheet(path, 32, 33, 30, 2, height/32);
	}
	
	

	
	public void update(double delta) {
		sprite.update(delta);
		
		move(delta);
		if(xSpeed >= 0) {
			path = "Assets/PlayerRight.png";
		}else {
			path = "Assets/PlayerLeft.png";
		}
		
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
			xSpeed -= xSpeed * 0.2 * delta;
			if(!(xSpeed > 0.2 || xSpeed < -0.2)) {
				xSpeed = 0;
			}
		}else {
			if(moveRight)
				if(xAcc < 0.5) {
					moveDirection = 0;
					xAcc += 0.25*delta;
				}
			
			if(moveLeft)
				if(xAcc > -0.5) {
					moveDirection = 1;
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
		sprite.paint(g, x-16, y+1, moveDirection);
	
	}


	

	
}
