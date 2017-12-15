package de.thegamingzerii.utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.IBufferable;

public class DisplayText implements IBufferable{

	public static ArrayList<DisplayText> allDisplayTexts = new ArrayList<DisplayText>();
	
	private String text;
	private double x;
	private double y;
	private int size;
	private double time;
	
	public DisplayText(String text, double x, double y, int size, double time) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.size = size;
		this.time = time;
		
		allDisplayTexts.add(this);
	}
	
	public DisplayText(DisplayText dp) {
		this.text = dp.text;
		this.x = dp.x;
		this.y = dp.y;
		this.size = dp.size;
		this.time = dp.time;		
	}
	
	public void paint(Graphics2D g) {
		String currentFont = g.getFont().toString();
		g.setFont(new Font(currentFont, Font.PLAIN, size));
		g.setColor(Color.RED);
		g.drawString(text, (int)x,  (int)y);
	}
	
	
	public void update(double delta) {
		time -= delta;
		if(time <= 0)
			allDisplayTexts.remove(this);
	}

	@Override
	public boolean onScreen() {
		return true;
	}

	@Override
	public void buffer() {
		if(onScreen())
			Game.currentBuffer.add(this.getCopy());
	}

	@Override
	public IBufferable getCopy() {
		return new DisplayText(this);
	}
	
}
