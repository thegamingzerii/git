package de.thegamingzerii.objects;

import java.awt.Graphics2D;

public interface IBufferable {
	public boolean onScreen();
	
	public void buffer();
	
	public IBufferable getCopy();
	
	public void paint(Graphics2D g);
}
