package de.thegamingzerii.maingame;


import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import de.thegamingzerii.utility.Constantes;
import de.thegamingzerii.utility.DisplayText;
import de.thegamingzerii.editor.Map;
import de.thegamingzerii.editor.Settings;
import de.thegamingzerii.items.Bomb;
import de.thegamingzerii.logicParts.Gate;
import de.thegamingzerii.logicParts.Lever;
import de.thegamingzerii.objects.BackgroundObject;
import de.thegamingzerii.objects.BreakableWall;
import de.thegamingzerii.objects.Camera;
import de.thegamingzerii.objects.DeadlyBlock;
import de.thegamingzerii.objects.IBufferable;
import de.thegamingzerii.objects.Jumper;
import de.thegamingzerii.objects.Particle;
import de.thegamingzerii.objects.Rope;
import de.thegamingzerii.objects.TextureBlock;
import de.thegamingzerii.spawners.Leaf;
import de.thegamingzerii.states.*;



@SuppressWarnings("serial")
public class Game extends JPanel{

		
	private static int lastFps = 0;
	private static int lastUps = 0;
	public static State ingameState;
	public static State mainMenuState;
	public static State pauseState;
	public static State editingState;
	public static State currentState;
	public static double animationCounter = 0;
	public static Game currentGame;
	
	public static ArrayList<ArrayList<IBufferable>> bufferList = new ArrayList<ArrayList<IBufferable>>();
	public static ArrayList<IBufferable> currentBuffer;
	
	public static Cursor defaultCursor;
	
	
	public static boolean drawHitBoxes = false;
	
	


	public static JFrame frame;
	public static Camera camera;
	public static Camera actualCamera;
	
	
	private double saveGameCounter = 0;
	
	
	public static void setCurrentState(State state) {
		currentState = state;
	}
	
	public static Game getGame() {
		return currentGame;
	
	}
	


	
	
	
	public Game() {

		
		frame.setFocusable(true);
		frame.requestFocus();
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
		ingameState.init();
		mainMenuState.init();
		pauseState.init();
		editingState.init();
	
		
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
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("Q"), "editor");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("E"), "e");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("R"), "r");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("T"), "t");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("F"), "f");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("G"), "g");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("Z"), "z");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("H"), "h");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("L"), "l");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("U"), "u");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("J"), "j");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("K"), "k");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("B"), "b");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("N"), "n");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("M"), "m");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("V"), "v");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("C"), "c");
		ingameState.getInputMap(IFW).put(KeyStroke.getKeyStroke("Y"), "y");
		
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
		ingameState.getActionMap().put("h", new PressedOtherKey(5));
		ingameState.getActionMap().put("u", new PressedOtherKey(6));
		ingameState.getActionMap().put("j", new PressedOtherKey(7));
		ingameState.getActionMap().put("k", new PressedOtherKey(8));
		ingameState.getActionMap().put("b", new PressedOtherKey(9));
		ingameState.getActionMap().put("n", new PressedOtherKey(10));
		ingameState.getActionMap().put("m", new PressedOtherKey(11));
		ingameState.getActionMap().put("v", new PressedOtherKey(12));
		ingameState.getActionMap().put("c", new PressedOtherKey(13));
		ingameState.getActionMap().put("l", new SwitchTexture());
		ingameState.getActionMap().put("e", new Interact());
		ingameState.getActionMap().put("y", new SwitchBackground());
		
		
		
		
		currentState = ingameState;
		frame.add(ingameState);
		frame.add(mainMenuState);
		frame.add(pauseState);
		frame.add(editingState);
		frame.setSize(Constantes.windowWidth, Constantes.windowHeight);
		frame.setUndecorated(true);
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    }

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
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
		
		actualCamera = new Camera(GameState.player.getXAxis() - Constantes.windowWidth/2, GameState.player.getYAxis() - Constantes.windowHeight/2);
		camera = actualCamera;
		ingameState.init();
		pauseState.init();
		mainMenuState.init();
		try {
			TextureBlock.init();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Gate.init();
		BackgroundObject.init();
		Lever.init();
		Leaf.init();
		BreakableWall.init();
		Particle.init();
				
		Map.loadMap();
		SaveGame.loadSaveGame();
		Settings.loadSettings();

		frame.setVisible(true);
		
		defaultCursor = frame.getContentPane().getCursor();
		
		cursorVisibility(false);
		
		
		
	}
	
	
	public static void cursorVisibility(boolean bool) {
		if(!bool) {
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

			// Create a new blank cursor.
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
			    cursorImg, new Point(0, 0), "blank cursor");

			// Set the blank cursor to the JFrame.
			frame.getContentPane().setCursor(blankCursor);
		}else {
			frame.getContentPane().setCursor(defaultCursor);
		}
		
		
	}
		
		
		
	public void update(double delta) {
			currentBuffer = new ArrayList<IBufferable>();
			animationCounter += delta;
			currentState.update(delta);
			actualCamera.update(delta);	
			currentState.buffer();
			saveGameCounter += delta;
			if(saveGameCounter >= 600) {
				SaveGame.saveGame();	
				Settings.saveSettings();
				saveGameCounter = 0;
			}
			
			bufferList.add(currentBuffer);
						
		}
		
		
		
	
	@Override
	public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			camera.paint(g2d);
			
			ArrayList<IBufferable> paintBuffer = bufferList.get(bufferList.size()-1);
			bufferList.clear();
			bufferList.add(paintBuffer);
			
			camera = (Camera) paintBuffer.get(0);
			
			for(int i = 1; i < paintBuffer.size(); i++) {
				paintBuffer.get(i).paint(g2d);
			}
				
			currentState.paint(g2d);
			
			g.setColor(Color.red);
			String currentFont = g.getFont().toString();
			g.setFont(new Font(currentFont, Font.PLAIN, 15));
			g.drawString("FPS: " + lastFps, 10 , 20);
			g.drawString("UPS: " + lastUps, 10 , 40);
			
			
		
		
	}
	
	
	
	
	
	


	

