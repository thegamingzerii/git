package de.thegamingzerii.utility;

import java.awt.geom.Point2D;

import de.thegamingzerii.maingame.Game;

public class ExtraMaths {

	public static int ActualModulo(double a, int b) {
		return (int)((a%b+b)%b);
	}
	
	public static boolean onScreen(Point2D point) {
		if(point.getX() > Game.actualCamera.getX() + Game.actualCamera.getWidth())
			return false;
		if(point.getY() > Game.actualCamera.getY() + Game.actualCamera.getHeight())
			return false;
		if(point.getX() < Game.actualCamera.getX())
			return false;
		if(point.getY() < Game.actualCamera.getY())
			return false;
		
		return true;
	}
}
