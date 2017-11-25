package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.awt.Image;
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

public class TextureBlock implements IBufferable{
	public static ArrayList<TextureBlock> allTextureBlocks = new ArrayList<TextureBlock>();
	private static BufferedImage g1 = null;
	private static BufferedImage g2 = null;
	private static BufferedImage s1 = null;
	private static BufferedImage s2 = null;
	private static BufferedImage s3 = null;
	
	private static Image ground1 = null;
	private static Image ground2 = null;
	private static Image slope1 = null;
	private static Image slope2 = null;
	private static Image slope3 = null;
	private static Image grass1 = null;
	
	private static Image[][] grass = new Image[4][5];
	private static Image[][] scaledGrass = new Image[4][5];
	
	
	
	
	private static BufferedImage image = null;
	
	private double x;
	private double y;
	public BlockType type;
	public double randomFactor;
	Random generator;
	
	
	
	public TextureBlock(double x, double y, BlockType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		generator = new Random((long) (x*y));
		randomFactor = Math.round(generator.nextInt()/100);
		allTextureBlocks.add(this);
	}
	
	
	public TextureBlock(TextureBlock block) {
		this.x = block.getX();
		this.y = block.getY();
		this.type = block.type;
		randomFactor = block.randomFactor;
	}
	
	public static void init() throws IOException {
		BufferedImage image = ImageIO.read(new File("Assets/Ground.png"));
		g1 = image.getSubimage(0, 0, 128, 128);
		g2 = image.getSubimage(0, 128, 128, 128);
		s1 = image.getSubimage(0, 256, 128, 128);
		s2 = image.getSubimage(0, 384, 128, 128);
		s3 = image.getSubimage(0, 512, 128, 128);
		
		image = ImageIO.read(new File("Assets/Grass.png"));
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {
				grass[i][j] = image.getSubimage(0 + 128 * i, 0 + 128 * j, 128, 128);
			}
		}
		reScale();
	}
	
	@SuppressWarnings("static-access")
	public static void reScale() {
		ground1 = g1.getScaledInstance((int)(128 * Game.camera.scale), (int)(128 * Game.camera.scale), image.SCALE_DEFAULT);
		ground2 = g2.getScaledInstance((int)(128 * Game.camera.scale), (int)(128 * Game.camera.scale), image.SCALE_DEFAULT);
		slope1 = s1.getScaledInstance((int)(128 * Game.camera.scale), (int)(128 * Game.camera.scale), image.SCALE_DEFAULT);
		slope2 = s2.getScaledInstance((int)(128 * Game.camera.scale), (int)(128 * Game.camera.scale), image.SCALE_DEFAULT);
		slope3 = s3.getScaledInstance((int)(128 * Game.camera.scale), (int)(128 * Game.camera.scale), image.SCALE_DEFAULT);
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {
				scaledGrass[i][j] = grass[i][j].getScaledInstance((int)(128 * Game.camera.scale), (int)(128 * Game.camera.scale), image.SCALE_DEFAULT);
			}
		}
	}
	

	
	public void paint(Graphics2D g) {
		if(onScreen()) {
			int xUsable = (int) ((x - Game.camera.getX()) * Game.camera.scale);
			int yUsable = (int)((y - Game.camera.getY()) * Game.camera.scale);
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
				g.drawImage(scaledGrass[ExtraMaths.ActualModulo(Math.floor((Game.animationCounter + randomFactor) /8), 4)][ExtraMaths.ActualModulo(randomFactor, 5)], xUsable, yUsable, Game.currentGame);
				break;
			}
		}
		
		
	}
	

	
	
	public boolean onScreen() {
		if(x > Game.actualCamera.getX() + Game.actualCamera.getWidth())
			return false;
		if(y > Game.actualCamera.getY() + Game.actualCamera.getHeight())
			return false;
		if(x + 128 < Game.actualCamera.getX())
			return false;
		if(y + 128 < Game.actualCamera.getY())
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

	@Override
	public void buffer() {
		
		if(onScreen()) {
			Game.currentBuffer.add(this.getCopy());
		}
			
	}

	@Override
	public IBufferable getCopy() {
		return new TextureBlock(this);
	}
}


