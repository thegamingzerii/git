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
import de.thegamingzerii.utility.Constantes.ParticleType;
import de.thegamingzerii.utility.ExtraMaths;

@SuppressWarnings("serial")
public class Particle extends JPanel implements IBufferable{

	public static ArrayList<Particle> allParticles = new ArrayList<Particle>();
	private static int differenParticles = 1;
	private static Image[][] images = new Image[4][differenParticles];
	private static Image[][] scaled= new Image[4][differenParticles];
	private static BufferedImage image;
	
	ParticleType type;
	double x;
	double y;
	double duration;
	double originalDuration;
	double xDirection = Math.random();
	double yDirection = Math.random();
	
	public Particle(ParticleType type, double x, double y, int duration) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.duration = duration;
		this.originalDuration = duration;
		
		allParticles.add(this);
		
		
	}
	
	
	
	public Particle(Particle particle) {
		this.type = particle.type;
		this.x = particle.x;
		this.y = particle.y;
		this.duration = particle.duration;
		this.originalDuration = particle.originalDuration;		
		
	}
	
	
	public static void init() {
		try {
			image = ImageIO.read(new File("Assets/Particles.png"));
			for(int i = 0; i < 4; i++)
				for(int j = 0; j < 1; j++)
					images[i][j] = image.getSubimage(0 + 64 * i, 0 + 64 * j, 64, 64);
		} catch (IOException e) {
			e.printStackTrace();
		}
		reScale();
	}
	
	
	public static void reScale() {
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 1; j++)
				scaled[i][j] = images[i][j].getScaledInstance((int)(32 * Game.camera.scale), (int)(32 * Game.camera.scale), image.SCALE_DEFAULT);
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
		
		int id = 0;
		switch (type) {
			case Dust1:
				id = 0;
				break;
		}
			
		

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (duration / originalDuration)));
		if(duration/originalDuration > 0.75)
			g.drawImage(scaled[0][id], xUsable, yUsable, this);
		else if(duration/originalDuration > 0.5)
			g.drawImage(scaled[1][id], xUsable, yUsable, this);
		else if(duration/originalDuration > 0.25)
			g.drawImage(scaled[2][id], xUsable, yUsable, this);
		else
			g.drawImage(scaled[3][id], xUsable, yUsable, this);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

	}


	@Override
	public boolean onScreen() {
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
