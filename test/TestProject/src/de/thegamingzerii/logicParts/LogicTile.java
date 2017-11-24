package de.thegamingzerii.logicParts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.objects.Camera;

public class LogicTile {
	
	public static int nextId = 0;
	public static HashMap<Integer, LogicTile> tiles = new HashMap<Integer, LogicTile>();
	
	int id;
	int connectedTo;
	
	public LogicTile(int id, int connectedTo) {
		if(id == -1) {
			this.id = nextId;
			nextId++;
		}
		else
			this.id = id;
		tiles.put(id, this);
		this.connectedTo = connectedTo;
	}
	
	public boolean connectsTo(Point2D point) {
		return false;
	}
	
	public int getId() {
		return id;
	}
	
	public int getConnectedTo() {
		return connectedTo;
	}
	
	public void activate(Boolean bool) {
		if(connectedTo != -1)
			if(tiles.containsKey(connectedTo))
				tiles.get(connectedTo).activate(bool);
	}
	
	public void connectTo(int id) {
		connectedTo = id;
	}
	
	public boolean Activateable() {
		return false;
	}
	
	public Point2D getLogicWirePoint() {
		return null;
	}
	
	
	public void paint(Graphics2D g) {
		if(connectedTo != -1 && Game.drawHitBoxes) {
			int xUsable = (int) (getLogicWirePoint().getX() -  Game.camera.getCameraPos().getX()  * Game.camera.scale);
			int yUsable = (int) (getLogicWirePoint().getY() -  Game.camera.getCameraPos().getY()  * Game.camera.scale);
			int x2Usable = (int) (tiles.get(connectedTo).getLogicWirePoint().getX() -  Game.camera.getCameraPos().getX()  * Game.camera.scale);
			int y2Usable = (int) (tiles.get(connectedTo).getLogicWirePoint().getY() -  Game.camera.getCameraPos().getY()  * Game.camera.scale);
			g.setColor(Color.red);
			g.drawLine(xUsable, yUsable, x2Usable, y2Usable);
			g.setColor(Color.black);
		}
	}
	
	
	public static ArrayList<ArrayList<? extends LogicTile>> getAllTileLists(){
		ArrayList<ArrayList<? extends LogicTile>> returnList = new ArrayList<ArrayList<? extends LogicTile>>();
		returnList.add(Gate.allGates);
		returnList.add(Lever.allLevers);
		return returnList;
		
	}
	
	
}
