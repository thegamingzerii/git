package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.states.GameState;

public class Jumper extends JPanel implements IInteract{

	public static ArrayList<Jumper> allJumpers =  new ArrayList<Jumper>();
	
	private double x = 0;
	private double y = 0;
	private double r = 100;
	private double usedCounter = 0;
	String path = "Assets/Lantern.png";
	SpriteSheet sprite;
	
	
	public Jumper(double x, double y) {
		this.x = x;
		this.y = y;
		sprite =  new SpriteSheet(path, 32, 32, 8, 6, 4);
		allJumpers.add(this);
	}
	
	public void update(double delta) {
		sprite.update(delta);
		usedCounter -= delta;
	}
	
	@Override
	public void interact() {
		if(usedCounter <= 0) {
			GameState.player.resetDoubleJump();
			usedCounter = 60;
		}
		
		
	}

	@Override
	public boolean checkProximity(Rectangle rect) {
		if(onScreen()) {
			if(Math.abs(x - rect.x) > rect.getWidth() + 50 || Math.abs(y - rect.y) > rect.getHeight() + 50 ) {
				return false;
			}else {
				double circleDistanceX = Math.abs(x - rect.x);
			    double circleDistanceY = Math.abs(y - rect.y);

			    if (circleDistanceX > (rect.width/2 + r)) { return false; }
			    if (circleDistanceY > (rect.height/2 + r)) { return false; }

			    if (circleDistanceX <= (rect.width/2)) { return true; } 
			    if (circleDistanceY <= (rect.height/2)) { return true; }

			    double cornerDistance_sq = Math.pow(circleDistanceX - rect.width/2, 2) + Math.pow(circleDistanceY - rect.height/2, 2);

			    return (cornerDistance_sq <= (r*r));
			}
		}
		return false;
		
		
		
	}
	
	
	public void paint(Graphics2D g) {
		if(onScreen()) {
			super.paintComponent(g);
			sprite.paintComponent(g, x-64, y-64, 0, false);
		}
		
	}
	
	public static void paintAllJumpers(Graphics2D g) {
		for(int i = 0; i < allJumpers.size(); i++) {
			allJumpers.get(i).paint(g);
		}
	}
	
	public double getXAxis() {
		return x;
	}


	
	public double getYAxis() {
		return y;
	}
	
	public String toString() {
		return "Jumper " + x + " " + y;
	}

	@Override
	public boolean onScreen() {
		if(x-100 > Game.camera.getX() + Game.camera.getWidth())
			return false;
		if(y-100 > Game.camera.getY() + Game.camera.getHeight())
			return false;
		if(x+100 < Game.camera.getX())
			return false;
		if(y+100 < Game.camera.getY())
			return false;
		return true;
	}
	

}
