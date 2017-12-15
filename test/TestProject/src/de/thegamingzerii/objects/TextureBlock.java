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
	private static BufferedImage b1 = null;
	private static BufferedImage b2 = null;
	private static BufferedImage b3 = null;
	
	private static Image ground1 = null;
	private static Image ground2 = null;
	private static Image slope1 = null;
	private static Image slope2 = null;
	private static Image slope3 = null;


	private static Image[][] grass = new Image[4][5];
	private static Image[][] scaledGrass = new Image[4][5];
	
	private static Image[] brick = new Image[3];
	private static Image[] scaledBrick = new Image[3];
	private static Image[] brickTop = new Image[3];
	private static Image[] scaledBrickTop = new Image[3];
	private static Image[] brickBackground = new Image[6];
	private static Image[] scaledBrickBackground = new Image[6];
	private static Image[] brickStairs = new Image[3];
	private static Image[] scaledBrickStairs = new Image[3];
	
	
	
	
	private static BufferedImage image = null;
	
	private boolean background = false;
	private double x;
	private double y;
	public BlockType type;
	public double randomFactor;
	Random generator;
	
	
	
	public TextureBlock(double x, double y, BlockType type, Boolean b) {
		this.x = x;
		this.y = y;
		this.type = type;
		generator = new Random((long) (x*y));
		randomFactor = Math.round(generator.nextInt()/100);
		this.background = b;
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
		b1 = ImageIO.read(new File("Assets/BrickWall.png"));
		b2 = ImageIO.read(new File("Assets/BrickWallBackground.png"));
		b3 = ImageIO.read(new File("Assets/BrickWallTop.png"));
		image = ImageIO.read(new File("Assets/Grass.png"));
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {
				grass[i][j] = image.getSubimage(0 + 128 * i, 0 + 128 * j, 128, 128);
			}
		}
		
		image = ImageIO.read(new File("Assets/BrickWallBackground.png"));
		for(int i = 0; i < 6; i++) {
			brickBackground[i] = image.getSubimage(0, 0 + 128 * i, 128, 128);
		}
		
		image = ImageIO.read(new File("Assets/BrickWall.png"));
		for(int i = 0; i < 3; i++) {
			brick[i] = image.getSubimage(0, 0 + 128 * i, 128, 128);
		}
		
		image = ImageIO.read(new File("Assets/BrickWallTop.png"));
		for(int i = 0; i < 3; i++) {
			brickTop[i] = image.getSubimage(0, 0 + 128 * i, 128, 128);
		}
		
		image = ImageIO.read(new File("Assets/BrickWallStairs.png"));
		for(int i = 0; i < 3; i++) {
			brickStairs[i] = image.getSubimage(0, 0 + 128 * i, 128, 128);
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
		
		for(int i = 0; i < 3; i++) {
			scaledBrick[i] = brick[i].getScaledInstance((int)(128 * Game.camera.scale), (int)(128 * Game.camera.scale), image.SCALE_DEFAULT);
		}
		for(int i = 0; i < 6; i++) {
			scaledBrickBackground[i] = brickBackground[i].getScaledInstance((int)(128 * Game.camera.scale), (int)(128 * Game.camera.scale), image.SCALE_DEFAULT);
		}
		for(int i = 0; i < 3; i++) {
			scaledBrickTop[i] = brickTop[i].getScaledInstance((int)(128 * Game.camera.scale), (int)(128 * Game.camera.scale), image.SCALE_DEFAULT);
		}
		for(int i = 0; i < 3; i++) {
			scaledBrickStairs[i] = brickStairs[i].getScaledInstance((int)(128 * Game.camera.scale), (int)(128 * Game.camera.scale), image.SCALE_DEFAULT);
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
			case Brick1:
				g.drawImage(scaledBrick[ExtraMaths.ActualModulo(randomFactor, 3)], xUsable, yUsable, Game.currentGame);
				break;
			case Brick2:
				g.drawImage(scaledBrickBackground[ExtraMaths.ActualModulo(randomFactor, 6)], xUsable, yUsable, Game.currentGame);
				break;
			case Brick3:
				g.drawImage(scaledBrickTop[ExtraMaths.ActualModulo(randomFactor, 3)], xUsable, yUsable, Game.currentGame);
				break;
			case BrickStairs1:
				g.drawImage(scaledBrickStairs[0], xUsable, yUsable, Game.currentGame);
				break;
			case BrickStairs2:
				g.drawImage(scaledBrickStairs[1], xUsable, yUsable, Game.currentGame);
				break;
			case BrickStairs3:
				g.drawImage(scaledBrickStairs[2], xUsable, yUsable, Game.currentGame);
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
		case Brick1:
			id = 6;
			break;
		case Brick2:
			id = 7;
			break;
		case Brick3:
			id = 8;
			break;
		case BrickStairs1:
			id = 9;
			break;
		case BrickStairs2:
			id = 10;
			break;
		case BrickStairs3:
			id = 11;
			break;
		default:
			id = 0;
			break;
			
		}
		String backgroundString = "";
		if(background)
			backgroundString = " true";
		else
			backgroundString = " false";
		
		return "TextureBlock " + x + " " + y + " " + id + backgroundString;
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
				g.drawImage(scaledGrass[0][0], x, y, Game.currentGame);
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
			case 6:
				g.drawImage(scaledBrick[0], x, y, Game.currentGame);
				break;
			case 7:
				g.drawImage(scaledBrickBackground[0], x, y, Game.currentGame);
				break;
			case 8:
				g.drawImage(scaledBrickTop[0], x, y, Game.currentGame);
				break;
			case 9:
				g.drawImage(scaledBrickStairs[0], x, y, Game.currentGame);
				break;
			case 10:
				g.drawImage(scaledBrickStairs[1], x, y, Game.currentGame);
				break;
			case 11:
				g.drawImage(scaledBrickStairs[2], x, y, Game.currentGame);
				break;
				
			}

		
	}

	@Override
	public void buffer() {
		if(onScreen()) {
			Game.currentBuffer.add(this.getCopy());
		}
			
	}
	
public void buffer(boolean b) {	
		if(onScreen() && this.background == b) {
			Game.currentBuffer.add(this.getCopy());
		}
			
	}

	@Override
	public IBufferable getCopy() {
		return new TextureBlock(this);
	}
}