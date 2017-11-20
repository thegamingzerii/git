package de.thegamingzerii.states;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thegamingzerii.editor.Map;
import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.Block;
import de.thegamingzerii.objects.Camera;
import de.thegamingzerii.objects.DeadlyBlock;
import de.thegamingzerii.objects.Jumper;
import de.thegamingzerii.objects.Rope;
import de.thegamingzerii.objects.Slope;
import de.thegamingzerii.objects.TextureBlock;

public class EditingState extends State{

	private boolean placing = false;
	private double placingX = 0;
	private double placingY = 0;
	public static boolean editing = false;
	public static int mode = 0;
	public static int textureMode = 0;
	private ArrayList<Integer> xSnaps = new ArrayList<Integer>();
	private ArrayList<Integer> ySnaps = new ArrayList<Integer>();
	
	private final int differentBlockTextures = 6;
	
	@Override
	public void init() {
		for(int i = -1000; i < 1000; i++) {
			xSnaps.add(128*i);
			ySnaps.add(128*i);
		}
		
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
		
		if(Camera.zoom != 4) {
			Game.camera.moveCamera((GameState.player.getXAxis() - Game.camera.getCameraPos().getX() - (Game.camera.getWidth()/2)) * 0.1 * delta, 
					(GameState.player.getYAxis() - Game.camera.getCameraPos().getY() - (Game.camera.getHeight()/2)) * 0.1 * delta);
			Game.camera.reFrame(4);
		}
			
		
	}

