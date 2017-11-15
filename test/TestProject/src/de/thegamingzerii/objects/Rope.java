package de.thegamingzerii.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thegamingzerii.maingame.Game;
import de.thegamingzerii.states.GameState;

public class Rope extends JPanel implements IInteract{

	private Line2D line;
	private double length;
	private double originalX;
	private double blocked = 0;
	private double xSpeedMult;
	private double xAcc;
	private boolean swinging = false;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	private double swingTimer = 0;
	public static ArrayList<Rope> allRopes = new ArrayList<Rope>();
	private static final int SIZE = 256;
    private static double DELTA_THETA = Math.PI / 90;
    private Image image = RotatableImage.getImage(SIZE);
    private double dt = DELTA_THETA;
    private double theta = 1000;
	
	public Rope(double x, double y, double length) {
		line = new Line2D.Double(x, y, x, y + length);
		allRopes.add(this);
		this.length = length;
		this.originalX = x;
	}
	
	public void interactionBlocked(int time) {
	
		blocked = time;
	}
	
	@Override
	public void interact() {
		if(blocked <= 0 && !GameState.player.hanging) {
			GameState.player.rope = this;
			GameState.player.hanging = true;
			swinging = true;
			swingTimer = 0;
			xSpeedMult = Math.abs(GameState.player.xSpeed / 12);
			if(GameState.player.xSpeed < 0)
				swingTimer = Math.PI;
		}
		
		
	}

	@Override
	public boolean checkProximity(Rectangle rect) {
		// TODO Auto-generated method stub
		return line.intersects(rect);
	}

	@Override
	public boolean onScreen() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public void recieveMovement(boolean direction, boolean released) {
		if(released) {
			if(direction) {
				moveLeft = false;
			}
			else {
				moveRight = false;
			}
				
		}else {
			if(direction) {
				moveLeft = true;
				moveRight = false;
			}
			else {
				moveRight = true;
				moveLeft = false;
			}
		}
	}
	
	public void update(double delta) {
		blocked -= delta;
		if(swinging)
			swingTimer += delta * 0.05;
			
			
		
		
		
		double x1 = line.getX1();
		double x2 = originalX + Math.sin(swingTimer) * this.length*0.7 * xSpeedMult;
		double y1 = line.getY1();
		double length = this.length;
		double y2 = Math.sqrt(Math.pow(length, 2) - Math.pow(x2 - x1, 2) ) + y1;
		line.setLine(x1, y1, x2, y2);
		if(swinging) {
			if(moveLeft && Math.sin(swingTimer) - Math.sin(swingTimer+0.1) > 0 && xSpeedMult <= 1) {
				xSpeedMult += xSpeedMult * 0.003;
			}
			if(moveRight && Math.sin(swingTimer) - Math.sin(swingTimer+0.1) < 0 && xSpeedMult <= 1) {
				xSpeedMult += xSpeedMult * 0.003;
			}
			if(moveLeft && Math.sin(swingTimer) - Math.sin(swingTimer+0.1) < 0) {
				xSpeedMult -= xSpeedMult * 0.01;
			}
			if(moveRight && Math.sin(swingTimer) - Math.sin(swingTimer+0.1) > 0) {
				xSpeedMult -= xSpeedMult * 0.01;
			}
		}
			
		
		if(GameState.player.hanging && GameState.player.rope == this) {
			xSpeedMult -= xSpeedMult * 0.001;
			GameState.player.changePos(x2, y2);
			blocked = 30;
		}
		else
			xSpeedMult -= xSpeedMult * 0.01;
		
		if(xSpeedMult < 0.02)
			xSpeedMult = 0;
		image = RotatableImage.getImage(SIZE);
		theta = Math.toRadians(Math.atan2(y1 - y2, x1 - x2));
		
		System.out.println(theta);
			
	}
	
	public void letGo() {
		GameState.player.xSpeed = 10 *  (Math.cos(swingTimer));
		GameState.player.xAcc = 0;
		GameState.player.ySpeed = -20 *  Math.sin(swingTimer);

		GameState.player.moveRight = moveRight;
		GameState.player.moveLeft = moveLeft;
		
	}
	
