package de.thegamingzerii.utility;

import javax.swing.JComponent;

public class Constantes {
	public static final String version = "0.0.1";
	public static final int width = 1280;
	public static final int height = 720;
	public static final int windowWidth = 1920;
	public static final int windowHeight = 1080;
	
	public static final double GRAVITY = 0.5;
	public static final double TERMINAL_VELO = 50;
	public static final double SLIDING_VELO = 5;
	
	public static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	

	public enum BlockType{
		Ground, Grass, Ground2, Slope1, Slope2, Slope3
	}
	
	public enum BackgroundType{
		Tree1, Bush1
	}
	
	public enum SpawnerType{
		Leaf
	}
	
	public enum ParticleType{
		Dust1
	}
}
