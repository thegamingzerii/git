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

import de.thegamingzerii.editor.Map;
import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.Camera;
import de.thegamingzerii.objects.ICollision;

public class Gate extends LogicTile implements ICollision{

	private static BufferedImage gateImage;
	private static BufferedImage frameImage;
	private static Image gate;
	private static Image frame;
	private double x;
	private double y;
	private boolean open;
	private boolean opening;
	private Rectangle doorRect;
	private Rectangle upperRect;
	private Rectangle lowerRect;
	private double doorY;
	private double openingTimer = -1;
	private double movementSpeed;
	
	public static ArrayList<Gate> allGates = new ArrayList<Gate>();
	
	public Gate(double x, double y, boolean open, int id, int connectedTo) {
		super(id, connectedTo);
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
		//Map.reWriteMap();
	}
	
	public void update(double delta) {
		
		movementSpeed = delta * 3;
		
		if(openingTimer >= 0) {
			if(opening) {
				openingTimer -= movementSpeed;
				doorY = doorY + movementSpeed;
				if(openingTimer <= 0) {
					openingTimer = 0;
					open = true;
					doorY = y + 244;
					Map.reWriteMap();
				}
			}else {
				openingTimer += movementSpeed;
				doorY = doorY - movementSpeed;
				if(openingTimer >= 244) {
					openingTimer = 244;
					open = false;
					doorY = y;
					Map.reWriteMap();
				}
			}
			
			
			doorRect.y = (int)doorY + 4;
		}
			
	}
	
	public void open() {
		if(openingTimer == -1)
			openingTimer = 244;
		opening = true;
	}
	
	public void close() {
		if(openingTimer == -1)
			openingTimer = 0;
		opening = false;
	}
	
	public void reset() {
		openingTimer = -1;
		open = false;
	}
	
	@Override
	public Point2D getLogicWirePoint() {
		return new Point2D.Double(x, y);
	}
	
	public boolean connectsTo(Point2D point) {
		Rectangle testRect = new Rectangle((int)x, (int)y, 128, 256);
		return testRect.contains(point);
	}
	
	public boolean Activateable() {
		return true;
	}
	
	public void activate(Boolean bool) {
		if(bool)
			open();
		else
			close();
		super.activate(bool);
	}
	
	public static void init() {
		try {
			gateImage = ImageIO.read(new File("Assets/Gate.png"));
			frameImage = ImageIO.read(new File("Assets/GateFrame.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		reScale();
	}
	
	@SuppressWarnings("static-access")
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
		Boolean bool = false;
		if(opening == open)
			bool = open;
		else
			bool = opening;
		return "Gate " + x + " " + y + " " + bool + " " + " " + getId() + " " + getConnectedTo();
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
			
			super.paint(g);
				
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
