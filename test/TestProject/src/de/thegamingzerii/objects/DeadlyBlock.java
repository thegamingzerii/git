package de.thegamingzerii.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.states.GameState;

@SuppressWarnings("serial")
public class DeadlyBlock extends JPanel implements IInteract{

public static ArrayList<DeadlyBlock> allDeadlyBlocks =  new ArrayList<DeadlyBlock>();
	
	Line2D line;
	
	
	public DeadlyBlock(double x, double y, double x2, double y2) {
		line = new Line2D.Double(x, y, x2, y2);
		allDeadlyBlocks.add(this);
	}
	
	public DeadlyBlock(DeadlyBlock deadly) {
		this.line = deadly.line;
	}
	
	public Rectangle getCollisionSize() {
        return null;
	}
	
	public String toString() {
		return "DeadlyBlock " + line.getX1() + " " + line.getY1() + " " + line.getX2() + " " + line.getY2();
	}


	@Override
	public boolean onScreen() {
		return line.intersects(Game.actualCamera.getScreen());
	}

	@Override
	public void interact(Boolean keyPressed) {
		GameState.player.damage(1);
		
	}

	@Override
	public boolean checkProximity(Rectangle rect) {
		
		return line.intersects(rect);
	}
	
	@SuppressWarnings("static-access")
	public void paint(Graphics2D g) {
		if(Game.drawHitBoxes) {
			int xUsable = (int) ((line.getX1() - Game.camera.getCameraPos().getX()-4) * Game.camera.scale);
			int yUsable = (int)((line.getY1() - Game.camera.getCameraPos().getY()) * Game.camera.scale);
			int xEndUsable = (int) ((line.getX2() - Game.camera.getCameraPos().getX()-4) * Game.camera.scale);
			int yEndUsable = (int)((line.getY2() - Game.camera.getCameraPos().getY()) * Game.camera.scale);
			g.setColor(Color.ORANGE);
	        g.draw(new Line2D.Float(xUsable, yUsable, xEndUsable, yEndUsable));
	        g.setColor(Color.black);
		}
	}
	
	@Override
	public void buffer() {
		if(onScreen())
			Game.currentBuffer.add(this.getCopy());
	}

	@Override
	public IBufferable getCopy() {
		return new DeadlyBlock(this);
	}

}
