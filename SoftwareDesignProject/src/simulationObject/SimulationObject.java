package simulationObject;

import geometry.CartesianCoordinate;

public interface SimulationObject {
	
	public void draw();
	
	public void unDraw(); 
	
	public void update(int deltaTime);
	
	public boolean collisionCheck(SimulationObject object);
	
	public CartesianCoordinate getPosition();

	public double getCollisionRadius();
	
}
