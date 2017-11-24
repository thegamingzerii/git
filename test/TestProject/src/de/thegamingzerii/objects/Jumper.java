package de.thegamingzerii.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.states.GameState;

@SuppressWarnings("serial")
public class Jumper extends JPanel implements IInteract{

	public static ArrayList<Jumper> allJumpers =  new ArrayList<Jumper>();
	
	private double x = 0;
	private double y = 0;
	private double usedCounter = 0;
	String path = "Assets/Lantern.png";
	SpriteSheet sprite;
	
	
	public Jumper(double x, double y) {
		this.x = x;
		this.y = y;
		sprite =  new SpriteSheet(path, 32, 32, 8, 6, 4);
		allJumpers.add(this);
	}
	
	public Jumper(Jumper jumper) {
		this.x = jumper.x;
		this.y = jumper.y;
		sprite =  jumper.sprite;
	}
	
	
	public void update(double delta) {
		sprite.update(delta);
		usedCounter -= delta;
	}
	
	@Override
	public void interact(Boolean keyPressed) {
		if(usedCounter <= 0) {
			GameState.player.resetDoubleJump();
			GameState.player.canJumpTimer = 30;
			usedCounter = 60;
		}
		
		
	}

	@Override
	public boolean checkProximity(Rectangle rect) {
		if(onScreen()) {
			return Math.hypot(rect.getCenterX() - x, rect.getCenterY() - y) < 150;
		}
		return false;
		
		
		
	}
	
	
	public void paint(Graphics2D g) {
		if(onScreen()) {
			super.paintComponent(g);
			if(checkProximity(GameState.player.getCollisionSize()))
				sprite.paintComponent(g, x-64, y-64, 1, false);
			else
				sprite.paintComponent(g, x-64, y-64, 0, false);
			
			if(Game.drawHitBoxes) {
				int xUsable = (int) ((x -50 - Game.camera.getCameraPos().getX()) * Game.camera.scale);
				int yUsable = (int)((y -50 - Game.camera.getCameraPos().getY()) * Game.camera.scale);
				g.setColor(Color.ORANGE);
				g.drawOval((int)xUsable, (int)yUsable, 100, 100);
				g.setColor(Color.black);
			}
				
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
	
	@Override
	public void buffer() {
		if(onScreen())
			Game.currentBuffer.add(this.getCopy());
	}

	@Override
	public IBufferable getCopy() {
		return new Jumper(this);
	}
	

}
