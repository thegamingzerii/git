package de.thegamingzerii.utility;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.IBufferable;

public class Animation implements IBufferable{

	public static ArrayList<Animation> allAnimations = new ArrayList<Animation>();
	
	double x;
	double y;
	BufferedImage buffered;
	int timePerFrame;
	int frameWidth;
	int startHeight;
	int frameHeight;
	Image[] images;
	double animationTimer = 0;
	int frameCount;
	boolean played = false;
	
	public Animation(double x, double y, String path, int timePerFrame, int frameWidth, int frameHeight, int startHeight) {
		this.x = x;
		this.y = y;
		this.timePerFrame = timePerFrame;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.startHeight = startHeight;
		try {
			buffered =  ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frameCount = (int) Math.floor(buffered.getWidth()/frameWidth);
		images = new Image[frameCount];
		for(int i = 0; i < images.length; i++)
			images[i] = buffered.getSubimage(frameWidth * i, startHeight, frameWidth, frameHeight);
		allAnimations.add(this);
	}
	
	
	
	public Animation(Animation ani) {
		this.x = ani.x;
		this.y = ani.y;
		this.timePerFrame = ani.timePerFrame;
		this.frameWidth = ani.frameWidth;
		this.frameHeight = ani.frameHeight;
		this.startHeight = ani.startHeight;
		this.animationTimer = ani.animationTimer;
		this.frameCount = ani.frameCount;
		this.images = ani.images;
		this.played = ani.played;
	}
	
	public void update(double delta) {
		animationTimer += delta;
		if(animationTimer >= timePerFrame * frameCount)
			played = true;
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
		return new Animation(this);
	}

	@Override
	public void paint(Graphics2D g) {
		if(!played) {
			int xUsable = (int) ((x - Game.camera.getCameraPos().getX()) * Game.camera.scale);
			int yUsable = (int)((y - Game.camera.getCameraPos().getY()) * Game.camera.scale);
			g.drawImage(images[ExtraMaths.ActualModulo(animationTimer/timePerFrame, frameCount)], xUsable, yUsable, null);
		}
		
		
	}

}
