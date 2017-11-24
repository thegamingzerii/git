package de.thegamingzerii.objects;

import java.awt.Rectangle;

public interface IInteract extends IBufferable{
	
	void interact(Boolean keyPressed);
	
	boolean checkProximity(Rectangle rect);
	
	boolean onScreen();
	
}
