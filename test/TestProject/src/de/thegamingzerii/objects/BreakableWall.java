package de.thegamingzerii.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.thegamingzerii.editor.Map;
import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.utility.Animation;
import de.thegamingzerii.utility.Constantes.ParticleType;

public class BreakableWall implements ICollision{

	private static BufferedImage wallImage;
	private static BufferedImage brokenImage;
	private static Image wall;
	private static Image broken;
	double x;
	double y;
	Line2D line1;
	Line2D line2;
	Line2D line3;
	boolean open;
	double breakingTimer = 0;
	
	public static ArrayList<BreakableWall> allBreakableWalls = new ArrayList<BreakableWall>();
	
	public BreakableWall(double x, double y, boolean open) {
		if(open)
			breakingTimer = -24;
		this.x = x;
		this.y = y;
		this.open = open;		
		line1 = new Line2D.Double(x+10, y + 256, x + 42, y + 240);
		line2 = new Line2D.Double(x+76, y + 240, x + 108, y + 256);
		line3 = new Line2D.Double(x+42, y+240, x+76, y+240);
		
		allBreakableWalls.add(this);
		//Map.reWriteMap();
	}
	
	
	public BreakableWall(BreakableWall wall) {
		this.x = wall.x;
		this.y = wall.y;
		this.open = wall.open;
		this.breakingTimer = wall.breakingTimer;
		this.line1 = wall.line1;
		this.line2 = wall.line2;
		this.line3 = wall.line3;
	}
	
	
	

	
	public void reset() {
		open = false;
	}
	
	
	public void destroy() {
		if(!open) {
			open = true;
			Map.reWriteMap();
			new Animation(x, y, "Assets/BreakWall.png", 3, 128, 256, 0);
			breakingTimer = Game.animationCounter;
		}		
	}


	
	public static void init() {
		try {
			BufferedImage image = ImageIO.read(new File("Assets/BreakWall.png"));
			brokenImage = image.getSubimage(1024, 0, 128, 256);
			wallImage = image.getSubimage(0, 0, 128, 256);
		} catch (IOException e) {
			e.printStackTrace();
		}
		reScale();
	}
	
	@SuppressWarnings("static-access")
	public static void reScale() {
		wall = wallImage.getScaledInstance((int)(128*Game.camera.scale), (int)(256*Game.camera.scale), wallImage.SCALE_DEFAULT);
		broken = brokenImage.getScaledInstance((int)(128*Game.camera.scale), (int)(256*Game.camera.scale), brokenImage.SCALE_DEFAULT);
	}
	
	
	public static void drawCurrentlyPlacing(Graphics2D g, double x, double y) {
		g.drawImage(wall, (int)x, (int)y, null);
	}
	
	public String toString() {
		return "BreakableWall " + x + " " + y + " " + open;
	}
	
	public void paint(Graphics2D g) {
		if(onScreen()) {
			int xUsable = (int) ((x - Game.camera.getX()) * Game.camera.scale);
			int yUsable = (int)((y - Game.camera.getY()) * Game.camera.scale);

			if(!open)
				g.drawImage(wall, xUsable, yUsable, null);
			else
				if(Game.animationCounter - breakingTimer > 24)
					g.drawImage(broken, xUsable, yUsable, null);
				else
					new Particle(ParticleType.Dust1, x + Math.random()*128, y + Math.random()*256, 50);

			
			if(Game.drawHitBoxes) {
				g.setColor(Color.ORANGE);
				if(!open)
					g.drawRect((int)(getCollisionSize().x- Game.camera.getCameraPos().getX() * Game.camera.scale), (int)(getCollisionSize().y - Game.camera.getCameraPos().getY() * Game.camera.scale), (int)(getCollisionSize().width * Game.camera.scale), (int)(getCollisionSize().height * Game.camera.scale));
				else {
					g.drawLine((int)(line1.getX1()- Game.camera.getCameraPos().getX() * Game.camera.scale), (int)(line1.getY1()- Game.camera.getCameraPos().getY() * Game.camera.scale), (int)(line1.getX2()- Game.camera.getCameraPos().getX() * Game.camera.scale), (int)(line1.getY2()- Game.camera.getCameraPos().getY() * Game.camera.scale));
					g.drawLine((int)(line2.getX1()- Game.camera.getCameraPos().getX() * Game.camera.scale), (int)(line2.getY1()- Game.camera.getCameraPos().getY() * Game.camera.scale), (int)(line2.getX2()- Game.camera.getCameraPos().getX() * Game.camera.scale), (int)(line2.getY2()- Game.camera.getCameraPos().getY() * Game.camera.scale));
					g.drawLine((int)(line3.getX1()- Game.camera.getCameraPos().getX() * Game.camera.scale), (int)(line3.getY1()- Game.camera.getCameraPos().getY() * Game.camera.scale), (int)(line3.getX2()- Game.camera.getCameraPos().getX() * Game.camera.scale), (int)(line3.getY2()- Game.camera.getCameraPos().getY() * Game.camera.scale));
				}
				g.setColor(Color.black);
			}
							
		}
	}

	@Override
	public boolean checkcollision(Rectangle rect) {
		if(open &&  (Game.animationCounter - breakingTimer > 24))
			if(line1.intersects(rect) || line2.intersects(rect) || line3.intersects(rect))
				return true;
			else
				return false;
		else
			if(rect.intersects(getCollisionSize()))
				return true;
			else
				return false;

	}

	@Override
	public double getXAxis() {
		return x;
	}

	@Override
	public double getYAxis() {
		return y;
	}

	@Override
	public void setX(int x) {
		this.x = x;
		
	}

	@Override
	public void setY(int y) {
		this.y = y;
		
	}

	@Override
	public Rectangle getCollisionSize() {
		return new Rectangle((int)x, (int)y, 128, 256);
	}
	
	@Override
	public boolean onScreen() {
		if(x > Game.actualCamera.getX() + Game.actualCamera.getWidth())
			return false;
		if(y > Game.actualCamera.getY() + Game.actualCamera.getHeight())
			return false;
		if(x + 128 < Game.actualCamera.getX())
			return false;
		if(y + 256 < Game.actualCamera.getY())
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
		return new BreakableWall(this);
	}
}
