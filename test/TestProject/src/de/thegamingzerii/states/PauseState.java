package de.thegamingzerii.states;


import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;

import de.thegamingzerii.objects.Button;
import de.thegamingzerii.objects.ButtonText;

@SuppressWarnings("serial")
public class PauseState extends State{
	Button[] buttons = {new Button(200, 0, new ButtonText(62, 50, 80, "Continue game")),
						new Button(400, 1, new ButtonText(62, 10, 80, "Exit to Main Menu"))};
	@Override
	public void update(double delta) {
		for(int i = 0; i < buttons.length; i++){
			buttons[i].hover(MouseInfo.getPointerInfo().getLocation().getX(), MouseInfo.getPointerInfo().getLocation().getY());
		}
	}


	@Override
	public void init() {
		
	}

	
	public void paint(Graphics2D g) {
		super.paint(g);
		for(int i = 0; i < buttons.length; i++){
			buttons[i].paint(g);
		}
		
		
	}
	
	
	public void mousePressed(MouseEvent e) {
		for(int i = 0; i < buttons.length; i++){
			buttons[i].pressed(e.getX(), e.getY());
		}

	}

}
