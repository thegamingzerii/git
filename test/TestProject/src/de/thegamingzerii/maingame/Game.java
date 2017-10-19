package de.thegamingzerii.maingame;


import java.nio.IntBuffer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Objects.Player;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;

import de.thegamingzerii.utility.Constantes;
import de.thegamingzerii.utility.KeyboardListener;
import de.thegamingzerii.states.*;



public class Game extends JPanel{

	
	// The window handle
	
	
	private long window;
	private int width = Constantes.width;
	private int height = Constantes.height;
	private static State ingameState;
	private static State mainMenuState;
	private static State pauseState;
	private static State currentState;
	
	private static Game currentGame;
	


	public static JFrame frame;
	
	
	
	public static void setCurrentState(State state) {
		currentState = state;
	}
	
	public static Game getGame() {
		return currentGame;
	
	}
	


	
	
	
	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				GameState.player.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				GameState.player.keyPressed(e);
			}
		});
		setFocusable(true);
		currentGame = run();
	}
	
	
	


	public Game run() {
		System.out.println("App version " + Constantes.version);
		init();

		return this;
	}
	
	
	

	private void init() {
		
		ingameState = new GameState();
		mainMenuState = new MenuState();
		pauseState = new PauseState();
		currentState = ingameState;
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ingameState.init();
		pauseState.init();
		mainMenuState.init();
		
		
		
		
		
		
	}
		
		
		
	public void update(double delta) {
		currentState.update(delta);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		GameState.player.paint(g2d);
	}
	
	
	
	


	

public static void main(String[] args) throws InterruptedException {
	
	frame = new JFrame("Mini Tennis");
	Game game = new Game();
	frame.add(game);
	//frame.add(keyboard);
	
	long lastLoopTime = System.nanoTime();
	final int TARGET_FPS = 300;
    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   
    final long CONSTANT_LOGIC_TIME = 1000000000 / 60; 
    long lastFpsTime = 0;
    int fps = 0;
	   
    // keep looping round till the game ends
    while (true)
    {
		   
		   
		   // work out how long its been since the last update, this
	  // will be used to calculate how far the entities should
	      // move this loop
	      long now = System.nanoTime();
	      long updateLength = now - lastLoopTime;
	      lastLoopTime = now;
	      double delta = updateLength / ((double)CONSTANT_LOGIC_TIME);

	      // update the frame counter
	      lastFpsTime += updateLength;
	      fps++;
	      
	      // update our FPS counter if a second has passed since
	      // we last recorded
	      if (lastFpsTime >= 1000000000)
	      {
	         System.out.println("(FPS: "+fps+")");
	         lastFpsTime = 0;
	         fps = 0;
	      }
	      
	      // update the game logic
	      game.update(delta);
	      
	      
	      // draw everyting
	      game.repaint();
	      
	      // we want each frame to take 10 milliseconds, to do this
	      // we've recorded when we started the frame. We add 10 milliseconds
	      // to this and then factor in the current time to give 
	      // us our final value to wait for
	      // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
	      //try{Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 )};
	      try {
	    	  if((lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 > 0) {
	    		  Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
	    	  }
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	     
	      
	   }
    }
		
public class MyKeyListener implements KeyListener {
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
	}
}
	

}
