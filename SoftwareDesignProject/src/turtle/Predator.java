package turtle;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import simulationObject.SimulationObject;

public class Predator extends Boid implements SimulationObject{
	
	public Predator(Canvas canvas, CartesianCoordinate startingPoint, Dimension canvasSize, Color color) {
		super(canvas, startingPoint, canvasSize, color);
	}
	
	/**
	 * turns toward the nearest boid
	 * 
	 * @param flock
	 * @param hunting
	 */
	public void chaseBoids(List<Boid> flock, double hunting) {
		double angleToTurn = currentPosition.angle(nearestBoid(flock).currentPosition);
		turn((int)(angleToTurn * hunting));
	}
	
}
