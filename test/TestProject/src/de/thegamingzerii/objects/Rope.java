package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

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
			xSpeedMult = Math.abs(GameState.player.xSpeed / 10);
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
		double x2 = originalX + Math.sin(swingTimer) * this.length*0.9 * xSpeedMult;
		double y1 = line.getY1();
		double length = this.length;
		double y2 = Math.sqrt(Math.pow(length, 2) - Math.pow(x2 - x1, 2) ) + y1;
		line.setLine(x1, y1, x2, y2);
		if(swinging)
			
		
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
		System.out.println(10 *  (Math.cos(swingTimer)));
		GameState.player.ySpeed = -20 *  Math.sin(swingTimer);
	}
	
	public void paint(Graphics2D g) {
		super.paint(g);
		int xUsable = (int) ((line.getX1() - Game.camera.getCameraPos().getX()) * Camera.scale);
		int yUsable = (int)((line.getY1() - Game.camera.getCameraPos().getY()) * Camera.scale);
		int xEndUsable = (int) ((line.getX2() - Game.camera.getCameraPos().getX()) * Camera.scale);
		int yEndUsable = (int)((line.getY2() - Game.camera.getCameraPos().getY()) * Camera.scale);
		Line2D lin = new Line2D.Float(xUsable, yUsable, xEndUsable, yEndUsable);
        g.draw(lin);
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
