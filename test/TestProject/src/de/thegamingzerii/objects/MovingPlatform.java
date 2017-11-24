package de.thegamingzerii.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.states.GameState;
import de.thegamingzerii.utility.Vector2d;

@SuppressWarnings("serial")
public class MovingPlatform extends JPanel implements ICollision{

	public static ArrayList<MovingPlatform> allMovingPlatforms = new ArrayList<MovingPlatform>();
	
	Line2D line;
	Rectangle.Double platform;
	boolean direction = true;
	Vector2d vector;
	SpriteSheet sprite;
	
	public MovingPlatform(double x1, double y1, double x2, double y2) {
		line = new Line2D.Double(x1, y1, x2, y2);
		platform = new Rectangle.Double(x1-100, y1-20, 200, 40);
		vector = new Vector2d(line.getP1(), line.getP2());
		vector.normalize();
		allMovingPlatforms.add(this);
		sprite = new SpriteSheet("Assets/Platform.png", 200, 40, 0, 1, 1);
	}
	
	public MovingPlatform(MovingPlatform mPlatform) {
		line = mPlatform.line;
		platform = mPlatform.platform;
		sprite = mPlatform.sprite;
	}
	
	public void update(double delta) {
		if(direction) {
			platform.x -= vector.getX() * delta * 3;
			platform.y -= vector.getY() * delta * 3;
			if(playerOnPlatform()) {
				GameState.player.x -= vector.getX() * delta * 3;
			}
			if(Math.abs(platform.x - line.getX2()) < 100 && Math.abs(platform.y - line.getY2()) < 40) {
				direction = false;
			}
		}else {
			platform.x += vector.getX() * delta * 3;
			platform.y += vector.getY() * delta * 3;
			if(playerOnPlatform()) {
				GameState.player.x += vector.getX() * delta * 3;
			}
			if(Math.abs(platform.x - line.getX1()) < 100 && Math.abs(platform.y - line.getY1()) < 40) {
				direction = true;
			}
		}
		
		
	}
	
	
	public boolean playerOnPlatform() {
		return(checkcollision(new Rectangle(GameState.player.getCollisionSize().x, GameState.player.getCollisionSize().y+50, GameState.player.getCollisionSize().width, GameState.player.getCollisionSize().height)));
	}
	
	public void paint(Graphics2D g) {
		int xUsable = (int) ((line.getX1() - Game.camera.getCameraPos().getX()) * Game.camera.scale);
		int yUsable = (int)((line.getY1() - Game.camera.getCameraPos().getY()) * Game.camera.scale);
		int xEndUsable = (int) ((line.getX2() - Game.camera.getCameraPos().getX()) * Game.camera.scale);
		int yEndUsable = (int)((line.getY2() - Game.camera.getCameraPos().getY()) * Game.camera.scale);
        g.draw(new Line2D.Float(xUsable, yUsable, xEndUsable, yEndUsable));
        
        sprite.paintComponent(g, platform.x, platform.y, 0, false);
        if(Game.drawHitBoxes) {
        	g.setColor(Color.ORANGE);
        	g.drawRect((int)((platform.x- Game.camera.getCameraPos().getX()) * Game.camera.scale), (int)((platform.y- Game.camera.getCameraPos().getY()) * Game.camera.scale), (int)(platform.width*Game.camera.scale), (int)(platform.height*Game.camera.scale));
        	g.setColor(Color.black);
        }
	}
	
	
	@Override
	public boolean checkcollision(Rectangle rect) {
		return rect.intersects(getCollisionSize());
	}

	@Override
	public double getXAxis() {
		return line.getX1();
	}

	@Override
	public double getYAxis() {
		return line.getY1();
	}
	
	public double getX2() {
		return line.getX2();
	}
	
	public double getY2() {
		return line.getY2();
	}

	@Override
	public void setX(int x) {
		
	}

	@Override
	public void setY(int y) {
		
	}

	@Override
	public Rectangle getCollisionSize() {
		return new Rectangle((int)platform.x, (int)platform.y, (int)platform.width, (int)platform.height);
	}

	@Override
	public boolean onScreen() {
		if(!(platform.getX() > Game.actualCamera.getX() + Game.actualCamera.getWidth()) && !(platform.getY() > Game.actualCamera.getY() + Game.actualCamera.getHeight())
				&& !(platform.getMaxX() < Game.actualCamera.getX()) && !(platform.getMaxY() < Game.actualCamera.getY()))
			return true;
		if(!(line.getX1() > Game.actualCamera.getX() + Game.actualCamera.getWidth()) && !(line.getY1() > Game.actualCamera.getY() + Game.actualCamera.getHeight())
				&& !(line.getX1() < Game.actualCamera.getX()) && !(line.getY1() < Game.actualCamera.getY()))
			return true;
		if(!(line.getX2() > Game.actualCamera.getX() + Game.actualCamera.getWidth()) && !(line.getY2() > Game.actualCamera.getY() + Game.actualCamera.getHeight())
				&& !(line.getX2() < Game.actualCamera.getX()) && !(line.getY2() < Game.actualCamera.getY()))
			return true;
		
		return false;
		
	}
	
	public String toString() {
		return "MovingPlatform " + line.getX1() + " " + line.getY1() + " " + line.getX2() + " " + line.getY2();
	}
	
	@Override
	public void buffer() {
		if(onScreen())
			Game.currentBuffer.add(this.getCopy());
	}

	@Override
	public IBufferable getCopy() {
		return new MovingPlatform(this);
	}

}