	@Override
	public void paint(Graphics2D g) {
		Game.ingameState.paint(g);
		for(int i = 0; i < xSnaps.size(); i++) {
			g.drawLine((int)((xSnaps.get(i) - Game.camera.getCameraPos().getX())/Camera.zoom), 0, (int)((xSnaps.get(i) - Game.camera.getCameraPos().getX())/Camera.zoom), 1920);
		}
		for(int i = 0; i < ySnaps.size(); i++) {
			g.drawLine(0, (int)((ySnaps.get(i) - Game.camera.getCameraPos().getY())/Camera.zoom), 1920, (int)((ySnaps.get(i) - Game.camera.getCameraPos().getY())/Camera.zoom));
		}

		
		
		
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
				g.drawOval((int)(MouseInfo.getPointerInfo().getLocation().getX() - (50*Camera.scale)), (int)(MouseInfo.getPointerInfo().getLocation().getY() - (50*Camera.scale)), (int)(100*Camera.scale), (int)(100*Camera.scale));
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
			
			case 3:
				if(xDiff > 0 || yDiff > 0) {
					g.drawLine((int)Math.round(x), (int)Math.round(y), (int)Math.round(x), (int)Math.round(y2));
				}
				break;
			case 4:
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
			case 6:
				if(xDiff > 0 || yDiff > 0) {
					g.drawLine((int)Math.round(x), (int)Math.round(y), (int)Math.round(x2), (int)Math.round(y2));
				}
				break;
			}
			
			  
		}
		
		if(mode == 5) {
			TextureBlock.drawCurrentlyPlacing(g, textureMode%differentBlockTextures, (int) Math.round(MouseInfo.getPointerInfo().getLocation().getX()), (int) Math.round(MouseInfo.getPointerInfo().getLocation().getY()));
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
			if(mode == 0 || mode == 4 || mode == 6) {
				placingX = getSnappedX(e.getX()*Camera.zoom+ Game.camera.getCameraPos().getX());
				placingY = getSnappedY(e.getY()*Camera.zoom+ Game.camera.getCameraPos().getY());
			}
		}
		
		
		
	}
	public void mouseReleased(MouseEvent e) {
		if(editing) {
			placing = false;
			double xDifference = e.getX()*Camera.zoom+ Game.camera.getCameraPos().getX() - placingX;
			double yDifference = e.getY()*Camera.zoom+ Game.camera.getCameraPos().getY() - placingY;
			if(mode == 0 || mode == 4) {
				xDifference = getSnappedX(e.getX()*Camera.zoom+ Game.camera.getCameraPos().getX()- placingX);
				yDifference = getSnappedY(e.getY()*Camera.zoom+ Game.camera.getCameraPos().getY()- placingY);
			}
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
				for(int i = Rope.allRopes.size()-1; i >= 0; i--) {
					if(Rope.allRopes.get(i).getXAxis() > x && Rope.allRopes.get(i).getXAxis() < x + xDifference) {
						if(Rope.allRopes.get(i).getYAxis() > y && Rope.allRopes.get(i).getYAxis() < y + yDifference) {
							Rope.allRopes.remove(Rope.allRopes.get(i));
						}
					}
				}
				for(int i = Slope.allSlopes.size()-1; i >= 0; i--) {
					if(Slope.allSlopes.get(i).getXAxis() > x && Slope.allSlopes.get(i).getXAxis() < x + xDifference) {
						if(Slope.allSlopes.get(i).getYAxis() > y && Slope.allSlopes.get(i).getYAxis() < y + yDifference) {
							Slope.allSlopes.remove(Slope.allSlopes.get(i));
						}
					}else {
						if(Slope.allSlopes.get(i).getX2() > x && Slope.allSlopes.get(i).getX2() < x + xDifference) {
							if(Slope.allSlopes.get(i).getY2() > y && Slope.allSlopes.get(i).getY2() < y + yDifference) {
								Slope.allSlopes.remove(Slope.allSlopes.get(i));
							}
						}
					}
				}
				Map.reWriteMap();
				break;
			case 3:
				Map.addToMap("Rope " + Math.round(x) + " " + Math.round(y) + " " + Math.round(yDifference));
				break;
			case 4:
				if(xDifference != 0 && yDifference != 0)
					Map.addToMap("DeadlyBlock " + Math.round(x) + " " + Math.round(y) + " " + Math.round(xDifference) + " " + Math.round(yDifference));
				break;
			case 5:
								
				double distanceX = 10000;
				int indexX = 0;
				double distanceY = 10000;
				int indexY = 0;
				for(int i = 0; i < xSnaps.size(); i++) {
					if(e.getX()*Camera.zoom+ Game.camera.getCameraPos().getX() - xSnaps.get(i) > 0 && e.getX()*Camera.zoom+ Game.camera.getCameraPos().getX() - xSnaps.get(i) < distanceX) {
						
						indexX = i;
						distanceX = e.getX()*Camera.zoom+ Game.camera.getCameraPos().getX() - xSnaps.get(i);
						
					}

				}
				
				
				for(int i = 0; i < ySnaps.size(); i++) {
					if(e.getY()*Camera.zoom+ Game.camera.getCameraPos().getY() - ySnaps.get(i) > 0 && e.getY()*Camera.zoom+ Game.camera.getCameraPos().getY() - ySnaps.get(i) < distanceY) {
						
						indexY = i;
						distanceY = e.getY()*Camera.zoom+ Game.camera.getCameraPos().getY() - ySnaps.get(i);
						
					}

				}
				if(!TextureBlock.deleteTextureBlock(xSnaps.get(indexX), ySnaps.get(indexY)))
					Map.addToMap("TextureBlock " + xSnaps.get(indexX) + " " + ySnaps.get(indexY) + " " + textureMode%differentBlockTextures);
				break;
			case 6:
				Map.addToMap("Slope " + Math.round(placingX) + " " + Math.round(placingY) + " " + Math.round(getSnappedX(e.getX()*Camera.zoom+ Game.camera.getCameraPos().getX())) + " " + Math.round(getSnappedY(e.getY()*Camera.zoom+ Game.camera.getCameraPos().getY())));
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
		
		
		
		for(int i = DeadlyBlock.allDeadlyBlocks.size()-1; i >= 0; i--) {
			if(DeadlyBlock.allDeadlyBlocks.get(i).checkProximity(rect)) {
				Rectangle blockRect = DeadlyBlock.allDeadlyBlocks.get(i).getCollisionSize();
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
				DeadlyBlock.allDeadlyBlocks.remove(DeadlyBlock.allDeadlyBlocks.get(i));
				Map.reWriteMap();
				
				if(rect1 !=null)
					Map.addToMap("DeadlyBlock " + rect1.getX() + " " + rect1.getY() + " " + rect1.getWidth() + " " + rect1.getHeight());
				if(rect2 !=null)
					Map.addToMap("DeadlyBlock " + rect2.getX() + " " + rect2.getY() + " " + rect2.getWidth() + " " + rect2.getHeight());
				if(rect3 !=null)
					Map.addToMap("DeadlyBlock " + rect3.getX() + " " + rect3.getY() + " " + rect3.getWidth() + " " + rect3.getHeight());
				if(rect4 !=null)
					Map.addToMap("DeadlyBlock " + rect4.getX() + " " + rect4.getY() + " " + rect4.getWidth() + " " + rect4.getHeight());
				
			}
			
			
		}
		
	}
	
	
	
	
	public int getSnappedX(double x) {
		double distance = 10000;
		int index = 0;
		for(int i = 0; i < xSnaps.size(); i++) {
			if(Math.abs(x - xSnaps.get(i))< distance){
				if((x < 0 && xSnaps.get(i) <= 0) || (x > 0 && xSnaps.get(i) >= 0)) {
					distance = Math.abs(x - xSnaps.get(i));
					index = i;
				}
				
			}
		}
		return xSnaps.get(index);
	}
	
	public int getSnappedY(double y) {
		double distance = 10000;
		int index = 0;
		for(int i = 0; i < ySnaps.size(); i++) {
			if(Math.abs(y - ySnaps.get(i))< distance){
				if((y < 0 && ySnaps.get(i) <= 0) || (y > 0 && ySnaps.get(i) >= 0)) {
					distance = Math.abs(y - ySnaps.get(i));
					index = i;
				}
			}
		}
		return ySnaps.get(index);
	}
	
	
	
	
	
	
	
	

}
