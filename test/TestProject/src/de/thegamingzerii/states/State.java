package de.thegamingzerii.states;

import java.awt.Graphics2D;
import java.lang.reflect.Array;

import javax.swing.JPanel;

public interface State {
	Object[] objects = new Object[64];
	
	
	
	void init();
	
	void update(double delta);
	
	void paint(Graphics2D g);
	
	
}
