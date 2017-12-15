package de.thegamingzerii.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.states.GameState;
import de.thegamingzerii.utility.Vector2d;

@SuppressWarnings("serial")
public class Rope extends JPanel implements IInteract{

	Line2D line;
	double length;
	double originalX;
	double blocked = 0;
	double xSpeedMult;
	boolean swinging = false;
	boolean moveLeft = false;
	boolean moveRight = false;
	double swingTimer = 0;
	public static ArrayList<Rope> allRopes = new ArrayList<Rope>();
	private static final int SIZE = 256;
	
	public Rope(double x, double y, double length) {
		line = new Line2D.Double(x, y, x, y + length);
		allRopes.add(this);
		this.length = length;
		this.originalX = x;
	}
	
	public Rope(Rope rope) {
		line = rope.line;
	}
	
	public void interactionBlocked(int time) {
	
		blocked = time;
	}
	
	@Override
	public void interact(Boolean keyPressed) {
		if(blocked <= 0 && !GameState.player.hanging) {
			GameState.player.rope = this;
			GameState.player.hanging = true;
			swinging = true;
			swingTimer = 0;
			xSpeedMult = Math.abs(GameState.player.xSpeed / 12);
			if(GameState.player.xSpeed < 0)
				swingTimer = Math.PI;
		}
		
		
	}

	@Override
	public boolean checkProximity(Rectangle rect) {
		return line.intersects(rect);
	}

	@Override
	public boolean onScreen() {
		return Game.actualCamera.getScreen().intersectsLine(line);
	}
	
	
	public void recieveMovement(boolean direction, boolean released) {
		if(released) {
			if(direction) {
				moveLeft = false;
			}
			else {
				moveRight = false;
			}
				
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
		double x2 = originalX + Math.sin(swingTimer) * this.length*0.7 * xSpeedMult;
		double y1 = line.getY1();
		double length = this.length;
		double y2 = Math.sqrt(Math.pow(length, 2) - Math.pow(x2 - x1, 2) ) + y1;
		line.setLine(x1, y1, x2, y2);
		if(swinging) {
			if(moveLeft && Math.sin(swingTimer) - Math.sin(swingTimer+0.1) > 0 && xSpeedMult <= 1) {
				xSpeedMult += xSpeedMult * 0.003;
			}
			if(moveRight && Math.sin(swingTimer) - Math.sin(swingTimer+0.1) < 0 && xSpeedMult <= 1) {
				xSpeedMult += xSpeedMult * 0.003;
			}
			if(moveLeft && Math.sin(swingTimer) - Math.sin(swingTimer+0.1) < 0) {
				xSpeedMult -= xSpeedMult * 0.01;
			}
			if(moveRight && Math.sin(swingTimer) - Math.sin(swingTimer+0.1) > 0) {
				xSpeedMult -= xSpeedMult * 0.01;
			}
		}
			
		
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
		/**
		GameState.player.xSpeed = 10 *  (Math.cos(swingTimer));
		GameState.player.xAcc = 0;
		GameState.player.ySpeed = -20 *  Math.sin(swingTimer);

		GameState.player.moveRight = moveRight;
		GameState.player.moveLeft = moveLeft;
		*/
		Vector2d vec = new Vector2d(line.getP1(), line.getP2());
		vec.normalize();
		Vector2d vec2 = vec.getNormal();
		GameState.player.xSpeed = vec2.getX() * 100;
		GameState.player.ySpeed = vec2.getY() * 100;
		GameState.player.moveRight = moveRight;
		GameState.player.moveLeft = moveLeft;
	}
	
	public void paint(Graphics2D g) {
		super.paint(g);
		int xUsable = (int) ((line.getX1() - Game.camera.getCameraPos().getX()-4) * Game.camera.scale);
		int yUsable = (int)((line.getY1() - Game.camera.getCameraPos().getY()) * Game.camera.scale);
		int xEndUsable = (int) ((line.getX2() - Game.camera.getCameraPos().getX()-4) * Game.camera.scale);
		int yEndUsable = (int)((line.getY2() - Game.camera.getCameraPos().getY()) * Game.camera.scale);
		
		BufferedImage image;
		Vector2d vec = new Vector2d(line.getP1(), line.getP2());
		Vector2d vec2 = vec.getNormal();
		Line2D ortVec = vec2.getLine(line.getP2());
		int xUsable2 = (int) ((ortVec.getX1() - Game.camera.getCameraPos().getX()-4) * Game.camera.scale);
		int yUsable2 = (int)((ortVec.getY1() - Game.camera.getCameraPos().getY()) * Game.camera.scale);
		int xEndUsable2 = (int) ((ortVec.getX2() - Game.camera.getCameraPos().getX()-4) * Game.camera.scale);
		int yEndUsable2 = (int)((ortVec.getY2() - Game.camera.getCameraPos().getY()) * Game.camera.scale);
		
		
		
		g.setStroke(new BasicStroke((float) (20*Game.camera.scale/Game.camera.zoom)));
		Color brown = new Color(59, 31, 6);
		g.setColor(brown);
        g.draw(new Line2D.Float(xUsable, yUsable, xEndUsable, yEndUsable));
        if(Game.drawHitBoxes)
        	g.draw(new Line2D.Float(xUsable2, yUsable2, xEndUsable2, yEndUsable2));
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1));
        
		try {
	        image = ImageIO.read(new File("Assets/Knot.png"));
	        @SuppressWarnings("static-access")
			Image scaledImage = image.getScaledInstance((int)(64/ Game.camera.zoom), (int)(64/ Game.camera.zoom), image.SCALE_DEFAULT);
	        g.drawImage(scaledImage, (int)(xUsable-32*Game.camera.scale), (int)(yUsable-32*Game.camera.scale), null);
	        g.drawImage(scaledImage, (int)(xEndUsable-32*Game.camera.scale), (int)(yEndUsable-10*Game.camera.scale), null);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

	
	

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SIZE, SIZE);
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

@Override
public void buffer() {
	if(onScreen())
		Game.currentBuffer.add(this.getCopy());
}

@Override
public IBufferable getCopy() {
	return new Rope(this);
}
}
