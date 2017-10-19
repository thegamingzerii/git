package Objects;

public interface ICollision {
	
	
	boolean checkcollision(ICollision C);
	
	double getXAxis();
	
	double getYAxis();
	
	void setX(int x);
	
	void setY(int y);
	
	int[] getCollisionSize();
	
	

}
