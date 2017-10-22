package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.states.EditingState;
import de.thegamingzerii.states.GameState;
import de.thegamingzerii.utility.CollisionChecker;


@SuppressWarnings("serial")
public class Player extends GravityObject implements ICollision{
	
	boolean moveRight = false;
	boolean moveLeft = false;
	boolean inAir = false;
	boolean inJump = false;
	boolean jumpPressed = false;
	boolean doubleJumpAvailable = true;
	double jumpTimer = 0;
	String path = "Assets/Player.png";
	SpriteSheet sprite;
	int moveDirection = 0;

	public Player(double width, double height) {
		super(width, height);
		sprite =  new SpriteSheet(path, 32, 36, 8, 4, height/32);
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
		
	
	public void resetDoubleJump() {
		doubleJumpAvailable = true;
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
		
		//checks if x direction movement is possible, also checks for stairs
		x += xSpeed * delta;
		if(CollisionChecker.CheckAllCollisions(this)) {
			int yMod = 1;
			boolean finished = false;
			//change yMod < XXXX for higher stairs to climb (in logic pixels)
			while(yMod < 50 && !finished) {
				yMod++;
				y -= yMod;
				
				if(CollisionChecker.CheckAllCollisions(this)) {
					y+= yMod;
				}else {
					finished = true;
				}
			}
			if(!finished)
				x-= xSpeed * delta;
			
			
		}

		
		
		y += 1;
		if(CollisionChecker.CheckAllCollisions(this)) {
			inAir = false;
			doubleJumpAvailable = true;
		}else {
			inAir =  true;
		}
		y -= 1;
		
		
		if(inAir) {
			y += 20;
			if(CollisionChecker.CheckAllCollisions(this)) {
				inJump = false;
			}else {
				inJump =  true;
			}
			y -= 20;
			
			
		}
		
		if(y > 2000) {
			y = 0;
			x = 0;
		}
		
	
	}
	
	public void keyReleased(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A))
			moveLeft = false;
		if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)) {
			moveRight = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jumpPressed = false;
			jumpTimer = 0;
		}
			
	}

	public void keyPressed(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && !moveLeft)
			moveLeft = true;
		if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && !moveRight)
			moveRight = true;
		if (e.getKeyCode() == KeyEvent.VK_SPACE && (!inAir || doubleJumpAvailable)) {
			if(inAir && jumpTimer <= 0) {
				doubleJumpAvailable = false;
			}
			jumpPressed = true;
			jumpTimer = 20;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_E) {
			if(Game.currentState != Game.editingState) {
				Game.setCurrentState(Game.editingState);
				EditingState.editing = true;
			}
			else {
				Game.setCurrentState(Game.ingameState);
				EditingState.editing = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_T) {
			EditingState.mode = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_R) {
			EditingState.mode = 1;
		}
			
			
	}
	


	public void paint(Graphics2D g) {
		super.paint(g);
		if(inJump) {
			if(moveDirection == 0)
				sprite.paint(g, x-16, y-8, 5);
			if(moveDirection == 1)
				sprite.paint(g, x-16, y-8, 4);
		}else {
			if(moveRight) {
				sprite.paint(g, x-16, y-8, moveDirection+2);
			}else {
				if(moveLeft) 
					sprite.paint(g, x-16, y-8, moveDirection+2);
				else
					sprite.paint(g, x-16, y-8, moveDirection);
			}
		}
		
		
	
	}


	

	
}
