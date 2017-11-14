package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.util.ArrayList;

import de.thegamingzerii.editor.Map;
import de.thegamingzerii.utility.Constantes.BlockType;

public class TextureBlock {
	public static ArrayList<TextureBlock> allTextureBlocks = new ArrayList<TextureBlock>();
	
	private double x;
	private double y;
	private BlockType type;
	private SpriteSheet sprite;
	private double randomFactor;
	
	public TextureBlock(double x, double y, BlockType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		
		String path = "";
		switch(type) {
		case Ground:
			path = "Assets/Ground.png";
			break;
		case Grass:
			path = "Assets/Grass.png";
			break;
		default:
			break;
		}
		sprite = new SpriteSheet(path, 32, 32, 0, 1, 4);
		randomFactor = Math.round(Math.random()*1000);
		
		allTextureBlocks.add(this);
	}
	
	public void draw(Graphics2D g) {
		switch(type) {
		case Ground:
			sprite.paintComponent(g, x, y, 0, false);
			break;
		case Grass:
			sprite.paintComponent(g, x, y, (int)randomFactor%5, randomFactor < 500);
			break;
		default:
			break;
		}
		
	}
	
	public String toString() {
		int id;
		switch(type) {
		case Ground:
			id = 0;
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
}
