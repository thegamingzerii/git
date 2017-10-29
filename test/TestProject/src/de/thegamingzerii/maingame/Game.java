package de.thegamingzerii.maingame;


import java.nio.IntBuffer;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import de.thegamingzerii.utility.Constantes;
import de.thegamingzerii.editor.Map;
import de.thegamingzerii.objects.Camera;
import de.thegamingzerii.objects.Player;
import de.thegamingzerii.states.*;



public class Game extends JPanel{

	
	// The window handle
	
	private static int lastFps = 0;
	private long window;
	private int width = Constantes.width;
	private int height = Constantes.height;
	public static State ingameState;
	public static State mainMenuState;
	public static State pauseState;
	public static State editingState;
	public static State currentState;
	
	private static Game currentGame;
	


	public static JFrame frame;
	public static Camera camera;
	
	
	
	public static void setCurrentState(State state) {
		currentState = state;
	}
	
	public static Game getGame() {
		return currentGame;
	
	}
	


	
	
	
	public Game() {

		
		setFocusable(true);
		currentGame = run();
	}
	
	
	


	public Game run() {
		System.out.println("App version " + Constantes.version);
		init();

		return this;
	}
	
	
	

	private void init() {
		int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
		ingameState = new GameState();
		mainMenuState = new MenuState();
		pauseState = new PauseState();
		editingState = new EditingState();
		
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("SPACE"), "jump");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("W"), "move up");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("A"), "move left");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("S"), "move down");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("D"), "move right");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("released SPACE"), "stop jump");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("released W"), "stop move up");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("released A"), "stop move left");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("released S"), "stop move down");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("released D"), "stop move right");
		
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("E"), "editor");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("R"), "r");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("T"), "t");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("F"), "f");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("G"), "g");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("Z"), "z");
		
		ingameState.getActionMap().put("jump", new JumpAction(false));
		ingameState.getActionMap().put("move up", new MoveAction(0, false));
		ingameState.getActionMap().put("move left", new MoveAction(1, false));
		ingameState.getActionMap().put("move down", new MoveAction(2, false));
		ingameState.getActionMap().put("move right", new MoveAction(3, false));
		ingameState.getActionMap().put("stop jump", new JumpAction(true));
		ingameState.getActionMap().put("stop move up", new MoveAction(0, true));
		ingameState.getActionMap().put("stop move left", new MoveAction(1, true));
		ingameState.getActionMap().put("stop move down", new MoveAction(2, true));
		ingameState.getActionMap().put("stop move right", new MoveAction(3, true));
		
		ingameState.getActionMap().put("escape", new EscapeAction());
		ingameState.getActionMap().put("editor", new ChangeToEditorAction());
		ingameState.getActionMap().put("t", new PressedOtherKey(0));
		ingameState.getActionMap().put("r", new PressedOtherKey(1));
		ingameState.getActionMap().put("f", new PressedOtherKey(2));
		ingameState.getActionMap().put("g", new PressedOtherKey(3));
		ingameState.getActionMap().put("z", new PressedOtherKey(4));
		
		
		
		
		currentState = ingameState;
		frame.add(ingameState);
		frame.add(mainMenuState);
		frame.add(pauseState);
		frame.add(editingState);
		frame.setSize(width, height);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    }

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				((EditingState) editingState).mousePressed(e);
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(currentState == editingState) {
					((EditingState) editingState).mouseReleased(e);
				}else {
					if(currentState == pauseState) {
						((PauseState) pauseState).mousePressed(e);
					}else {
						if(currentState == mainMenuState) {
							((MenuState) mainMenuState).mousePressed(e);
						}
					}
					
				}
				
				
				
				
				
			}
		});
		ingameState.init();
		pauseState.init();
		mainMenuState.init();
		
		camera = new Camera(0, 0);
		Map.loadMap();
		

		
		
		
		
		
		
	}
		
		
		
	public void update(double delta) {
		currentState.update(delta);
		Game.camera.update(delta);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		currentState.paint(g2d);
		g.setColor(Color.red);
		String currentFont = g.getFont().toString();
		g.setFont(new Font(currentFont, Font.PLAIN, 15));
		g.drawString("FPS: " + lastFps, (int) (10 * Camera.scale) , (int) (20 * Camera.scale));
	}
	
	
	
	
	
	


	

public static void main(String[] args) throws InterruptedException {
	
	frame = new JFrame("Game");
	Game game = new Game();
	frame.add(game);
	//frame.add(keyboard);
	
	double lastLoopTime = System.nanoTime();
	final int TARGET_FPS = 60;
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
    		lastFps = (int) (1000/((System.nanoTime() - lastLoopTime)/1000000));
	      long now = System.nanoTime();
	      long updateLength = (long) (now - lastLoopTime);
	      lastLoopTime = now;
	      double delta = updateLength / ((double)CONSTANT_LOGIC_TIME);

	      // update the frame counter
	      lastFpsTime += updateLength;
	      fps++;
	      
	      // update our FPS counter if a second has passed since
	      // we last recorded
	      if (lastFpsTime >= 1000000000)
	      {
	    	  
	    	 lastFps = fps;
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
	    		  Thread.sleep( (long) ((lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000) );
	    	  }
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	     
	      
	   }
    }


private class JumpAction extends AbstractAction{
	private boolean released = false;
	public JumpAction(boolean released) {
		this.released = released;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource() instanceof GameState) {
			if(released) {
				GameState.player.jumpPressed = false;
				GameState.player.jumpTimer = 0;
			}
			else
				GameState.player.jump();
				
		}
			
		
	}
	
}

private class MoveAction extends AbstractAction{
	private int direction;
	private boolean released = false;
	
	public MoveAction(int direction, boolean released) {
		this.direction = direction;
		this.released = released;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(currentState == ingameState) {
			switch(direction) {
			case 1:
				GameState.player.recieveMovement(true, released);
				break;
			case 3:
				GameState.player.recieveMovement(false, released);
				break;
			}
		}
		if(currentState == editingState) {
			if(!released) {
				switch(direction) {
				case 0:
					camera.moveCamera(0, -10);
					break;
				case 1:
					camera.moveCamera(-10, 0);
					break;
				case 2:
					camera.moveCamera(0, 10);
					break;
				case 3:
					camera.moveCamera(10, 0);
					break;
				}
			}
			
		}
			
		
	}
}


private class EscapeAction extends AbstractAction{

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(currentState == ingameState) {
			currentState = pauseState;
		}else {
			if(currentState == pauseState)
				currentState = ingameState;
			else {
				if(currentState == mainMenuState)
					System.exit(0);
			}
			
		}
			
		
		
	}
	
}

private class ChangeToEditorAction extends AbstractAction{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(currentState == ingameState) {
			currentState = editingState;
			EditingState.editing = true;
		}	
		else if(currentState == editingState) {
			currentState = ingameState;
			EditingState.editing = false;
		}
		
	}
	
}

private class PressedOtherKey extends AbstractAction{

	int keyId;
	
	public PressedOtherKey(int keyId) {
		this.keyId = keyId;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(currentState == editingState) {
				EditingState.mode = keyId;
				
			}
		}
		
	}
	
}
		

	

