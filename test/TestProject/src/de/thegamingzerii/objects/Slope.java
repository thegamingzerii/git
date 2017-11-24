package de.thegamingzerii.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.utility.ExtraMaths;
import de.thegamingzerii.utility.Vector2d;

@SuppressWarnings("serial")
public class Slope extends JPanel implements ICollision{
	public static ArrayList<Slope> allSlopes = new ArrayList<Slope>();
	private Line2D line;
	private Vector2d vector;
	
	public Slope(Point2D point1, Point2D point2) {
		line = new Line2D.Double(point1, point2);
		vector = new Vector2d(point1, point2);
		allSlopes.add(this);
	}
	

	@Override
	public boolean checkcollision(Rectangle rect) {
		if(onScreen())
			return ((line.intersects(new Rectangle((int)rect.getX(), (int)rect.getY(), (int)(rect.getWidth()/2), (int)rect.getHeight())))&&(line.intersects(new Rectangle((int)rect.getCenterX(), (int)rect.getY(), (int)(rect.getWidth()/2), (int)rect.getHeight()))));
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

	public Vector2d getVector() {
		return vector;
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
		if(ExtraMaths.onScreen(line.getP1()))
			return true;
		if(ExtraMaths.onScreen(line.getP2()))
			return true;
		return false;
					
	}
	
	public void paintComponent(Graphics2D g) {
		super.paint(g);
		if(Game.drawHitBoxes && onScreen()) {
			int xUsable = (int) ((line.getX1() - Game.camera.getCameraPos().getX()-4) * Camera.scale);
			int yUsable = (int)((line.getY1() - Game.camera.getCameraPos().getY()) * Camera.scale);
			int xEndUsable = (int) ((line.getX2() - Game.camera.getCameraPos().getX()-4) * Camera.scale);
			int yEndUsable = (int)((line.getY2() - Game.camera.getCameraPos().getY()) * Camera.scale);
			g.setColor(Color.ORANGE);
	        g.draw(new Line2D.Float(xUsable, yUsable, xEndUsable, yEndUsable));
	        g.setColor(Color.black);
		}
		
		
	}
	
	
	public String toString() {
		return "Slope " + line.getX1() + " " + line.getY1() + " " + line.getX2() + " " + line.getY2();
	}
	
	
	
	
	
	
	
	
	
	

}
