package de.thegamingzerii.states;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.Block;
import de.thegamingzerii.objects.Jumper;
import de.thegamingzerii.objects.Particle;
import de.thegamingzerii.objects.Player;




public class GameState extends State{
	public static Player player;
	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub
		player.update(delta);		
		Game.camera.moveCamera((player.getXAxis() - Game.camera.getCameraPos().getX() - (Game.camera.getWidth()/2)) * 0.1, 
				(player.getYAxis() - Game.camera.getCameraPos().getY() - (Game.camera.getHeight()/2)) * 0.1);
		
		for(int i = 0; i < Jumper.allJumpers.size(); i++) {
			Jumper.allJumpers.get(i).update(delta);
			if(Jumper.allJumpers.get(i).checkProximity(player.getCollisionSize())) {
				Jumper.allJumpers.get(i).interact();
			}
		}
		
		for(int i = 0; i < Particle.allParticles.size(); i++) {
			Particle.allParticles.get(i).update(delta);
		}
		
	}



	@Override
	public void init() {
		player = new Player(96, 128);
				
	}

	
	public void paint(Graphics2D g) {
		player.paint(g);
		for(int i = 0; i < Block.allBlocks.size(); i++) {
			Block.allBlocks.get(i).paint(g);
		}
		for(int i = 0; i < Jumper.allJumpers.size(); i++) {
			Jumper.allJumpers.get(i).paint(g);
		}
		
		for(int i = 0; i < Particle.allParticles.size(); i++) {
			Particle.allParticles.get(i).paint(g);
		}
	}
	
	public void output() {
System.out.println("dies das");
	}

	


}
