package de.thegamingzerii.objects;

import java.awt.Rectangle;

public interface ICollision {
	
	
	boolean checkcollision(Rectangle rect);
	
	double getXAxis();
	
	double getYAxis();
	
	void setX(int x);
	
	void setY(int y);
	
	Rectangle getCollisionSize();
	
	boolean onScreen();
	


}
