package de.thegamingzerii.objects;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

import de.thegamingzerii.maingame.Game;

public class Camera {

	private double x = 0;
	private double y = 0;
	private double screenHeight;
	private double screenWidth;
	private double zoomingTo = 0;
	private boolean zooming = false;
	public static double scale = 1;
	public static double zoom = 2;
	public static double time = 0;
	
	
	public Camera(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point2D getCameraPos() {
		return new Point2D.Double(x, y);
	}
	
	public double getWidth() {
		return screenWidth * zoom;
	}
	
	public double getHeight() {
		return screenHeight * zoom;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void moveCamera(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	public void update(double delta) {
		
		if(zooming) {
			zoom += (zoomingTo - zoom) * 0.05 * delta;
			if(Math.abs(zoomingTo - zoom) < 0.05)
				zoom = zoomingTo;
		}
			
		
		Rectangle screenSize = Game.frame.getBounds();
		screenHeight = screenSize.getHeight();
		screenWidth = screenSize.getWidth();
		
		
		
	    scale = (screenHeight / 1080) / zoom;
	}
	
	public void reFrame(double zoom) {
		zooming = true;
		zoomingTo = zoom;
	}
}
