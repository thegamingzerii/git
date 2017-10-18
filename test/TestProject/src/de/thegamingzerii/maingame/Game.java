package de.thegamingzerii.maingame;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import de.thegamingzerii.utility.Constantes;



public class Game {

	// The window handle
	private long window;
	private int width = Constantes.width;
	private int height = Constantes.height;
	
	private static Game currentGame;
	
	
	
	public static Game getGame() {
		return currentGame;
	
	}
	
	public static void createGame() {
		currentGame = new Game().run();
	
	}
	


	public Game run() {
		System.out.println("App version " + Constantes.version);
		init();
		gameLoop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		return this;
	}
	
	
	

	private void init() {
		
		
		
		
		
		
		
		
		
		
		
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(width, height, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(0);

		// Make the window visible
		glfwShowWindow(window);
	}
		
		
		
	public void update(double delta) {
		
	}
	
	
	public void render() {
		
	}
	
	
	
	
	public void gameLoop(){
			// This line is critical for LWJGL's interoperation with GLFW's
			// OpenGL context, or any context that is managed externally.
			// LWJGL detects the context that is current in the current thread,
			// creates the GLCapabilities instance and makes the OpenGL
			// bindings available for use.
			GL.createCapabilities();

			// Set the clear color
			glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

			// Run the rendering loop until the user has attempted to close
			// the window or has pressed the ESCAPE key.
		   long lastLoopTime = System.nanoTime();
		   final int TARGET_FPS = 60;
		   final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   
		   long lastFpsTime = 0;
		   float fps = 0;
		   
		   // keep looping round til the game ends
		   while (!glfwWindowShouldClose(window))
		   {
			   glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

				glfwSwapBuffers(window); // swap the color buffers

				// Poll for window events. The key callback above will only be
				// invoked during this call.
				glfwPollEvents();
			   
			   
			   
		      // work out how long its been since the last update, this
		      // will be used to calculate how far the entities should
		      // move this loop
		      long now = System.nanoTime();
		      long updateLength = now - lastLoopTime;
		      lastLoopTime = now;
		      double delta = updateLength / ((double)OPTIMAL_TIME);

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
		      update(delta);
		      
		      // draw everyting
		      render();
		      
		      // we want each frame to take 10 milliseconds, to do this
		      // we've recorded when we started the frame. We add 10 milliseconds
		      // to this and then factor in the current time to give 
		      // us our final value to wait for
		      // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
		      //try{Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 )};
		      try {
				Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		      
		      
		   }
		}

	

	public static void main(String[] args) {
		createGame();
	
	}
		
		
	

}
