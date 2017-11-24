package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.thegamingzerii.editor.Map;
import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.utility.Constantes.BackgroundType;

public class BackgroundObject implements IBufferable{
	
	double x;
	double y;
	BackgroundType type;
	public static ArrayList<BackgroundObject> allBackgroundObjects = new ArrayList<BackgroundObject>();
	
	private static ArrayList<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
	private static ArrayList<Image> images = new ArrayList<Image>();
	
	public BackgroundObject(double x, double y, BackgroundType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		allBackgroundObjects.add(this);
	}
	
	public BackgroundObject(BackgroundObject bgo) {
		this.x = bgo.x;
		this.y = bgo.y;
		this.type = bgo.type;
	}
	
	
	public static void init() {
		try {
			bufferedImages.add(ImageIO.read(new File("Assets/Tree.png")));
			bufferedImages.add(ImageIO.read(new File("Assets/Bush.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		reScale();
		
	}
	
	@SuppressWarnings("static-access")
	public static void reScale() {
		images.clear();
		for(int i = 0; i < bufferedImages.size(); i++) {
			images.add(bufferedImages.get(i).getScaledInstance((int)(512 * Game.camera.scale), (int)(1024 * Game.camera.scale), bufferedImages.get(i).SCALE_DEFAULT));
		}
	}
	
	public int getId() {
		switch(type) {
		case Tree1:
			return 0;
		case Bush1:
			return 1;
		default:
			return -1;
			
		}
	}
	
	public void paint(Graphics2D g) {
		int xUsable = (int) ((x - Game.camera.getX()) * Game.camera.scale);
		int yUsable = (int)((y - Game.camera.getY()) * Game.camera.scale);
		switch(type) {
		case Tree1:
			g.drawImage(images.get(0), xUsable, yUsable, Game.currentGame);
			break;
		case Bush1:
			g.drawImage(images.get(1), xUsable, yUsable, Game.currentGame);
			break;
			
		}
	}
	
	public static boolean deletBackgroundObject(double x, double y, int id) {
		boolean returnValue = false;
		for(int i = allBackgroundObjects.size()-1; i >= 0; i--) {
			if(allBackgroundObjects.get(i).getX() == x && allBackgroundObjects.get(i).getY() == y) {
				if(allBackgroundObjects.get(i).getId() == id) {
					returnValue = true;
					allBackgroundObjects.remove(allBackgroundObjects.get(i));
					Map.reWriteMap();
				}
			}
				
				
		}
		return returnValue;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public String toString() {
		int id = 0;
		switch(type) {
		case Tree1:
			id = 0;
			break;
		case Bush1:
			id = 1;
			break;
			
		}
		return "BackgroundObject " + x + " " + y + " " + id;
	}
	
	
	public static void drawCurrentlyPlacing(Graphics2D g, int id, int x, int y) {
		
		g.drawImage(images.get(id), x, y, Game.currentGame);
	
}


	@Override
	public boolean onScreen() {
		if(x > Game.actualCamera.getX() + Game.actualCamera.getWidth())
			return false;
		if(y > Game.actualCamera.getY() + Game.actualCamera.getHeight())
			return false;
		if(x + 512 < Game.actualCamera.getX())
			return false;
		if(y + 1024 < Game.actualCamera.getY())
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
		return new BackgroundObject(this);
	}
	
	
}
