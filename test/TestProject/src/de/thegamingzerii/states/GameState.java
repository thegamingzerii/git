package de.thegamingzerii.states;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Objects.Player;
import Objects.Block;
import de.thegamingzerii.maingame.Game;




public class GameState extends JPanel implements State{
	public static Player player;
	public static Block block;
	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub
		player.update(delta);		
	}



	@Override
	public void init() {
		player = new Player();
		block = new Block();
		Game.frame.add(block);
		Game.frame.add(player);
				
	}

	
	public void paint(Graphics2D g) {
		player.paint(g);
		block.paint(g);
		
	}

	


}
