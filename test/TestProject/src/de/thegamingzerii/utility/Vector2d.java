package de.thegamingzerii.utility;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Vector2d {

	private double x = 0;
	private double y = 0;
	
	public Vector2d(Point2D point1, Point2D point2) {
		x = point1.getX() - point2.getX();
		y = point1.getY() - point2.getY();
	}
	
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double length() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public void normalize() {
		double length = length();
		x = x/length;
		y = y/length;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public Vector2d getNormal() {
		return new Vector2d(-y, x);
	}
	
	public Line2D getLine(Point2D point) {
		return new Line2D.Double(point.getX(), point.getY(), point.getX() + x, point.getY() + y);
	}
}
