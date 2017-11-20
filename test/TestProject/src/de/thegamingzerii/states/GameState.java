package de.thegamingzerii.states;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.Block;
import de.thegamingzerii.objects.Camera;
import de.thegamingzerii.objects.DeadlyBlock;
import de.thegamingzerii.objects.Jumper;
import de.thegamingzerii.objects.MovingPlatform;
import de.thegamingzerii.objects.Particle;
import de.thegamingzerii.objects.Player;
import de.thegamingzerii.objects.Rope;
import de.thegamingzerii.objects.Slope;
import de.thegamingzerii.objects.TextureBlock;




public class GameState extends State{
	
	
	public static Player player;
	@Override
	public void update(double delta) {
		
		
		// TODO Auto-generated method stub
		player.update(delta);		
		Game.camera.moveCamera((player.getXAxis() - Game.camera.getCameraPos().getX() - (Game.camera.getWidth()/2)) * 0.1 * delta, 
				(player.getYAxis() - Game.camera.getCameraPos().getY() - (Game.camera.getHeight()/2)) * 0.1 * delta);
		
		for(int i = 0; i < Jumper.allJumpers.size(); i++) {
			Jumper.allJumpers.get(i).update(delta);
			if(Jumper.allJumpers.get(i).checkProximity(player.getCollisionSize())) {
				Jumper.allJumpers.get(i).interact();
			}
		}
		
		for(int i = 0; i < Rope.allRopes.size(); i++) {
			Rope.allRopes.get(i).update(delta);
			if(Rope.allRopes.get(i).checkProximity(player.getCollisionSize())) {
				Rope.allRopes.get(i).interact();
			}
		}
		
		for(int i = 0; i < Particle.allParticles.size(); i++) {
			Particle.allParticles.get(i).update(delta);
		}
		
		for(int i = 0; i < DeadlyBlock.allDeadlyBlocks.size(); i++) {
			if(DeadlyBlock.allDeadlyBlocks.get(i).checkProximity(player.getCollisionSize())) {
				DeadlyBlock.allDeadlyBlocks.get(i).interact();
			}
		}
		
		for(int i = 0; i < MovingPlatform.allMovingPlatforms.size(); i++) {
			MovingPlatform.allMovingPlatforms.get(i).update(delta);
		}
		
		
		
		if(Camera.zoom != 1)
			Game.camera.reFrame(1);
		
	}



	@Override
	public void init() {
		player = new Player(96, 128);
				
	}

	
	public void paint(Graphics2D g) {
		player.paint(g);
		for(int i = 0; i < Jumper.allJumpers.size(); i++) {
			Jumper.allJumpers.get(i).paint(g);
		}
		
		for(int i = 0; i < Particle.allParticles.size(); i++) {
			Particle.allParticles.get(i).paint(g);
		}
		
		for(int i = 0; i < DeadlyBlock.allDeadlyBlocks.size(); i++) {
			DeadlyBlock.allDeadlyBlocks.get(i).paint(g);
		}
		for(int i = 0; i < TextureBlock.allTextureBlocks.size(); i++) {
			TextureBlock.allTextureBlocks.get(i).draw(g);
		}
		
		for(int i = 0; i < Rope.allRopes.size(); i++) {
			Rope.allRopes.get(i).paint(g);
		}
		
		if(Game.drawHitBoxes)
		for(int i = 0; i < Block.allBlocks.size(); i++) {
			Block.allBlocks.get(i).paint(g);
		}
		
		for(int i = 0; i < Slope.allSlopes.size(); i++) {
			Slope.allSlopes.get(i).paintComponent(g);
		}
		for(int i = 0; i < MovingPlatform.allMovingPlatforms.size(); i++) {
			MovingPlatform.allMovingPlatforms.get(i).paintComponent(g);
		}
		
		
		
		
	}
	
	public void output() {
System.out.println("dies das");
	}

	


}
