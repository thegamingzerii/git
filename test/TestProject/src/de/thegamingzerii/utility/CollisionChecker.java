package de.thegamingzerii.utility;

import java.awt.Rectangle;

import de.thegamingzerii.objects.Block;
import de.thegamingzerii.objects.ICollision;
import de.thegamingzerii.objects.Jumper;

public class CollisionChecker {

	public static boolean CheckCollision(ICollision object1, ICollision object2) {
		if(object1.onScreen() && object2.onScreen()) {
			Rectangle r1 = object1.getCollisionSize();
			Rectangle r2 = object2.getCollisionSize();
			if(r1.intersects(r2))
			{
			    return true;
			}
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
	
	
	public static void checkAllInteractions(ICollision object) {
		for(int i = 0; i < Jumper.allJumpers.size(); i++) {
			if(Jumper.allJumpers.get(i).checkProximity(object.getCollisionSize()))
				Jumper.allJumpers.get(i).interact();
		}
	}
}
