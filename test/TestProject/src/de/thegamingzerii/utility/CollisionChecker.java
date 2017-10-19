package de.thegamingzerii.utility;

import java.awt.Rectangle;

import Objects.Block;
import Objects.ICollision;

public class CollisionChecker {

	public static boolean CheckCollision(ICollision object1, ICollision object2) {
		
		Rectangle r1 = object1.getCollisionSize();
		Rectangle r2 = object2.getCollisionSize();
		if(r1.intersects(r2))
		{
		    return true;
		}
		
		
		
		return false;
	}
	
	
	public static boolean CheckAllCollisions(ICollision object1) {
		boolean bool = false;
		for(int i = 0; i < Block.allBlocks.size(); i++) {
			if(CheckCollision(object1, Block.allBlocks.get(i))) {
				bool =  true;
			}
		}
		return bool;
		
	}
}
