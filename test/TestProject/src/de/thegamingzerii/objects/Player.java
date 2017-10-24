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
import de.thegamingzerii.utility.Constantes;


@SuppressWarnings("serial")
public class Player extends GravityObject implements ICollision{
	
	boolean moveRight = false;
	boolean moveLeft = false;
	boolean slidingLeft = false;
	boolean slidingRight =  false;
	boolean inAir = false;
	boolean inJump = false;
	public boolean jumpPressed = false;
	boolean doubleJumpAvailable = true;
	public double jumpTimer = 0;
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
	
	
	public void jump() {
		if(!jumpPressed && (!inAir || doubleJumpAvailable || slidingLeft || slidingRight)) {
			if(slidingLeft) {
				xSpeed = 8;
				jumpPressed = true;
				jumpTimer = 20;
			}else {
				if(slidingRight) {
					xSpeed = -8;
					jumpPressed = true;
					jumpTimer = 20;
				}else {
					if(inAir && jumpTimer <= 0) {
						doubleJumpAvailable = false;
						jumpPressed = true;
						jumpTimer = 20;
					}else {
						if(!inAir) {
							jumpPressed = true;
							jumpTimer = 20;
						}else {
							jumpPressed = false;
						}
					}
					
					
				}
			}
		
		
		}
	}
	
	
	public void gravity(double delta) {
		super.gravity(delta);
		if((slidingLeft || slidingRight) && ySpeed > Constantes.SLIDING_VELO) {
			ySpeed = Constantes.SLIDING_VELO;
			if(slidingLeft) {
				if(Math.random() < 0.1) {
					new Particle("Assets/Particle.png", x, y-28, 16, 16, 60);
				}
				if(Math.random() < 0.1) {
					new Particle("Assets/Particle.png", x, y+100, 16, 16, 60);
				}
			}else {
				if(Math.random() < 0.1) {
					new Particle("Assets/Particle.png", x+85, y-28, 16, 16, 60);
				}
				if(Math.random() < 0.1) {
					new Particle("Assets/Particle.png", x+85, y+100, 16, 16, 60);
				}
			}
			
		}
	}
	
	
	public void move(double delta) {
		gravity(delta);
		if(slidingLeft || slidingRight) {
			doubleJumpAvailable = true;
			
		}
		
		slidingLeft = false;
		slidingRight = false;
		
		
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
					moveDirection = 0;
					if(inAir) {
						if(xAcc < 0.25) {
							xAcc += 0.2*delta;
						}
						
					}else {
						xAcc += 0.2*delta;
					}
					
				}
			
			if(moveLeft)
				if(xAcc > -0.5) {
					moveDirection = 1;
					if(inAir) {
						if(xAcc > -0.25) {
							xAcc -= 0.2*delta;
						}
					}else {
						xAcc -= 0.2*delta;
					}
					
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
				
				if(CollisionChecker.CheckAllCollisions(this) || inAir) {
					y+= yMod;
				}else {
					finished = true;
				}
			}
			if(!finished) {
				x-= xSpeed * delta;
				if(inAir) {
					if(xSpeed < 0)
						slidingLeft = true;
					else
						slidingRight = true;
				}
			}
				
			
			
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
			
			
		}else {
			inJump = false;
		}
		
		if(y > 2000) {
			y = 0;
			x = 0;
		}
		
	
		while(CollisionChecker.CheckAllCollisions(this)) {
			y -= 1;
		}
		
	}
	
	
	public void recieveMovement(boolean direction, boolean released) {
		if(released) {
			if(direction)
				moveLeft = false;
			else
				moveRight = false;
		}else {
			if(direction)
				moveLeft = true;
			else
				moveRight = true;
		}
	}

	


	public void paint(Graphics2D g) {
		super.paint(g);
		if(slidingLeft) {
			sprite.paint(g, x-20, y-8, moveDirection + 6);
		}else {
			if(slidingRight) {
				sprite.paint(g, x-12, y-8, moveDirection + 6);
			}else {
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
	}
}
