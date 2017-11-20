package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import de.thegamingzerii.editor.Map;
import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.utility.Constantes.BlockType;
import de.thegamingzerii.utility.ExtraMaths;

public class TextureBlock {
	public static ArrayList<TextureBlock> allTextureBlocks = new ArrayList<TextureBlock>();
	public static ArrayList<Point> ground = new ArrayList<Point>();
	public static ArrayList<Point> ground2 = new ArrayList<Point>();
	public static ArrayList<Point> slope1 = new ArrayList<Point>();
	public static ArrayList<Point> slope2 = new ArrayList<Point>();
	public static ArrayList<Point> slope3 = new ArrayList<Point>();
	
	private double x;
	private double y;
	private BlockType type;
	private SpriteSheet sprite;
	private double randomFactor;
	Random generator;
	
	public TextureBlock(double x, double y, BlockType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		
		String path = "";
		switch(type) {
		case Ground:
			path = "Assets/Ground.png";
			break;
		case Ground2:
			path = "Assets/Ground.png";
			break;
		case Slope1:
			path = "Assets/Ground.png";
			break;
		case Slope2:
			path = "Assets/Ground.png";
			break;
		case Slope3:
			path = "Assets/Ground.png";
			break;
		case Grass:
			path = "Assets/Grass.png";
			break;
		default:
			break;
		}
		sprite = new SpriteSheet(path, 128,128, 0, 1, 1);
		generator = new Random((long) (x*y));
		randomFactor = Math.round(generator.nextInt()/100);
		
		
		
		
		allTextureBlocks.add(this);
	}

	
	public void draw(Graphics2D g) {
		if(onScreen()) {
			switch(type) {
			case Ground:
				ground.add(new Point((int)x, (int)y));
				break;
			case Ground2:
				ground2.add(new Point((int)x, (int)y));
				break;
			case Slope1:
				slope1.add(new Point((int)x, (int)y));
				break;
			case Slope2:
				slope2.add(new Point((int)x, (int)y));
				break;
			case Slope3:
				slope3.add(new Point((int)x, (int)y));
				break;
			case Grass:			
				sprite.paintComponent(g, x, y, ExtraMaths.ActualModulo(randomFactor, 5), randomFactor < 500);
				break;
			default:
				break;
			}
		}
		
		
	}
	

	
	
	public boolean onScreen() {
		if(x > Game.camera.getX() + Game.camera.getWidth())
			return false;
		if(y > Game.camera.getY() + Game.camera.getHeight())
			return false;
		if(x + 128 < Game.camera.getX())
			return false;
		if(y + 128 < Game.camera.getY())
			return false;
		return true;
	}
	
	public String toString() {
		int id;
		switch(type) {
		case Ground:
			id = 0;
			break;
		case Ground2:
			id = 2;
			break;
		case Slope1:
			id = 3;
			break;
		case Slope2:
			id = 4;
			break;
		case Slope3:
			id = 5;
			break;
		case Grass:
			id = 1;
			break;
		default:
			id = 0;
			break;
			
		}
		return "TextureBlock " + x + " " + y + " " + id;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public static boolean deleteTextureBlock(double x, double y) {
		boolean returnValue = false;
		for(int i = allTextureBlocks.size()-1; i >= 0; i--) {
			if(allTextureBlocks.get(i).getX() == x && allTextureBlocks.get(i).getY() == y) {
				returnValue = true;
				allTextureBlocks.remove(allTextureBlocks.get(i));
				Map.reWriteMap();
			}
				
				
		}
		return returnValue;
	}
	
	
	public static void drawOtherBlocks(Graphics2D g) {
		
		try {
			BufferedImage image = ImageIO.read(new File("Assets/Ground.png"));
			BufferedImage rightPart = image.getSubimage(0, 0, 128, 128);
			Image scaledImage = rightPart.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
			
			for(int i = 0; i < ground.size(); i++) {
				int xUsable = (int) ((ground.get(i).getX() - Game.camera.getX()) * Camera.scale);
				int yUsable = (int)((ground.get(i).getY() - Game.camera.getY()) * Camera.scale);
				g.drawImage(scaledImage, xUsable, yUsable, Game.currentGame);
			}
			
			rightPart = image.getSubimage(0, 128, 128, 128);
			scaledImage = rightPart.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
			for(int i = 0; i < ground2.size(); i++) {
				int xUsable = (int) ((ground2.get(i).getX() - Game.camera.getX()) * Camera.scale);
				int yUsable = (int)((ground2.get(i).getY() - Game.camera.getY()) * Camera.scale);
				g.drawImage(scaledImage, xUsable, yUsable, Game.currentGame);
			}
			rightPart = image.getSubimage(0, 256, 128, 128);
			scaledImage = rightPart.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
			for(int i = 0; i < slope1.size(); i++) {
				int xUsable = (int) ((slope1.get(i).getX() - Game.camera.getX()) * Camera.scale);
				int yUsable = (int)((slope1.get(i).getY() - Game.camera.getY()) * Camera.scale);
				g.drawImage(scaledImage, xUsable, yUsable, Game.currentGame);
			}
			rightPart = image.getSubimage(0, 384, 128, 128);
			scaledImage = rightPart.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
			for(int i = 0; i < slope2.size(); i++) {
				int xUsable = (int) ((slope2.get(i).getX() - Game.camera.getX()) * Camera.scale);
				int yUsable = (int)((slope2.get(i).getY() - Game.camera.getY()) * Camera.scale);
				g.drawImage(scaledImage, xUsable, yUsable, Game.currentGame);
			}
			rightPart = image.getSubimage(0, 512, 128, 128);
			scaledImage = rightPart.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
			for(int i = 0; i < slope3.size(); i++) {
				int xUsable = (int) ((slope3.get(i).getX() - Game.camera.getX()) * Camera.scale);
				int yUsable = (int)((slope3.get(i).getY() - Game.camera.getY()) * Camera.scale);
				g.drawImage(scaledImage, xUsable, yUsable, Game.currentGame);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ground.clear();
		ground2.clear();
		slope1.clear();
		slope2.clear();
		slope3.clear();
		
		
	}
	
	public static void drawCurrentlyPlacing(Graphics2D g, int id, int x, int y) {
		try {
			BufferedImage image = ImageIO.read(new File("Assets/Ground.png"));
			BufferedImage rightPart = image.getSubimage(0, 0, 128, 128);
			
			switch(id) {	
			case 0:
				break;
			case 1:
				image = ImageIO.read(new File("Assets/Grass.png"));
				rightPart = image.getSubimage(0, 0, 128, 128);
				break;
			case 2:
				rightPart = image.getSubimage(0, 128, 128, 128);
				break;
			case 3:
				rightPart = image.getSubimage(0, 256, 128, 128);
				break;
			case 4:
				rightPart = image.getSubimage(0, 384, 128, 128);
				break;
			case 5:
				rightPart = image.getSubimage(0, 512, 128, 128);
				break;
				
			}
			Image scaledImage = rightPart.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
			g.drawImage(scaledImage, (int)(x-(64*Camera.scale)), (int)(y-(64*Camera.scale)), Game.currentGame);
		}catch (IOException e){
			e.printStackTrace();
		}
		
	}
}


