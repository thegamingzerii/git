package de.thegamingzerii.objects;

import java.awt.Rectangle;

import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.utility.CollisionChecker;
import de.thegamingzerii.utility.Constantes;

@SuppressWarnings("serial")
public class GravityObject extends JPanel implements ICollision{
	public double x = 0;
	public double y = 0;
	public double xSpeed = 0;
	public double ySpeed = 0;
	public double xAcc = 0;
	public double yAcc = 0;
	public double height = 128;
	public double width = 128;
	
	public GravityObject(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	public boolean gravity(double delta) {
		boolean bool = false;
		if(ySpeed < Constantes.TERMINAL_VELO) {
			ySpeed += (yAcc + Constantes.GRAVITY) * delta;
		}
		
		for(int i = 0; i < 4; i++) {
			y += ySpeed/4 * delta;
			if(CollisionChecker.CheckAllCollisions(this)) {
				bool = true;
				y-= ySpeed/4 * delta;
				yAcc = 0;
				ySpeed = 0;
			}
		}
		return bool;
		
	}

	@Override
	public boolean checkcollision(Rectangle rect) {
		return false;
	}




	@Override
	public double getXAxis() {
		return x;
	}




	@Override
	public double getYAxis() {
		return y;
	}




	@Override
	public void setX(int x) {
		this.x = x;
	}




	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public Rectangle getCollisionSize() {
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
	}

	@Override
	public boolean onScreen() {
		if(x > Game.camera.getX() + Game.camera.getWidth())
			return false;
		if(y > Game.camera.getY() + Game.camera.getHeight())
			return false;
		if(x + width < Game.camera.getX())
			return false;
		if(y + height < Game.camera.getY())
			return false;
		return true;
	}


}
