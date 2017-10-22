package de.thegamingzerii.states;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thegamingzerii.editor.Map;
import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.Block;
import de.thegamingzerii.objects.Camera;

public class EditingState extends JPanel implements State{

	private boolean placing = false;
	private double placingX = 0;
	private double placingY = 0;
	public static boolean editing = false;
	public static int mode = 0;
	
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
		Game.ingameState.paint(g);
		
		if(placing) {
			switch(mode) {
			case 0:
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

				g.drawRect ((int) Math.round(x), (int)Math.round(y), (int)Math.round(xDiff), (int)Math.round(yDiff));
				break;
			case 1:
				g.drawOval((int)MouseInfo.getPointerInfo().getLocation().getX() - 50, (int)MouseInfo.getPointerInfo().getLocation().getY() - 50, 100, 100);
			}
			
			  
		}
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int xUsable = (int) (50 * Camera.scale);
		int yUsable = (int)(50 * Camera.scale);
		
		try {
			BufferedImage image = ImageIO.read(new File("Assets/Modes.gif"));
			Image scaledImage = image.getScaledInstance((int)(264 * Camera.scale), (int)(128 * Camera.scale), image.SCALE_DEFAULT);
			g.drawImage(scaledImage, xUsable, yUsable, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void mousePressed(MouseEvent e) {
		if(editing) {
			placing = true;
			placingX = e.getX() + Game.camera.getCameraPos().getX();
			placingY = e.getY() + Game.camera.getCameraPos().getY();
		}
		
		
		
	}
	public void mouseReleased(MouseEvent e) {
		if(editing) {
			placing = false;
			switch(mode) {
			case 0:
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
					Map.addToMap("Block " + Math.round(x) + " " + Math.round(y) + " " + Math.round(xDifference) + " " + Math.round(yDifference));
				break;
			case 1:
				Map.addToMap("Jumper " + Math.round(e.getX()+ Game.camera.getCameraPos().getX()) + " " + Math.round(e.getY()+ Game.camera.getCameraPos().getY()));
				break;
			}
			
			
		}
		
		
		
	}

}
