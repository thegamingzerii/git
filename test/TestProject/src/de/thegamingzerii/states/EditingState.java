package de.thegamingzerii.states;

import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.thegamingzerii.editor.Map;
import de.thegamingzerii.logicParts.Gate;
import de.thegamingzerii.logicParts.Lever;
import de.thegamingzerii.logicParts.LogicTile;
import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.BackgroundObject;
import de.thegamingzerii.objects.BreakableWall;
import de.thegamingzerii.objects.DeadlyBlock;
import de.thegamingzerii.objects.Jumper;
import de.thegamingzerii.objects.MovingPlatform;
import de.thegamingzerii.objects.Rope;
import de.thegamingzerii.objects.Slope;
import de.thegamingzerii.objects.TextureBlock;
import de.thegamingzerii.utility.DisplayText;

@SuppressWarnings("serial")
public class EditingState extends State{

	private boolean placing = false;
	private double placingX = 0;
	private double placingY = 0;
	public static boolean editing = false;
	public static int mode = 0;
	public static int textureMode = 0;
	private ArrayList<Integer> xSnaps = new ArrayList<Integer>();
	private ArrayList<Integer> ySnaps = new ArrayList<Integer>();
	
	private final int differentBlockTextures = 12;
	private final int differentBackgroundTextures = 3;
	public static boolean background = false;
	
	@Override
	public void init() {
		for(int i = -1000; i < 1000; i++) {
			xSnaps.add(128*i);
			ySnaps.add(128*i);
		}
		
	}

	@Override
	public void update(double delta) {
		
		for(int i = 0; i < DisplayText.allDisplayTexts.size(); i++) {
			DisplayText.allDisplayTexts.get(i).update(delta);
		}

		if(MouseInfo.getPointerInfo().getLocation().getX() > 1900)
			Game.actualCamera.moveCamera(50, 0);
		if(MouseInfo.getPointerInfo().getLocation().getY() > 1060)
			Game.actualCamera.moveCamera(0, 50);
		if(MouseInfo.getPointerInfo().getLocation().getX() < 20)
			Game.actualCamera.moveCamera(-50, 0);
		if(MouseInfo.getPointerInfo().getLocation().getY() < 20)
			Game.actualCamera.moveCamera(0, -50);
		
		if(Game.actualCamera.zoom != 4) {
			Game.actualCamera.moveCamera((GameState.player.getXAxis() - Game.actualCamera.getCameraPos().getX() - (Game.actualCamera.getWidth()/2)) * 0.1 * delta, 
					(GameState.player.getYAxis() - Game.actualCamera.getCameraPos().getY() - (Game.actualCamera.getHeight()/2)) * 0.1 * delta);
			Game.actualCamera.reFrame(4);
			
			
		}
			
		
	}
	
	public void buffer() {
		Game.ingameState.buffer();
	}

