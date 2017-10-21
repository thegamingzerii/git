package de.thegamingzerii.states;

import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.thegamingzerii.editor.Map;
import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.Block;

public class EditingState extends JPanel implements State{

	private boolean placing = false;
	private double placingX = 0;
	private double placingY = 0;
	public static boolean editing = false;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics2D g) {
		GameState.player.paint(g);
		for(int i = 0; i < Block.allBlocks.size(); i++) {
			Block.allBlocks.get(i).paint(g);
		}
		
		if(placing) {
			double x = placingX - Game.camera.getCameraPos().getX();
			double y = placingY - Game.camera.getCameraPos().getY();
			double x2 = MouseInfo.getPointerInfo().getLocation().getX();
			double y2 = MouseInfo.getPointerInfo().getLocation().getY();
			double xDiff = x2 - x;
			double yDiff = y2 - y;
			
			
		
			if(xDiff < 0) {
				x = x2;
				xDiff = Math.abs(xDiff);
			}
			if(yDiff < 0) {
				y = y2;
				yDiff = Math.abs(yDiff);
			}

			System.out.println((int)x + "; " + (int)y + "; " + (int)(xDiff) + "; " + (int)(yDiff));
			g.drawRect ((int)x, (int)y, (int)(xDiff), (int)(yDiff));
			  
		}
		
	}
	
	
	public void mousePressed(MouseEvent e) {
		if(editing) {
			System.out.println("mousepressed");
			placing = true;
			placingX = e.getX() + Game.camera.getCameraPos().getX();
			placingY = e.getY() + Game.camera.getCameraPos().getY();
		}
		
		
		
	}
	public void mouseReleased(MouseEvent e) {
		if(editing) {
			placing = false;
			double xDifference = e.getX()+ Game.camera.getCameraPos().getX() - placingX;
			double yDifference = e.getY()+ Game.camera.getCameraPos().getY() - placingY;
			double y = 0;
			double x = 0;
			if(yDifference < 0) {
				y = e.getY()+ Game.camera.getCameraPos().getY();
				yDifference = Math.abs(yDifference);
			}
				
			else
				y = placingY;
			
			if(xDifference < 0) {
				x = e.getX()+ Game.camera.getCameraPos().getX();
				xDifference = Math.abs(xDifference);
			}
			else
				x = placingX;
				
				
			if(xDifference != 0 && yDifference != 0)
				Map.addToMap("Block " + x + " " + y + " " + xDifference + " " + yDifference);
			
		}
		
		
		
	}

}
