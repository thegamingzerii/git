package de.thegamingzerii.logicParts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.Camera;
import de.thegamingzerii.objects.IInteract;

public class Lever extends LogicTile implements IInteract{

	public static ArrayList<Lever> allLevers = new ArrayList<Lever>();
	private static BufferedImage leverImage;
	private static Image lever;
	private static BufferedImage leverImageFlipped;
	private static Image leverFlipped;
	
	private double x;
	private double y;
	private boolean flipped;
	
	public Lever(double x, double y, boolean flipped, int id, int connectedTo) {
		super(id, connectedTo);
		this.x = x;
		this.y = y;
		this.flipped = flipped;
		allLevers.add(this);
		//Map.reWriteMap();
	}
	
	public static void init() {
		try {
			leverImage = ImageIO.read(new File("Assets/Lever.png"));
			leverImageFlipped = leverImage.getSubimage(0, 128, 128, 128);
			leverImage = leverImage.getSubimage(0, 0, 128, 128);
		} catch (IOException e) {
			e.printStackTrace();
		}
		reScale();
	}
	
	@SuppressWarnings("static-access")
	public static void reScale() {
		lever = leverImage.getScaledInstance((int)(128*Camera.scale), (int)(128*Camera.scale), leverImage.SCALE_DEFAULT);
		leverFlipped = leverImageFlipped.getScaledInstance((int)(128*Camera.scale), (int)(128*Camera.scale), leverImage.SCALE_DEFAULT);
	}	
	
	public void paint(Graphics2D g) {
		int xUsable = (int) ((x - 64 - Game.camera.getCameraPos().getX()) * Camera.scale);
		int yUsable = (int)((y - 64 - Game.camera.getCameraPos().getY()) * Camera.scale);
		if(flipped) 
			g.drawImage(lever, xUsable, yUsable, null);
		else
			g.drawImage(leverFlipped, xUsable, yUsable, null);
		
		if(Game.drawHitBoxes) {
			xUsable = (int) ((x - 5 - Game.camera.getCameraPos().getX()) * Camera.scale);
			yUsable = (int)((y - 5 - Game.camera.getCameraPos().getY()) * Camera.scale);
			g.setColor(Color.ORANGE);
			g.fillOval(xUsable, yUsable, 10, 10);
			g.setColor(Color.black);
		}
			
		
		super.paint(g);
	}
	
	
	@Override
	public void interact(Boolean keyPressed) {
		if(keyPressed) {
			if(flipped)
				super.activate(false);
			else
				super.activate(true);
			flipped = !flipped;
		}
			
		
	}
	
	public String toString() {
		return "Lever " + x + " " + y + " " + flipped + " " + getId() + " " + getConnectedTo();
	}
	
	public void reset() {
		flipped = false;
	}
	
	@Override
	public Point2D getLogicWirePoint() {
		return new Point2D.Double(x, y+50);
	}
	
	public boolean connectsTo(Point2D point) {
		return Math.hypot(point.getX() - x, point.getY() - y) < 200;
	}

	
	@Override
	public boolean checkProximity(Rectangle rect) {
		return rect.contains(x, y);
	}

	@Override
	public boolean onScreen() {
		return true;
	}
	
	public static void drawCurrentlyPlacing(Graphics2D g, int x, int y) {
		int xUsable = x - (int) ( 10  * Camera.scale);
		int yUsable = y - (int)( 10  * Camera.scale);
		g.drawRect(xUsable, yUsable, (int)(20*Camera.scale), (int)(20*Camera.scale));
	}

}
