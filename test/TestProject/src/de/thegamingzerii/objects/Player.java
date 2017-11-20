package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.text.Position;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.states.EditingState;
import de.thegamingzerii.states.GameState;
import de.thegamingzerii.utility.CollisionChecker;
import de.thegamingzerii.utility.Constantes;
import de.thegamingzerii.utility.Vector2d;


@SuppressWarnings("serial")
public class Player extends GravityObject implements ICollision{
	
	public boolean moveRight = false;
	public boolean moveLeft = false;
	boolean slidingLeft = false;
	boolean slidingRight =  false;
	boolean inAir = false;
	boolean inJump = false;
	boolean inDoubleJump = false;
	public boolean jumpPressed = false;
	boolean doubleJumpAvailable = true;
	public boolean hanging = false;
	private boolean overSlope = false;
	public Rope rope = null;
	public double jumpTimer = 0;
	String path = "Assets/Player.png";
	SpriteSheet sprite;
	int moveDirection = 0;
	public Point2D.Double checkPoint = new Point2D.Double(0, 0);

	public Player(double width, double height) {
		super(width, height);
		sprite =  new SpriteSheet(path, 32, 36, 8, 4, height/32);
	}
	
	public void changePos(double x, double y) {
		if(x > this.x) {
			moveRight = true;
			moveLeft = false;
			moveDirection = 0;
		}else {
			moveRight = false;
			moveLeft = true;
			moveDirection = 1;
		}
		this.x = x;
		this.y = y;
	}

	
	public void update(double delta) {
		sprite.update(delta);
		
		if(!hanging) {
			move(delta);
		}else {
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
						inDoubleJump = true;
						doubleJumpAvailable = false;
						jumpPressed = true;
						jumpTimer = 20;
					}else {
						if(!inAir || hanging) {
							jumpPressed = true;
							jumpTimer = 20;
						}else {
							jumpPressed = false;
						}
					}
					
					
				}
			}
		
		
		}
		if(hanging) {
			hanging = false;
			rope.interactionBlocked(30);
			rope.letGo();
			doubleJumpAvailable = true;
		}
	}
	
	
	public void gravity(double delta) {
		super.gravity(delta);
		if((slidingLeft || slidingRight) && ySpeed > Constantes.SLIDING_VELO) {
			ySpeed = Constantes.SLIDING_VELO;
			if(slidingLeft) {
				if(Math.random() < 0.1*delta) {
					new Particle("Assets/Particle.png", x, y-28, 16, 16, 60);
				}
				if(Math.random() < 0.1*delta) {
					new Particle("Assets/Particle.png", x, y+100, 16, 16, 60);
				}
			}else {
				if(Math.random() < 0.1*delta) {
					new Particle("Assets/Particle.png", x+85, y-28, 16, 16, 60);
				}
				if(Math.random() < 0.1*delta) {
					new Particle("Assets/Particle.png", x+85, y+100, 16, 16, 60);
				}
			}
			
		}
	}
	
	
	public void move(double delta) {
		
		
		
		gravity(delta);
		if(slidingLeft || slidingRight) {
			doubleJumpAvailable = true;
			inDoubleJump = false;
			
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
		
		xSpeed += xAcc * delta;
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
			inDoubleJump = false;
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
			damage(1);
		}
		
	
		while(CollisionChecker.CheckAllCollisions(this)) {
			y -= 1;
		}
		
		
		if(!inAir)
			checkPoint = new Point2D.Double(x, y);
		
		 y += 50;
		 overSlope = false;
		for(int i = 0; i < Slope.allSlopes.size(); i++) {
			if(Slope.allSlopes.get(i).checkcollision(getCollisionSize())) {
				resetDoubleJump();
				inDoubleJump = false;
				overSlope = true;
			}
				
		}
		y-= 50;
		
	}
	
	
	public void recieveMovement(boolean direction, boolean released) {
		if(hanging)
			rope.recieveMovement(direction, released);
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

	
	
	public void damage(int damage) {
		x = checkPoint.x;
		y = checkPoint.y;
		xSpeed = 0;
		ySpeed = 0;
		xAcc = 0;
		yAcc = 0;
	}
	


	public void paint(Graphics2D g) {
		super.paint(g);
		if(slidingLeft) {
			sprite.paintComponent(g, x-20, y-8, 3, true);
		}else {
			if(slidingRight) {
				sprite.paintComponent(g, x-12, y-8, 3, false);
			}else {
				if(inDoubleJump && !overSlope) {
					if(moveDirection == 0)
						sprite.paintComponent(g, x-16, y-8, 2, false);
					if(moveDirection == 1)
						sprite.paintComponent(g, x-16, y-8, 2, true);
				}else {
					if(inJump && !overSlope) {
						if(moveDirection == 0)
							sprite.paintComponent(g, x-16, y-8, 4, false);
						if(moveDirection == 1)
							sprite.paintComponent(g, x-16, y-8, 4, true);
					}else {
						if(moveRight) {
							sprite.paintComponent(g, x-16, y-8, 1, false);
						}else {
							if(moveLeft) 
								sprite.paintComponent(g, x-16, y-8, 1, true);
							else
								if(moveDirection == 0) {
									sprite.paintComponent(g, x-16, y-8, 0, false);
								}else {
									sprite.paintComponent(g, x-16, y-8, 0, true);
								}
								
						}
					}
				}
				
			}
		}
	}
}
