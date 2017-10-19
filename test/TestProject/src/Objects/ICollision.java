package Objects;


public interface ICollision {
	
	
	void checkcollision(ICollision C);
	
	double getX();
	
	double getY();
	
	void setX(int x);
	
	void setY(int y);
	
	int[] getSize();

}
