package de.thegamingzerii.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.thegamingzerii.states.GameState;

public class Jumper extends JPanel implements IInteract{

	public static ArrayList<Jumper> allJumpers =  new ArrayList<Jumper>();
	
	private double x = 0;
	private double y = 0;
	private double r = 100;
	String path = "Assets/Lantern.png";
	SpriteSheet sprite;
	
	public Jumper(double x, double y) {
		this.x = x;
		this.y = y;
		sprite =  new SpriteSheet(path, 32, 32, 8, 6, 4);
		allJumpers.add(this);
	}
	
	public void update(double delta) {
		sprite.update(delta);
	}
	
	@Override
	public void interact() {
		GameState.player.resetDoubleJump();
		
	}

	@Override
	public boolean checkProximity(Rectangle rect) {
		if(Math.abs(x - rect.x) > rect.getWidth() + 50 || Math.abs(y - rect.y) > rect.getHeight() + 50 ) {
			return false;
		}else {
			double circleDistanceX = Math.abs(x - rect.x);
		    double circleDistanceY = Math.abs(y - rect.y);

		    if (circleDistanceX > (rect.width/2 + r)) { return false; }
		    if (circleDistanceY > (rect.height/2 + r)) { return false; }

		    if (circleDistanceX <= (rect.width/2)) { return true; } 
		    if (circleDistanceY <= (rect.height/2)) { return true; }

		    double cornerDistance_sq = Math.pow(circleDistanceX - rect.width/2, 2) + Math.pow(circleDistanceY - rect.height/2, 2);

		    return (cornerDistance_sq <= (r*r));
		}
		
		
	}
	
	
	public void paint(Graphics2D g) {
		super.paintComponent(g);
		sprite.paint(g, x-64, y-64, 0);
	}
	
	public static void paintAllJumpers(Graphics2D g) {
		for(int i = 0; i < allJumpers.size(); i++) {
			allJumpers.get(i).paint(g);
		}
	}
	

}
