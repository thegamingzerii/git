package de.thegamingzerii.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.utility.ExtraMaths;
import de.thegamingzerii.utility.Vector2d;

public class Obstacle implements ICollision{
	public static ArrayList<Obstacle> allObstacles = new ArrayList<Obstacle>();
	Line2D line;
	
	public Obstacle(Point2D point1, Point2D point2) {
		line = new Line2D.Double(point1, point2);
		allObstacles.add(this);
	}
	
	public Obstacle(Obstacle obstacle) {
		line = obstacle.line;
	}
	

	@Override
	public boolean checkcollision(Rectangle rect) {
		if(onScreen())
			return line.intersects(rect);
		return false;

	}

	@Override
	public double getXAxis() {
		return line.getX1();
	}

	@Override
	public double getYAxis() {
		return line.getY1();
	}
	
	public double getX2() {
		return line.getX2();
	}
	
	public double getY2() {
		return line.getY2();
	}

	
	@Override
	public void setX(int x) {
		
	}

	@Override
	public void setY(int y) {
		
	}

	@Override
	public Rectangle getCollisionSize() {
		return null;
	}
	

	@Override
	public boolean onScreen() {
		return line.intersects(Game.actualCamera.getScreen());
					
	}
	
	public void paint(Graphics2D g) {
		if(Game.drawHitBoxes) {
			int xUsable = (int) ((line.getX1() - Game.camera.getCameraPos().getX()-4) * Game.camera.scale);
			int yUsable = (int)((line.getY1() - Game.camera.getCameraPos().getY()) * Game.camera.scale);
			int xEndUsable = (int) ((line.getX2() - Game.camera.getCameraPos().getX()-4) * Game.camera.scale);
			int yEndUsable = (int)((line.getY2() - Game.camera.getCameraPos().getY()) * Game.camera.scale);
			g.setColor(Color.ORANGE);
	        g.draw(new Line2D.Float(xUsable, yUsable, xEndUsable, yEndUsable));
	        g.setColor(Color.black);
		}
		
		
	}
	
	
	public String toString() {
		return "Obstacle " + line.getX1() + " " + line.getY1() + " " + line.getX2() + " " + line.getY2();
	}
	
	
	
	
	
	@Override
	public void buffer() {
		if(onScreen())
			Game.currentBuffer.add(this.getCopy());
	}

	@Override
	public IBufferable getCopy() {
		return new Obstacle(this);
	}
	
	
	
	
}
