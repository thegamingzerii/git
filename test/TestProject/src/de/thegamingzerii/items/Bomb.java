package de.thegamingzerii.items;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import de.thegamingzerii.logicParts.Gate;
import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.BreakableWall;
import de.thegamingzerii.objects.Camera;
import de.thegamingzerii.objects.GravityObject;
import de.thegamingzerii.objects.IBufferable;
import de.thegamingzerii.utility.Animation;
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
	
	public Bomb(Bomb bomb) {
		super(20, 20);
		this.x = bomb.x;
		this.y = bomb.y;
	}
	
	
	public void update(double delta) {	
		if(!exploded) {
			if(super.gravity(delta))
				if(xSpeed > 0)
					xSpeed = 0.2;
				else
					xSpeed = -1 * delta;
			explosionTimer -= delta;
			if(explosionTimer <= 0)
				explode();
			
			
			x += xSpeed * 5 * delta;
			
			if(CollisionChecker.CheckAllCollisions(this)) {
				x -= xSpeed * 5 * delta;
				explode();
			}
				
		}
		
	}
	
	private void explode() {
		exploded = true;
		new Animation(x-64, y-64, "Assets/Explosion.png", 5, 128, 128, 0);
		for(int i = 0; i < BreakableWall.allBreakableWalls.size(); i++)
			if(BreakableWall.allBreakableWalls.get(i).checkcollision(new Rectangle((int)(x-64), (int)(y-64), 128, 128)))
				BreakableWall.allBreakableWalls.get(i).destroy();
		allBombs.remove(this);
	}
	
	public void paint(Graphics2D g) {
		if(!exploded) {
			int xUsable = (int) ((x - Game.camera.getCameraPos().getX()) * Game.camera.scale);
			int yUsable = (int)((y - Game.camera.getCameraPos().getY()) * Game.camera.scale);
			g.fillOval(xUsable, yUsable, (int)(width * Game.camera.scale), (int)(height*Game.camera.scale));
		}
		
	}
	
	public boolean onScreen() {
		return true;
	}
	
	@Override
	public void buffer() {
		if(onScreen())
			Game.currentBuffer.add(this.getCopy());
	}

	@Override
	public IBufferable getCopy() {
		return new Bomb(this);
	}
	

}
