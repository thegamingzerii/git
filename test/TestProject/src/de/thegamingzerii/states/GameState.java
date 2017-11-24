package de.thegamingzerii.states;

import java.awt.Graphics2D;

import de.thegamingzerii.items.Bomb;
import de.thegamingzerii.logicParts.Gate;
import de.thegamingzerii.logicParts.Lever;
import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.BackgroundObject;
import de.thegamingzerii.objects.Block;
import de.thegamingzerii.objects.Camera;
import de.thegamingzerii.objects.DeadlyBlock;
import de.thegamingzerii.objects.Jumper;
import de.thegamingzerii.objects.MovingPlatform;
import de.thegamingzerii.objects.Obstacle;
import de.thegamingzerii.objects.Particle;
import de.thegamingzerii.objects.Player;
import de.thegamingzerii.objects.Rope;
import de.thegamingzerii.objects.Slope;
import de.thegamingzerii.objects.TextureBlock;




@SuppressWarnings("serial")
public class GameState extends State{
	
	public static Player player;
	@Override
	public void update(double delta) {
		
		if(Game.actualCamera.zoom != 1)
			Game.actualCamera.reFrame(1);
		else {
			player.update(delta);		
			
			for(int i = 0; i < Jumper.allJumpers.size(); i++) {
				Jumper.allJumpers.get(i).update(delta);
				if(Jumper.allJumpers.get(i).checkProximity(player.getCollisionSize())) {
					Jumper.allJumpers.get(i).interact(false);
				}
			}
			
			for(int i = 0; i < Bomb.allBombs.size(); i++) {
				Bomb.allBombs.get(i).update(delta);
			}
			
			for(int i = 0; i < Lever.allLevers.size(); i++) {
				if(Lever.allLevers.get(i).checkProximity(player.getCollisionSize()))
					Lever.allLevers.get(i).interact(false);
			}
			
			for(int i = 0; i < Rope.allRopes.size(); i++) {
				Rope.allRopes.get(i).update(delta);
				if(Rope.allRopes.get(i).checkProximity(player.getCollisionSize())) {
					Rope.allRopes.get(i).interact(false);
				}
			}
			
			for(int i = 0; i < Particle.allParticles.size(); i++) {
				Particle.allParticles.get(i).update(delta);
			}
			
			for(int i = 0; i < DeadlyBlock.allDeadlyBlocks.size(); i++) {
				if(DeadlyBlock.allDeadlyBlocks.get(i).checkProximity(player.getCollisionSize())) {
					DeadlyBlock.allDeadlyBlocks.get(i).interact(false);
				}
			}
			
			
			for(int i = 0; i < MovingPlatform.allMovingPlatforms.size(); i++) {
				MovingPlatform.allMovingPlatforms.get(i).update(delta);
			}
			
			for(int i = 0; i < Gate.allGates.size(); i++) {
				Gate.allGates.get(i).update(delta);
			}
		}
		
		
		Game.actualCamera.moveCamera((player.getXAxis() - Game.actualCamera.getCameraPos().getX() - (Game.actualCamera.getWidth()/2)) * 0.1 * delta, 
				(player.getYAxis() - Game.actualCamera.getCameraPos().getY() - (Game.actualCamera.getHeight()/2)) * 0.1 * delta);
		
		
		
		
		
		
		
		
	}
	
	public void buffer() {
		Game.actualCamera.buffer();
		
		for(int i = 0; i < BackgroundObject.allBackgroundObjects.size(); i++) {
			BackgroundObject.allBackgroundObjects.get(i).buffer();
		}
		
		player.buffer();
		for(int i = 0; i < Jumper.allJumpers.size(); i++) {
			Jumper.allJumpers.get(i).buffer();
		}
		
		for(int i = 0; i < Gate.allGates.size(); i++) {
			Gate.allGates.get(i).buffer();
		}
		
		for(int i = 0; i < Lever.allLevers.size(); i++) {
			Lever.allLevers.get(i).buffer();
		}
		
		for(int i = 0; i < Particle.allParticles.size(); i++) {
			Particle.allParticles.get(i).buffer();
		}
		
		for(int i = 0; i < DeadlyBlock.allDeadlyBlocks.size(); i++) {
			DeadlyBlock.allDeadlyBlocks.get(i).buffer();
		}
		for(int i = 0; i < TextureBlock.allTextureBlocks.size(); i++) {
			TextureBlock.allTextureBlocks.get(i).buffer();
		}
		
		for(int i = 0; i < Rope.allRopes.size(); i++) {
			Rope.allRopes.get(i).buffer();
		}
		
		for(int i = 0; i < Bomb.allBombs.size(); i++) {
			Bomb.allBombs.get(i).buffer();
		}
		
		for(int i = 0; i < Slope.allSlopes.size(); i++) {
			Slope.allSlopes.get(i).buffer();
		}
		for(int i = 0; i < MovingPlatform.allMovingPlatforms.size(); i++) {
			MovingPlatform.allMovingPlatforms.get(i).buffer();
		}
		
		
		//if(Game.drawHitBoxes)
		for(int i = 0; i < Obstacle.allObstacles.size(); i++) {
			Obstacle.allObstacles.get(i).buffer();
		}
	}



	@Override
	public void init() {
		player = new Player(96, 128);	
	}

	
	public void paint(Graphics2D g) {
	
		
	}
	
	public void output() {
System.out.println("dies das");
	}

	


}