public static void main(String[] args) throws InterruptedException {
	
	frame = new JFrame("Game");
	Game game = new Game();
	game.setSize(Constantes.windowWidth, Constantes.windowHeight);
	game.update(1);
	game.update(1);
	game.update(1);
	frame.add(game);
	//frame.add(keyboard);
		
	
    final long CONSTANT_LOGIC_TIME = 1000000000 / 60; 
    
    	
    Thread scannerThread = new Thread(new Runnable() {
  	  public void run() {
  		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);  // Reading from System.in
  		  while(true) {
  			
  	  	    String command = reader.nextLine(); // Scans the next token of the input as an int.
  	  	    if(command.equals("drawHitBoxes")) {
  	  	    	if(drawHitBoxes)
  	  	    		drawHitBoxes = false;
  	  	    	else
  	  	    		drawHitBoxes = true;
  	  	    	
  	  	    	System.out.println("Toggled hitBoxes");
  	  	    }
  	  	    
  	  	    if(command.equals("reset")) {
  	  	    for(int i = 0; i < Gate.allGates.size(); i++)
  				Gate.allGates.get(i).reset();
  	  	    for(int i = 0; i < Lever.allLevers.size(); i++)
  	  	    	Lever.allLevers.get(i).reset();
  	  	    for(int i = 0; i < BreakableWall.allBreakableWalls.size(); i++)
  	  	    	BreakableWall.allBreakableWalls.get(i).reset();
  	  	    System.out.println("Reset Logic Tiles");
  	  	    }
  	  	    
  	  	    
  	  	    if(command.equals("reWriteMap")) {
  	  	    Map.reWriteMap();
  	  	    System.out.println("Map has been rewriten");
  	  	    }
  	  	    
  	  	    if(command.equals("spawnLeaf")) {
  	  	    	new Leaf(GameState.player.x - 100, GameState.player.y);
  	  	    	System.out.println("Leaf Spawned");
  	  	    }
  	  	    if(command.equals("resetPlayerPos")) {
	  	    	GameState.player.changePos(0, 0);
	  	    	System.out.println("Player position has been reset to (0/0)");
	  	    }
  	  	    
  		  }
  		
  	  }
  	});
    scannerThread.start();

    
    
    
    Thread updateThread = new Thread(new Runnable() {
    	  public void run() {
    		  Timer timer1 = new Timer(1, new ActionListener() {
    		    	long ulastFpsTime = 0;
    		        int ufps = 0;
    		        double ulastLoopTime = System.nanoTime();
    		    	  public void actionPerformed( ActionEvent e ) {
    		    		  
    		              //lastFps = (int) (1000/((System.nanoTime() - lastLoopTime)/1000000));
    		    	      long unow = System.nanoTime();
    		    	      long uupdateLength = (long) (unow - ulastLoopTime);
    		    	      ulastLoopTime = unow;
    		    	      double delta = uupdateLength / ((double)CONSTANT_LOGIC_TIME);

    		    	      // update the frame counter
    		    	      ulastFpsTime += uupdateLength;
    		    	      ufps++;
    		    	      
    		    	      // update our FPS counter if a second has passed since
    		    	      // we last recorded
    		    	      if (ulastFpsTime >= 1000000000)
    		    	      {
    		    	    	  
    		    	    	 lastUps = ufps;
    		    	         ulastFpsTime = 0;
    		    	         ufps = 0;
    		    	      }
    		    	      
    		    	      game.update(delta);    		              
    		              //.setDelay((int) ((lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000));
    		    		  }});
    		    timer1.start();
    		
    	  }
    	});
    updateThread.start();
    
    
    
    Thread paintThread = new Thread(new Runnable() {
  	  public void run() {
  		Timer timer = new Timer(1, new ActionListener() {
  	    	long lastFpsTime = 0;
  	        int fps = 0;
  	        double lastLoopTime = System.nanoTime();
  	    	  public void actionPerformed( ActionEvent e ) {
  	    		  
  	              //lastFps = (int) (1000/((System.nanoTime() - lastLoopTime)/1000000));
  	    	      long now = System.nanoTime();
  	    	      long updateLength = (long) (now - lastLoopTime);
  	    	      lastLoopTime = now;

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
  	    	      
  	              game.repaint();
  	              
  	    		  }});
  	    timer.start();
  		
  	  }
  	});
    paintThread.setPriority(10);
  paintThread.start();
    
    

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
		
		
		if(currentState == ingameState || currentState == editingState) {
			cursorVisibility(true);
			currentState = pauseState;
		}else {
			if(currentState == pauseState) {
				cursorVisibility(false);
				currentState = ingameState;
			}else {
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
			cursorVisibility(true);
			currentState = editingState;
			EditingState.editing = true;
		}	
		else if(currentState == editingState) {
			cursorVisibility(false);
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
		if(keyId == 1)
			if(currentState == ingameState) {
				if(GameState.player.moveDirection == 0)
					new Bomb(GameState.player.getXAxis(), GameState.player.getYAxis(), 3);
				else
					new Bomb(GameState.player.getXAxis(), GameState.player.getYAxis(), -3);

					
				}
		}
		
}


