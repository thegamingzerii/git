package de.thegamingzerii.states;

import java.awt.Graphics2D;

public interface IState {
	void init();
	
	void update(double delta);
	
	void paint(Graphics2D g);
}
