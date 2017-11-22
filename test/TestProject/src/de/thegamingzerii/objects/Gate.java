package de.thegamingzerii.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.thegamingzerii.editor.Map;
import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.utility.ExtraMaths;

public class Gate implements ICollision{

	private static BufferedImage gateImage;
	private static BufferedImage frameImage;
	private static Image gate;
	private static Image frame;
	private double x;
	private double y;
	private boolean open;
	private Rectangle doorRect;
	private Rectangle upperRect;
	private Rectangle lowerRect;
	private double doorY;
	private double openingTimer = -1;
	
	public static ArrayList<Gate> allGates = new ArrayList<Gate>();
	
	public Gate(double x, double y, boolean open) {
		this.x = x;
		this.y = y;
		this.open = open;		
		
		
		upperRect = new Rectangle((int)x, (int)y, 64, 8);
		lowerRect = new Rectangle((int)x, (int)y + 248, 64, 8);
		doorRect = new Rectangle((int)x + 12,(int)y + 4, 40, 248);
		doorY = y;
		
		if(open) {
			openingTimer = 0;
			doorY = y + 244;
			doorRect = new Rectangle((int)x + 12,(int)y + 248, 40, 248);
		}
		
		allGates.add(this);
	}
	
	public void update(double delta) {
		if(openingTimer > 0) {
			openingTimer -= delta;
			if(openingTimer <= 0) {
				openingTimer = 0;
				open = true;
				Map.reWriteMap();
			}
			doorY = doorY + delta;
			doorRect.y = (int)doorY + 4;
		}
			
	}
	
	public void open() {
		openingTimer = 244;
	}
	
	public static void init() {
		try {
			gateImage = ImageIO.read(new File("Assets/Gate.png"));
			frameImage = ImageIO.read(new File("Assets/GateFrame.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reScale();
	}
	
	public static void reScale() {
		gate = gateImage.getScaledInstance((int)(64*Camera.scale), (int)(256*Camera.scale), gateImage.SCALE_DEFAULT);
		frame = frameImage.getScaledInstance((int)(64*Camera.scale), (int)(256*Camera.scale), frameImage.SCALE_DEFAULT);
	}
	
	public boolean onScreen() {
		return true;
	}
	
	public static void drawCurrentlyPlacing(Graphics2D g, double x, double y) {
		g.drawImage(gate, (int)x, (int)y, null);
		g.drawImage(frame, (int)x, (int)y, null);
	}
	
	public String toString() {
		return "Gate " + x + " " + y + " " + open;
	}
	
	public void paint(Graphics2D g) {
		if(onScreen()) {
			int xUsable = (int) ((x - Game.camera.getX()) * Camera.scale);
			int yUsable = (int)((y - Game.camera.getY()) * Camera.scale);

			g.drawImage(frame, xUsable, yUsable, null);
			if(openingTimer != -1)
				g.drawImage(gate, xUsable, (int)(yUsable  + (244 - openingTimer) * Camera.scale), null);
			else
				g.drawImage(gate, xUsable, yUsable, null);
			
			if(Game.drawHitBoxes) {
				g.setColor(Color.ORANGE);
				g.drawRect((int)(upperRect.x- Game.camera.getCameraPos().getX() * Camera.scale), (int)(upperRect.y - Game.camera.getCameraPos().getY() * Camera.scale), (int)(upperRect.width * Camera.scale), (int)(upperRect.height * Camera.scale));
				g.drawRect((int)(lowerRect.x- Game.camera.getCameraPos().getX() * Camera.scale), (int)(lowerRect.y - Game.camera.getCameraPos().getY() * Camera.scale), (int)(lowerRect.width * Camera.scale), (int)(lowerRect.height * Camera.scale));
				g.drawRect((int)(doorRect.x- Game.camera.getCameraPos().getX() * Camera.scale), (int)(doorRect.y - Game.camera.getCameraPos().getY() * Camera.scale), (int)(doorRect.width * Camera.scale), (int)(doorRect.height * Camera.scale));
				g.setColor(Color.black);
			}
				
		}
	}

	@Override
	public boolean checkcollision(Rectangle rect) {
		if(rect.intersects(upperRect))
			return true;
		if(rect.intersects(lowerRect))
			return true;
		if(rect.intersects(doorRect))
			return true;
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
		return null;
	}
	
}
