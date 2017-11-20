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
	private static BufferedImage g1 = null;
	private static BufferedImage g2 = null;
	private static BufferedImage s1 = null;
	private static BufferedImage s2 = null;
	private static BufferedImage s3 = null;
	private static BufferedImage gr1 = null;
	private static BufferedImage gr2 = null;
	private static BufferedImage gr3 = null;
	private static BufferedImage gr4 = null;
	private static BufferedImage gr5 = null;
	
	private static Image ground1 = null;
	private static Image ground2 = null;
	private static Image slope1 = null;
	private static Image slope2 = null;
	private static Image slope3 = null;
	private static Image grass1 = null;
	private static Image grass2 = null;
	private static Image grass3 = null;
	private static Image grass4 = null;
	private static Image grass5 = null;
	
	
	
	
	private static BufferedImage image = null;
	
	private double x;
	private double y;
	private BlockType type;
	private SpriteSheet sprite;
	private double randomFactor;
	Random generator;
	
	public static void init() throws IOException {
		BufferedImage image = ImageIO.read(new File("Assets/Ground.png"));
		g1 = image.getSubimage(0, 0, 128, 128);
		g2 = image.getSubimage(0, 128, 128, 128);
		s1 = image.getSubimage(0, 256, 128, 128);
		s2 = image.getSubimage(0, 384, 128, 128);
		s3 = image.getSubimage(0, 512, 128, 128);
		
		image = ImageIO.read(new File("Assets/Grass.png"));
		gr1 = image.getSubimage(0, 0, 128, 128);
		gr2 = image.getSubimage(0, 128, 128, 128);
		gr3 = image.getSubimage(0, 256, 128, 128);
		gr4 = image.getSubimage(0, 384, 128, 128);
		gr5 = image.getSubimage(0, 512, 128, 128);
		reScale(1);
	}
	
	public static void reScale(double scaling) {
		System.out.println("scaling");
		ground1 = g1.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
		ground2 = g2.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
		slope1 = s1.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
		slope2 = s2.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
		slope3 = s3.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
		grass1 = gr1.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
		grass2 = gr2.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
		grass3 = gr3.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
		grass4 = gr4.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
		grass5 = gr5.getScaledInstance((int)(128 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
	}
	
	
	public TextureBlock(double x, double y, BlockType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		generator = new Random((long) (x*y));
		randomFactor = Math.round(generator.nextInt()/100);
		allTextureBlocks.add(this);
	}

	
	public void draw(Graphics2D g) {
		if(onScreen()) {
			int xUsable = (int) ((x - Game.camera.getX()) * Camera.scale);
			int yUsable = (int)((y - Game.camera.getY()) * Camera.scale);
			switch(type) {
			case Ground:
				g.drawImage(ground1, xUsable, yUsable, Game.currentGame);
				break;
			case Ground2:
				g.drawImage(ground2, xUsable, yUsable, Game.currentGame);
				break;
			case Slope1:
				g.drawImage(slope1, xUsable, yUsable, Game.currentGame);
				break;
			case Slope2:
				g.drawImage(slope2, xUsable, yUsable, Game.currentGame);
				break;
			case Slope3:
				g.drawImage(slope3, xUsable, yUsable, Game.currentGame);
				break;
			case Grass:		
				switch(ExtraMaths.ActualModulo(randomFactor, 5)) {
				case 0:
					g.drawImage(grass1, xUsable, yUsable, Game.currentGame);
					break;
				case 1:
					g.drawImage(grass2, xUsable, yUsable, Game.currentGame);
					break;
				case 2:
					g.drawImage(grass3, xUsable, yUsable, Game.currentGame);
					break;
				case 3:
					g.drawImage(grass4, xUsable, yUsable, Game.currentGame);
					break;
				case 4:
					g.drawImage(grass5, xUsable, yUsable, Game.currentGame);
					break;
				}				
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
	

	
	public static void drawCurrentlyPlacing(Graphics2D g, int id, int x, int y) {
			
			switch(id) {	
			case 0:
				g.drawImage(ground1, x, y, Game.currentGame);
				break;
			case 1:
				g.drawImage(grass1, x, y, Game.currentGame);
				break;
			case 2:
				g.drawImage(ground2, x, y, Game.currentGame);
				break;
			case 3:
				g.drawImage(slope1, x, y, Game.currentGame);
				break;
			case 4:
				g.drawImage(slope2, x, y, Game.currentGame);
				break;
			case 5:
				g.drawImage(slope3, x, y, Game.currentGame);
				break;
				
			}

		
	}
}


