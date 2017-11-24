package de.thegamingzerii.items;

import java.awt.Graphics2D;
import java.util.ArrayList;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.Camera;
import de.thegamingzerii.objects.GravityObject;
import de.thegamingzerii.utility.CollisionChecker;

@SuppressWarnings("serial")
public class Bomb extends GravityObject{

	private double explosionTimer = 30;
	private boolean exploded = false;
	
	public static ArrayList<Bomb> allBombs = new ArrayList<Bomb>();
	
	public Bomb(double x, double y, double xSpeed) {
		super(20, 20);
		this.x = x;
		this.y = y;
		this.ySpeed = -10;
		this.xSpeed = xSpeed;
		allBombs.add(this);
	}
	
	
	public void update(double delta) {		
		if(!exploded) {
			if(super.gravity(delta))
				if(xSpeed > 0)
					xSpeed = 0.2;
				else
					xSpeed = -0.2;
			explosionTimer -= delta;
			if(explosionTimer <= 0)
				explode();
			
			
			x += xSpeed;
			
			if(CollisionChecker.CheckAllCollisions(this))
				x -= xSpeed;
		}
		
	}
	
	private void explode() {
		exploded = true;
		allBombs.remove(this);
	}
	
	public void paint(Graphics2D g) {
		if(!exploded) {
			int xUsable = (int) ((x - Game.camera.getCameraPos().getX()) * Camera.scale);
			int yUsable = (int)((y - Game.camera.getCameraPos().getY()) * Camera.scale);
			g.fillOval(xUsable, yUsable, (int)(width * Camera.scale), (int)(height*Camera.scale));
		}
		
	}
	

}