	public void paint(Graphics2D g) {
		super.paint(g);
		int xUsable = (int) ((line.getX1() - Game.camera.getCameraPos().getX()-4) * Camera.scale);
		int yUsable = (int)((line.getY1() - Game.camera.getCameraPos().getY()) * Camera.scale);
		int xEndUsable = (int) ((line.getX2() - Game.camera.getCameraPos().getX()-4) * Camera.scale);
		int yEndUsable = (int)((line.getY2() - Game.camera.getCameraPos().getY()) * Camera.scale);
		Line2D lin = new Line2D.Float(xUsable, yUsable, xEndUsable, yEndUsable);
		BufferedImage image;
		g.setStroke(new BasicStroke((float) (20*Camera.scale/Camera.zoom)));
		Color brown = new Color(59, 31, 6);
		g.setColor(brown);
        g.draw(new Line2D.Float(xUsable, yUsable, xEndUsable, yEndUsable));
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1));
		try {
			/**
			image = ImageIO.read(new File("Assets/Rope.png"));
			BufferedImage rightPart = image.getSubimage(0, 2048-(int)length, 8, (int)length*2);
			
			Image scaledImage = rightPart.getScaledInstance((int)(8 * Camera.scale), (int)(length* 6 * Camera.scale), image.SCALE_DEFAULT);
			
			
			
			AffineTransform oldXForm = g.getTransform();
			g.translate(scaledImage.getWidth(this) / 2 + xUsable, scaledImage.getWidth(this) / 2 + yUsable);
	        g.rotate(Math.PI- xSpeedMult *Math.sin(swingTimer)* 0.08*Math.PI);
	        g.translate(-(scaledImage.getWidth(this) / 2+ xUsable), -(scaledImage.getHeight(this) / 2+ yUsable));
	        g.drawImage(scaledImage, xUsable, (int) (yUsable-length*3), null);
	        g.setTransform(oldXForm);
	        */
	        
	        image = ImageIO.read(new File("Assets/Knot.png"));
	        Image scaledImage = image.getScaledInstance((int)(64/ Camera.zoom), (int)(64/ Camera.zoom), image.SCALE_DEFAULT);
	        g.drawImage(scaledImage, (int)(xUsable-32*Camera.scale), (int)(yUsable-32*Camera.scale), null);
	        g.drawImage(scaledImage, (int)(xEndUsable-32*Camera.scale), (int)(yEndUsable-10*Camera.scale), null);
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

	
	
	
	/**
	
	
	class RotatePanel extends JPanel implements ActionListener {

	    private static final int SIZE = 256;
	    private static double DELTA_THETA = Math.PI / 90;
	    private final Timer timer = new Timer(25, this);
	    private Image image = RotatableImage.getImage(SIZE);
	    private double dt = DELTA_THETA;
	    private double theta;

	    public RotatePanel() {
	        this.setBackground(Color.lightGray);
	        this.setPreferredSize(new Dimension(
	            image.getWidth(null), image.getHeight(null)));
	        this.addMouseListener(new MouseAdapter() {

	            @Override
	            public void mousePressed(MouseEvent e) {
	                image = RotatableImage.getImage(SIZE);
	                dt = -dt;
	            }
	        });
	        timer.start();
	    }

	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.translate(this.getWidth() / 2, this.getHeight() / 2);
	        g2d.rotate(theta);
	        g2d.translate(-image.getWidth(this) / 2, -image.getHeight(this) / 2);
	        g2d.drawImage(image, 0, 0, null);
	    }

	    
	}
	*/
	

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SIZE, SIZE);
    }




public String toString() {
	return "Rope " + line.getX1() + " " + line.getY1() + " " + length;
}

public double getXAxis() {
	return line.getX1();
}
public double getYAxis() {
	return line.getY1();
}


static class RotatableImage {

    private static final Random r = new Random();

    static public Image getImage(int size) {
        BufferedImage bi = new BufferedImage(
            size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(Color.getHSBColor(r.nextFloat(), 1, 1));
        g2d.setStroke(new BasicStroke(size / 8));
        g2d.drawLine(0, size / 2, size, size / 2);
        g2d.drawLine(size / 2, 0, size / 2, size);
        g2d.dispose();
        return bi;
    }
	


	
	
	
	

}
}
