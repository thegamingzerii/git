package de.thegamingzerii.objects;

import java.awt.Rectangle;

import javax.swing.JPanel;

import de.thegamingzerii.utility.CollisionChecker;
import de.thegamingzerii.utility.Constantes;

public class GravityObject extends JPanel implements ICollision{
	double x = 0;
	double y = 0;
	double xSpeed = 0;
	double ySpeed = 0;
	double xAcc = 0;
	double yAcc = 0;
	double height = 64;
	double width = 64;
	
	public void gravity(double delta) {
		if(ySpeed < Constantes.TERMINAL_VELO) {
			ySpeed += (yAcc + Constantes.GRAVITY) * delta;
		}
		
		y += ySpeed * delta;
		if(CollisionChecker.CheckAllCollisions(this)) {
			y-= ySpeed * delta;
			yAcc = 0;
			ySpeed = 0;
		}
	}

	@Override
	public boolean checkcollision(ICollision C) {
		// TODO Auto-generated method stub
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
}
