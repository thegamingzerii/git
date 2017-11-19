package de.thegamingzerii.utility;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import de.thegamingzerii.objects.TextureBlock;

public class PaintCall {
	long id;
	public double camPosX;
	public double camPosY;
	public double scale;
	public ArrayList<Point> ground1 = new ArrayList<Point>();
	public ArrayList<Point> ground2 = new ArrayList<Point>();
	public ArrayList<ArrayList<Point>> blockList = new ArrayList<ArrayList<Point>>();
	
	
	public PaintCall(long counter) {
		id = counter;
	}
	
	public void setTextureBlocks(ArrayList<ArrayList<Point>> blockLists) {
		ground1 = blockLists.get(0);
		ground2 = blockLists.get(1);
		this.blockList = blockLists;
	}
	
	public void paint(Graphics g) {
		System.out.println("painting blocks: " + blockList.size() + " " + blockList.get(0).size()+ " " + blockList.get(1).size());
		TextureBlock.drawOtherBlocks((Graphics2D) g, camPosX, camPosY, scale, blockList);
	}
}
