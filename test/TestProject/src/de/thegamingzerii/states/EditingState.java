package de.thegamingzerii.states;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thegamingzerii.editor.Map;
import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.Block;
import de.thegamingzerii.objects.Camera;
import de.thegamingzerii.objects.Jumper;

public class EditingState extends State{

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

		if(MouseInfo.getPointerInfo().getLocation().getX() > 1900)
			Game.camera.moveCamera(50, 0);
		if(MouseInfo.getPointerInfo().getLocation().getY() > 1060)
			Game.camera.moveCamera(0, 50);
		if(MouseInfo.getPointerInfo().getLocation().getX() < 20)
			Game.camera.moveCamera(-50, 0);
		if(MouseInfo.getPointerInfo().getLocation().getY() < 20)
			Game.camera.moveCamera(0, -50);
		
	}

	@Override
	public void paint(Graphics2D g) {
		Game.ingameState.paint(g);
		
		if(placing) {
			double x = (placingX - Game.camera.getCameraPos().getX()) / Camera.zoom;
			double y = (placingY - Game.camera.getCameraPos().getY()) / Camera.zoom;
			double x2 = MouseInfo.getPointerInfo().getLocation().getX();
			double y2 = MouseInfo.getPointerInfo().getLocation().getY();
			double xDiff = x2 - x;
			double yDiff = y2 - y;
			switch(mode) {
			case 0:
				

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
				break;
				
			case 2:

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
			}
			
			  
		}
		
		 super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int xUsable = (int) (50 * Camera.scale);
		int yUsable = (int)(50 * Camera.scale);
		
		try {
			BufferedImage image = ImageIO.read(new File("Assets/Modes.png"));
			g.drawImage(image, xUsable, yUsable, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void mousePressed(MouseEvent e) {
		if(editing) {
			placing = true;
			placingX = e.getX()*Camera.zoom + Game.camera.getCameraPos().getX();
			placingY = e.getY()*Camera.zoom + Game.camera.getCameraPos().getY();
		}
		
		
		
	}
	public void mouseReleased(MouseEvent e) {
		if(editing) {
			placing = false;
			double xDifference = e.getX()*Camera.zoom+ Game.camera.getCameraPos().getX() - placingX;
			double yDifference = e.getY()*Camera.zoom+ Game.camera.getCameraPos().getY() - placingY;
			double y = 0;
			double x = 0;
			if(yDifference < 0) {
				y = e.getY()*Camera.zoom+ Game.camera.getCameraPos().getY();
				yDifference = Math.abs(yDifference);
			}
				
			else
				y = placingY;
			
			if(xDifference < 0) {
				x = e.getX()*Camera.zoom+ Game.camera.getCameraPos().getX();
				xDifference = Math.abs(xDifference);
			}
			else
				x = placingX;
				
			switch(mode) {
			case 0:
				
					
				if(xDifference != 0 && yDifference != 0)
					Map.addToMap("Block " + Math.round(x) + " " + Math.round(y) + " " + Math.round(xDifference) + " " + Math.round(yDifference));
				break;
			case 1:
				Map.addToMap("Jumper " + Math.round(e.getX()*Camera.zoom+ Game.camera.getCameraPos().getX()) + " " + Math.round(e.getY()*Camera.zoom+ Game.camera.getCameraPos().getY()));
				break;
			case 2:
				cutOutBlock(new Rectangle((int)x, (int)y, (int)xDifference, (int)yDifference));
				
				for(int i = Jumper.allJumpers.size()-1; i >= 0; i--) {
					if(Jumper.allJumpers.get(i).getXAxis() > x && Jumper.allJumpers.get(i).getXAxis() < x + xDifference) {
						if(Jumper.allJumpers.get(i).getYAxis() > y && Jumper.allJumpers.get(i).getYAxis() < y + yDifference) {
							Jumper.allJumpers.remove(Jumper.allJumpers.get(i));
						}
					}
				}
				Map.reWriteMap();
				break;
			}
			
			
		}
		
		
		
	}
	
	
	public void cutOutBlock(Rectangle rect) {
		for(int i = Block.allBlocks.size()-1; i >= 0; i--) {
			if(Block.allBlocks.get(i).checkcollision(rect)) {
				Rectangle blockRect = Block.allBlocks.get(i).getCollisionSize();
				Rectangle2D intersection = rect.createIntersection(blockRect);
				Rectangle rect1 = null;
				Rectangle rect2 = null;
				Rectangle rect3 = null;
				Rectangle rect4 = null;
				if(intersection.getX() > blockRect.getX()) {
					rect1 = new Rectangle((int)blockRect.getX(), (int)blockRect.getY(), (int)Math.abs(blockRect.getX() - intersection.getX()), (int)blockRect.getHeight());
				}
				if(intersection.getY() > blockRect.getY()) {
					rect2 = new Rectangle((int)blockRect.getX(), (int)blockRect.getY(), (int)blockRect.getWidth(), (int)Math.abs(blockRect.getY() - intersection.getY()));
				}
				if(intersection.getMaxX() < blockRect.getMaxX()) {
					rect3 = new Rectangle((int)intersection.getMaxX(), (int)blockRect.getY(), (int)Math.abs(intersection.getMaxX() - blockRect.getMaxX()), (int)blockRect.getHeight());
				}
				if(intersection.getMaxY() < blockRect.getMaxY()) {
					rect4 =  new Rectangle((int)blockRect.getX(), (int)intersection.getMaxY(), (int)blockRect.getWidth(), (int)Math.abs(intersection.getMaxY() - blockRect.getMaxY()));
				}
				Block.allBlocks.remove(Block.allBlocks.get(i));
				Map.reWriteMap();
				
				if(rect1 !=null)
					Map.addToMap("Block " + rect1.getX() + " " + rect1.getY() + " " + rect1.getWidth() + " " + rect1.getHeight());
				if(rect2 !=null)
					Map.addToMap("Block " + rect2.getX() + " " + rect2.getY() + " " + rect2.getWidth() + " " + rect2.getHeight());
				if(rect3 !=null)
					Map.addToMap("Block " + rect3.getX() + " " + rect3.getY() + " " + rect3.getWidth() + " " + rect3.getHeight());
				if(rect4 !=null)
					Map.addToMap("Block " + rect4.getX() + " " + rect4.getY() + " " + rect4.getWidth() + " " + rect4.getHeight());
				
			}
			
			
		}
		
	}
	
	
	
	
	
	
	
	

}
