package de.thegamingzerii.spawners;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.IBufferable;
import de.thegamingzerii.utility.ExtraMaths;

public class Leaf implements IBufferable{

	private static int rows = 3;
	public static ArrayList<Leaf> allLeafs = new ArrayList<Leaf>();
	private static Image[][] leaf = new Image[4][rows];
	
	double x;
	double y;
	double xMod;
	double yMod;
	double animationTimer;
	double aliveTimer = 1;
	int row;
	
	public Leaf(double x, double y) {
		this.x = x;
		this.y = y;
		this.xMod = 3 + Math.random() * 4;
		this.yMod = 2 + Math.random() * 2;
		row = ExtraMaths.ActualModulo(Math.random()*100, rows);
		allLeafs.add(this);
	}
	
	public Leaf(Leaf leaf) {
		this.x = leaf.x;
		this.y = leaf.y;
		this.xMod = leaf.xMod;
		this.yMod = leaf.yMod;
		this.animationTimer = leaf.animationTimer;
		this.aliveTimer = leaf.aliveTimer;
		this.row = leaf.row;
	}
	
	public void update(double delta) {
		if(aliveTimer < 1100)
			aliveTimer += delta;
		else
			allLeafs.remove(this);
		animationTimer = Game.animationCounter;
		x -= xMod * delta;
		y += yMod * delta;
	}
	
	public static void init() {
		try {
			BufferedImage buffered =  ImageIO.read(new File("Assets/Leaf.png"));
			for(int i = 0; i < 4; i++)
				for(int j = 0; j < rows; j++)
					leaf[i][j] = buffered.getSubimage(0 + i * 32, 0 + j * 32, 32, 32);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	@Override
	public boolean onScreen() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void buffer() {
		if(onScreen())
			Game.currentBuffer.add(this.getCopy());
	}

	@Override
	public IBufferable getCopy() {
		return new Leaf(this);
	}

	@Override
	public void paint(Graphics2D g) {
		int xUsable = (int) ((x - Game.camera.getCameraPos().getX()) * Game.camera.scale);
		int yUsable = (int)((y - Game.camera.getCameraPos().getY()) * Game.camera.scale);
		if(aliveTimer < 30)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (aliveTimer/30)));	
		g.drawImage(leaf[ExtraMaths.ActualModulo(animationTimer/10, 4)][row], xUsable, yUsable, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
	}

}
