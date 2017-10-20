package de.thegamingzerii.states;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.Block;
import de.thegamingzerii.objects.Player;




public class GameState extends JPanel implements State{
	public static Player player;
	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub
		player.update(delta);		
	}



	@Override
	public void init() {
		player = new Player();
				
	}

	
	public void paint(Graphics2D g) {
		player.paint(g);
		for(int i = 0; i < Block.allBlocks.size(); i++) {
			Block.allBlocks.get(i).paint(g);
		}
		
	}

	


}
