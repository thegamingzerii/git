package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import de.thegamingzerii.logicParts.Gate;
import de.thegamingzerii.logicParts.Lever;
import de.thegamingzerii.maingame.Game;

public class Camera implements IBufferable{

	double x = 0;
	double y = 0;
	double screenHeight;
	double screenWidth;
	double zoomingTo = 0;
	boolean zooming = false;
	public double scale = 1;
	public double zoom = 1;
	public double time = 0;	
	public static boolean zoomEnded = false;
	
	
	public Camera(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Camera(Camera cam) {
		this.x = cam.x;
		this.y = cam.y;
		this.zooming = cam.zooming;
		this.zoomingTo = cam.zoomingTo;
		this.scale = cam.scale;
		this.zoom = cam.zoom;
		this.time = cam.time;
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
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void moveCamera(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	public void update(double delta) {
		
		if(zooming) {
			zoom += (zoomingTo - zoom) * 0.05 * delta;
			scale = (screenHeight / 1080) / zoom;
			if(Math.abs(zoomingTo - zoom) < 0.05) {
				zoom = zoomingTo;
				scale = (screenHeight / 1080) / zoom;
				zooming = false;
				zoomEnded = true;
			}
				
		}
			
		
		Rectangle screenSize = Game.frame.getBounds();
		screenHeight = screenSize.getHeight();
		screenWidth = screenSize.getWidth();
		
		
		
	    
	    	
	}
	
	public void reFrame(double zoom) {
		zooming = true;
		zoomingTo = zoom;
	}
	
	public Rectangle getScreen() {
		return new Rectangle((int)x, (int)y, (int)(screenWidth * zoom), (int)(screenHeight * zoom));
	}

	@Override
	public boolean onScreen() {
		return false;
	}

	@Override
	public void buffer() {
		Game.currentBuffer.add(this.getCopy());
	}

	@Override
	public IBufferable getCopy() {
		return new Camera(this);
	}
	
	public void paint(Graphics2D g) {
		if(zooming) {
			TextureBlock.reScale();
			Gate.reScale();
			Lever.reScale();
			BackgroundObject.reScale();
			BreakableWall.reScale();
			Particle.reScale();
			
				
		}
		if(zoomEnded) {

			zoom = Game.actualCamera.zoom;
			scale = Game.actualCamera.scale;
			TextureBlock.reScale();
			BackgroundObject.reScale();
			Gate.reScale();			
			Lever.reScale();
			BreakableWall.reScale();
			Particle.reScale();
			zoomEnded = false;
		}
	}
}
