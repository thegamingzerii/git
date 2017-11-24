package de.thegamingzerii.objects;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;

@SuppressWarnings("serial")
public class Particle extends JPanel implements IBufferable{

	public static ArrayList<Particle> allParticles = new ArrayList<Particle>();
	
	String path;
	double x;
	double y;
	int width;
	int height;
	double duration;
	double originalDuration;
	double xDirection = Math.random();
	double yDirection = Math.random();
	
	public Particle(String path, double x, double y, int width, int height, int duration) {
		this.path = path;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.duration = duration;
		this.originalDuration = duration;
		
		allParticles.add(this);
		
		
	}
	
	
	
	public Particle(Particle particle) {
		this.path = particle.path;
		this.x = particle.x;
		this.y = particle.y;
		this.width = particle.width;
		this.height = particle.height;
		this.duration = particle.duration;
		this.originalDuration = particle.duration;		
		
	}
	
	
	public void update(double delta) {
		duration -= delta;
		if(duration <= 0) {
			allParticles.remove(this);
			duration = 0.1;
		}
		x -=  delta * (- 0.5  + xDirection);
		y -= 2 * delta * xDirection;
	}
	
	
	public void paint(Graphics2D g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int xUsable = (int) ((x - Game.camera.getCameraPos().getX()) * Game.camera.scale);
		int yUsable = (int)((y - Game.camera.getCameraPos().getY()) * Game.camera.scale);
		
		try {
			BufferedImage image = ImageIO.read(new File(path));
			@SuppressWarnings("static-access")
			Image scaledImage = image.getScaledInstance((int)(width * Game.camera.scale), (int)(height * Game.camera.scale), image.SCALE_DEFAULT);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (duration / originalDuration)));
			g.drawImage(scaledImage, xUsable, yUsable, this);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public boolean onScreen() {
		if(x > Game.camera.getX() + Game.camera.getWidth())
			return false;
		if(y > Game.camera.getY() + Game.camera.getHeight())
			return false;
		if(x + width < Game.camera.getX())
			return false;
		if(y + height < Game.camera.getY())
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
		return new Particle(this);
	}
	
}
