package de.thegamingzerii.objects;

import java.awt.Rectangle;

public interface IInteract {
	
	void interact();
	
	boolean checkProximity(Rectangle rect);
	
	boolean onScreen();
}
