package de.thegamingzerii.spawners;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.IBufferable;
import de.thegamingzerii.utility.Constantes.SpawnerType;

public class Spawner implements IBufferable{

	public static ArrayList<Spawner> allSpawners = new ArrayList<Spawner>();
	
	double x;
	double y;
	SpawnerType type;
	
	public Spawner(double x, double y, SpawnerType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		allSpawners.add(this);
	}
	
	public Spawner(Spawner spawner) {
		this.x = spawner.x;
		this.y = spawner.y;
		this.type = spawner.type;
	}
	
	public void update(double delta) {
		if(Math.random() < 0.001 && onScreen())
			new Leaf(x-100 + Math.random()*200, y-100 + Math.random()*200);
	}
	
	public String toString() {
		return "Spawner " + x + " " + y + " " + 0;
	}
	@Override
	public boolean onScreen() {
		if(x-100 > Game.actualCamera.getX() + Game.actualCamera.getWidth())
			return false;
		if(y-100 > Game.actualCamera.getY() + Game.actualCamera.getHeight())
			return false;
		if(x+100 < Game.actualCamera.getX())
			return false;
		if(y+100 < Game.actualCamera.getY())
			return false;
		return true;
	}

	@Override
	public void buffer() {
		if(onScreen())
			Game.currentBuffer.add(this.getCopy());
	}

	@Override
	public IBufferable getCopy() {
		return new Spawner(this);
	}
	
	@Override
	public void paint(Graphics2D g) {
		if(Game.drawHitBoxes) {
			g.setColor(Color.blue);
			int xUsable = (int) ((x - 100 - Game.camera.getCameraPos().getX()) * Game.camera.scale);
			int yUsable = (int)((y - 100 - Game.camera.getCameraPos().getY()) * Game.camera.scale);
			g.drawOval(xUsable, yUsable, (int)(200 * Game.camera.scale), (int)(200 * Game.camera.scale));
		}
	}
}