	@Override
	public void paint(Graphics2D g) {
		for(int i = 0; i < xSnaps.size(); i++) {
			g.drawLine((int)((xSnaps.get(i) - Game.camera.getCameraPos().getX())/Game.camera.zoom), 0, (int)((xSnaps.get(i) - Game.camera.getCameraPos().getX())/Game.camera.zoom), 1920);
		}
		for(int i = 0; i < ySnaps.size(); i++) {
			g.drawLine(0, (int)((ySnaps.get(i) - Game.camera.getCameraPos().getY())/Game.camera.zoom), 1920, (int)((ySnaps.get(i) - Game.camera.getCameraPos().getY())/Game.camera.zoom));
		}

		
		
		
		if(placing) {
			double x = (placingX - Game.camera.getCameraPos().getX()) / Game.camera.zoom;
			double y = (placingY - Game.camera.getCameraPos().getY()) / Game.camera.zoom;
			double x2 = MouseInfo.getPointerInfo().getLocation().getX();
			double y2 = MouseInfo.getPointerInfo().getLocation().getY();
			double xDiff = x2 - x;
			double yDiff = y2 - y;
			switch(mode) {
			case 0:
				
				g.drawLine((int)Math.round(x), (int)Math.round(y), (int)Math.round(x2), (int)Math.round(y2));
				break;
			
			case 1:
				g.drawOval((int)(MouseInfo.getPointerInfo().getLocation().getX() - (50*Game.camera.scale)), (int)(MouseInfo.getPointerInfo().getLocation().getY() - (50*Game.camera.scale)), (int)(100*Game.camera.scale), (int)(100*Game.camera.scale));
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
				g.drawLine((int)Math.round(x), (int)Math.round(y), (int)Math.round(x2), (int)Math.round(y2));
				break;
			case 6:
				if(xDiff > 0 || yDiff > 0) {
					g.drawLine((int)Math.round(x), (int)Math.round(y), (int)Math.round(x2), (int)Math.round(y2));
				}
				break;
			case 7:
				if(xDiff > 0 || yDiff > 0) {
					g.drawLine((int)Math.round(x), (int)Math.round(y), (int)Math.round(x2), (int)Math.round(y2));
				}
				break;
			case 11:
				g.drawLine((int)Math.round(x), (int)Math.round(y), (int)Math.round(x2), (int)Math.round(y2));
				break;
			}
			
			  
		}
		
		if(mode == 5) {
			TextureBlock.drawCurrentlyPlacing(g, textureMode%differentBlockTextures, (int) Math.round(MouseInfo.getPointerInfo().getLocation().getX()), (int) Math.round(MouseInfo.getPointerInfo().getLocation().getY()));
		}
		
		if(mode == 8)
			BackgroundObject.drawCurrentlyPlacing(g, textureMode%differentBackgroundTextures, (int) ((getSnappedLeftX(MouseInfo.getPointerInfo().getLocation().getX())- Game.camera.getCameraPos().getX()) / Game.camera.zoom), (int) ((getSnappedUpY(MouseInfo.getPointerInfo().getLocation().getY())- Game.camera.getCameraPos().getY()) / Game.camera.zoom));

		if(mode == 9)
			Gate.drawCurrentlyPlacing(g, (int) ((getSnappedLeftX(MouseInfo.getPointerInfo().getLocation().getX())- Game.camera.getCameraPos().getX()) / Game.camera.zoom), (int) ((getSnappedUpY(MouseInfo.getPointerInfo().getLocation().getY())- Game.camera.getCameraPos().getY()) / Game.camera.zoom));
		
		if(mode == 10)
			Lever.drawCurrentlyPlacing(g, (int) ((getSnappedLeftX(MouseInfo.getPointerInfo().getLocation().getX()) + 64- Game.camera.getCameraPos().getX()) / Game.camera.zoom), (int) ((getSnappedUpY(MouseInfo.getPointerInfo().getLocation().getY()) + 64 - Game.camera.getCameraPos().getY()) / Game.camera.zoom));
		
		if(mode == 13)
			BreakableWall.drawCurrentlyPlacing(g, (int) ((getSnappedLeftX(MouseInfo.getPointerInfo().getLocation().getX())- Game.camera.getCameraPos().getX()) / Game.camera.zoom), (int) ((getSnappedUpY(MouseInfo.getPointerInfo().getLocation().getY())- Game.camera.getCameraPos().getY()) / Game.camera.zoom));
		
		 super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int xUsable = (int) (50 * Game.camera.scale);
		int yUsable = (int)(50 * Game.camera.scale);
		
		try {
			BufferedImage image = ImageIO.read(new File("Assets/Modes.png"));
			g.drawImage(image, xUsable, yUsable, this);
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
	}
	
	
	public void mousePressed(MouseEvent e) {
		if(editing) {
			placing = true;
			placingX = e.getX()*Game.camera.zoom + Game.camera.getCameraPos().getX();
			placingY = e.getY()*Game.camera.zoom + Game.camera.getCameraPos().getY();
			if(mode == 0 || mode == 4 || mode == 6 || mode == 7 || mode == 9 || mode == 13) {
				placingX = getSnappedX(e.getX()*Game.camera.zoom+ Game.camera.getCameraPos().getX());
				placingY = getSnappedY(e.getY()*Game.camera.zoom+ Game.camera.getCameraPos().getY());
			}
		}
		
		
		
	}
	public void mouseReleased(MouseEvent e) {
		if(editing) {
			placing = false;
			double xDifference = e.getX()*Game.camera.zoom+ Game.camera.getCameraPos().getX() - placingX;
			double yDifference = e.getY()*Game.camera.zoom+ Game.camera.getCameraPos().getY() - placingY;
			if(mode == 0 || mode == 4) {
				xDifference = getSnappedX(e.getX()*Game.camera.zoom+ Game.camera.getCameraPos().getX()- placingX);
				yDifference = getSnappedY(e.getY()*Game.camera.zoom+ Game.camera.getCameraPos().getY()- placingY);
			}
			double y = 0;
			double x = 0;
			if(yDifference < 0) {
				y = e.getY()*Game.camera.zoom+ Game.camera.getCameraPos().getY();
				yDifference = Math.abs(yDifference);
			}
				
			else
				y = placingY;
			
			if(xDifference < 0) {
				x = e.getX()*Game.camera.zoom+ Game.camera.getCameraPos().getX();
				xDifference = Math.abs(xDifference);
			}
			else
				x = placingX;
				
			switch(mode) {
			case 0:
				Map.addToMap("Obstacle " + Math.round(placingX) + " " + Math.round(placingY) + " " + Math.round(getSnappedX(e.getX()*Game.camera.zoom+ Game.camera.getCameraPos().getX())) + " " + Math.round(getSnappedY(e.getY()*Game.camera.zoom+ Game.camera.getCameraPos().getY())));
				break;
			case 1:
				Map.addToMap("Jumper " + Math.round(e.getX()*Game.camera.zoom+ Game.camera.getCameraPos().getX()) + " " + Math.round(e.getY()*Game.camera.zoom+ Game.camera.getCameraPos().getY()));
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
				for(int i = MovingPlatform.allMovingPlatforms.size()-1; i >= 0; i--) {
					if(MovingPlatform.allMovingPlatforms.get(i).getXAxis() > x && MovingPlatform.allMovingPlatforms.get(i).getXAxis() < x + xDifference) {
						if(MovingPlatform.allMovingPlatforms.get(i).getYAxis() > y && MovingPlatform.allMovingPlatforms.get(i).getYAxis() < y + yDifference) {
							MovingPlatform.allMovingPlatforms.remove(MovingPlatform.allMovingPlatforms.get(i));
						}
					}else {
						if(MovingPlatform.allMovingPlatforms.get(i).getX2() > x && MovingPlatform.allMovingPlatforms.get(i).getX2() < x + xDifference) {
							if(MovingPlatform.allMovingPlatforms.get(i).getY2() > y && MovingPlatform.allMovingPlatforms.get(i).getY2() < y + yDifference) {
								MovingPlatform.allMovingPlatforms.remove(MovingPlatform.allMovingPlatforms.get(i));
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
				Map.addToMap("DeadlyBlock " + Math.round(placingX) + " " + Math.round(placingY) + " " + Math.round(getSnappedX(e.getX()*Game.camera.zoom+ Game.camera.getCameraPos().getX())) + " " + Math.round(getSnappedY(e.getY()*Game.camera.zoom+ Game.camera.getCameraPos().getY())));
				break;
			case 5:
				
				if(!TextureBlock.deleteTextureBlock(getSnappedLeftX(e.getX()), getSnappedUpY(e.getY())))
					Map.addToMap("TextureBlock " + getSnappedLeftX(e.getX()) + " " + getSnappedUpY(e.getY()) + " " + textureMode%differentBlockTextures + " " + background);
				break;
			case 6:
				Map.addToMap("Slope " + Math.round(placingX) + " " + Math.round(placingY) + " " + Math.round(getSnappedX(e.getX()*Game.camera.zoom+ Game.camera.getCameraPos().getX())) + " " + Math.round(getSnappedY(e.getY()*Game.camera.zoom+ Game.camera.getCameraPos().getY())));
				break;
			case 7:
				Map.addToMap("MovingPlatform " + Math.round(placingX) + " " + Math.round(placingY) + " " + Math.round(getSnappedX(e.getX()*Game.camera.zoom+ Game.camera.getCameraPos().getX())) + " " + Math.round(getSnappedY(e.getY()*Game.camera.zoom+ Game.camera.getCameraPos().getY())));
				break;
			case 8:
				if(!BackgroundObject.deletBackgroundObject(getSnappedLeftX(e.getX()), getSnappedUpY(e.getY()), textureMode%differentBackgroundTextures))
					Map.addToMap("BackgroundObject " + getSnappedLeftX(e.getX()) + " " + getSnappedUpY(e.getY()) + " " + textureMode%differentBackgroundTextures);
				break;
			case 9:
				Map.addToMap("Gate " + getSnappedLeftX(e.getX()) + " " + getSnappedUpY(e.getY()) + " " + "false -1 -1");
				break;
			case 10:
				Map.addToMap("Lever " + (getSnappedLeftX(e.getX()) + 64) + " " + (getSnappedUpY(e.getY()) + 64) + " " + "false -1 -1");
				break;
			case 11:
				ArrayList<ArrayList<? extends LogicTile>> list = LogicTile.getAllTileLists();
				Point2D point1 = new Point2D.Double(placingX, placingY);
				Point2D point2 = new Point2D.Double(e.getX()*Game.camera.zoom+ Game.camera.getCameraPos().getX(), e.getY()*Game.camera.zoom+ Game.camera.getCameraPos().getY());
				LogicTile tile1 = null;
				LogicTile tile2 = null;
				for(int i = 0; i < list.size(); i++) {
					for(int j = 0; j < list.get(i).size(); j++) {
						if(list.get(i).get(j).connectsTo(point1))
							tile1 = list.get(i).get(j);
					}
				}
				
				for(int i = 0; i < list.size(); i++) {
					for(int j = 0; j < list.get(i).size(); j++) {
						if(list.get(i).get(j).connectsTo(point2) && list.get(i).get(j) != tile1)
							tile2 = list.get(i).get(j);
					}
				}
				
				
				if(tile1 != null && tile2 != null && tile2.Activateable()) {
					tile1.connectTo(tile2.getId());
				}
				Map.reWriteMap();
				break;
			case 12:
				Map.addToMap("Spawner " + Math.round(e.getX()*Game.camera.zoom+ Game.camera.getCameraPos().getX()) + " " + Math.round(e.getY()*Game.camera.zoom+ Game.camera.getCameraPos().getY()) + " " + 0);
				break;
			case 13:
				Map.addToMap("BreakableWall " + getSnappedLeftX(e.getX()) + " " + getSnappedUpY(e.getY()) + " " + "false");
				break;
				
				
				
			}
			
			
		}
		
		
		
	}
	
	
	public void cutOutBlock(Rectangle rect) {
		
		
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
	
	public int getSnappedLeftX(double x){
		double distanceX = 10000;
		int index = 0;
		for(int i = 0; i < xSnaps.size(); i++) {
			if(x *Game.camera.zoom+ Game.camera.getCameraPos().getX() - xSnaps.get(i) > 0 && x*Game.camera.zoom+ Game.camera.getCameraPos().getX() - xSnaps.get(i) < distanceX) {
				
				index = i;
				distanceX = x*Game.camera.zoom+ Game.camera.getCameraPos().getX() - xSnaps.get(i);
				
			}

		}
		return xSnaps.get(index);
	}
	
	
	public int getSnappedUpY(double y){
		double distanceY = 10000;
		int index = 0;
		for(int i = 0; i < xSnaps.size(); i++) {
			if(y *Game.camera.zoom+ Game.camera.getCameraPos().getY() - ySnaps.get(i) > 0 && y*Game.camera.zoom+ Game.camera.getCameraPos().getY() - ySnaps.get(i) < distanceY) {
				
				index = i;
				distanceY = y*Game.camera.zoom+ Game.camera.getCameraPos().getY() - ySnaps.get(i);
				
			}

		}
		return ySnaps.get(index);
	}
	
	
	
	
	
	
	
	

}
