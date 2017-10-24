package de.thegamingzerii.states;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.thegamingzerii.objects.Button;
import de.thegamingzerii.objects.ButtonText;

public class MenuState extends State{
	Button[] buttons = {new Button(200, 0, new ButtonText(62, 110, 80, "Start game")),
						new Button(400, 2, new ButtonText(62, 120, 80, "Exit Game"))};
	@Override
	public void update(double delta) {
		for(int i = 0; i < buttons.length; i++){
			buttons[i].hover(MouseInfo.getPointerInfo().getLocation().getX(), MouseInfo.getPointerInfo().getLocation().getY());
		}
	}
	
	
	@Override
	public void init() {
	// TODO Auto-generated method stub
	
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
