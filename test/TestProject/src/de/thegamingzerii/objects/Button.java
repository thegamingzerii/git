package de.thegamingzerii.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;

@SuppressWarnings("serial")
public class Button extends JPanel{

	double x = 1920/2 - 512/2;
	double y;
	double width = 512;
	double height = 128;
	int type;
	boolean mouseOver = false;
	String path;
	ButtonText text;
	
	public Button(double y, int type, ButtonText text) {
		this.y = y;
		this.type = type;
		this.text = text;
	}
	
	public void hover(double x, double y) {

		mouseOver = (x * Camera.scale > this.x && x * Camera.scale < this.x + width && y * Camera.scale > this.y && y * Camera.scale < this.y + this.height);
			
	}
	
	public void pressed(double x, double y) {
		if(mouseOver) {
			switch (type) {
			case 0:
				Game.currentState = Game.ingameState;
				break;
			case 1:
				Game.currentState = Game.mainMenuState;
				break;
			case 2:
				System.exit(0);
				break;
			}
		}
			
	}
	
	public void paint(Graphics2D g) {
		Game.ingameState.paint(g);

		if(mouseOver)
			path = "Assets/MenuGreen.png";
		else
			path = "Assets/MenuBlack.png";
		super.paint(g);		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int xUsable = (int) (x * Camera.scale);
		int yUsable = (int)(y  * Camera.scale);
		try {
			BufferedImage image = ImageIO.read(new File(path));
			g.drawImage(image, xUsable, yUsable, this);
			String currentFont = g.getFont().toString();
			g.setFont(new Font(currentFont, Font.PLAIN, text.size));
		     
		    g.setColor(Color.white);
			g.drawString(text.text, (int) (xUsable + text.x * Camera.scale) , (int) (yUsable + text.y * Camera.scale));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
