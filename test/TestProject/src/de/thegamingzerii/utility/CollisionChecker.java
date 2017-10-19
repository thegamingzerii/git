package de.thegamingzerii.utility;

import Objects.ICollision;

public class CollisionChecker {

	public static boolean CheckCollision(ICollision object1, ICollision object2) {
		
		if(object1.getXAxis() < object2.getXAxis() && object1.getXAxis() > object2.getXAxis() + object2.getCollisionSize()[0]) {
			if(object1.getYAxis() < object2.getYAxis() && object1.getYAxis() > object2.getYAxis() + object2.getCollisionSize()[1]) {
				
			}else {
				if(object1.getYAxis() + object1.getCollisionSize()[1] < object2.getYAxis() && object1.getYAxis() > object2.getYAxis() + object2.getCollisionSize()[1]) {
					
				}else {
					if(object1.getXAxis() + object1.getCollisionSize()[0] < object2.getXAxis() && object1.getXAxis() > object2.getXAxis() + object2.getCollisionSize()[0]) {
						if(object1.getYAxis() < object2.getYAxis() && object1.getYAxis() > object2.getYAxis() + object2.getCollisionSize()[1]) {
							
						}else {
							if(object1.getYAxis() + object1.getCollisionSize()[1] < object2.getYAxis() && object1.getYAxis() > object2.getYAxis() + object2.getCollisionSize()[1]) {
								
							}else {
								return false;
							}
						}
						
					}
				}
			}
			
		}
		
		
		
		return true;
	}
}