private class SwitchTexture extends AbstractAction{

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(currentState == editingState) {
				EditingState.textureMode++;
				
			}
		}
		
	}


private class SwitchBackground extends AbstractAction{

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(currentState == editingState) {
				if(EditingState.background) {
					EditingState.background = false;
					new DisplayText("Foreground", 800, 500, 50, 60);
				}
				else {
					EditingState.background = true;
					new DisplayText("Background", 800, 500, 50, 60);
				}
				
			}
		
		}
		
	}


private class Interact extends AbstractAction{

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(currentState == ingameState) {
			for(int i = 0; i < Jumper.allJumpers.size(); i++) {
				if(Jumper.allJumpers.get(i).checkProximity(GameState.player.getCollisionSize())) {
					Jumper.allJumpers.get(i).interact(true);
				}
			}
			
			for(int i = 0; i < Lever.allLevers.size(); i++) {
				if(Lever.allLevers.get(i).checkProximity(GameState.player.getCollisionSize()))
					Lever.allLevers.get(i).interact(true);
			}
			
			for(int i = 0; i < Rope.allRopes.size(); i++) {
				if(Rope.allRopes.get(i).checkProximity(GameState.player.getCollisionSize())) {
					Rope.allRopes.get(i).interact(true);
				}
			}
			
			for(int i = 0; i < DeadlyBlock.allDeadlyBlocks.size(); i++) {
				if(DeadlyBlock.allDeadlyBlocks.get(i).checkProximity(GameState.player.getCollisionSize())) {
					DeadlyBlock.allDeadlyBlocks.get(i).interact(true);
				}
			}
				
			}
		}
		
	}
}
	

	



		

	

