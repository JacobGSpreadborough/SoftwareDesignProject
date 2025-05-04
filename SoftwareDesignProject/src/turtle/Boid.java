package turtle;

import java.awt.Color;

import drawing.Canvas;
import geometry.CartesianCoordinate;

public class Boid extends Turtle {

	/**
	 * 
	 * @param canvas
	 * @param startingPoint
	 */
	
	public Boid(Canvas canvas, CartesianCoordinate startingPoint, Color color) {
		super(canvas, startingPoint, color);
	}
	
	/**
	 * 
	 * @param canvas
	 * @param x
	 * @param y
	 */
	public Boid(Canvas canvas, double x, double y, Color color) {
		super(canvas, new CartesianCoordinate(x,y), color);
	}
	/**
	 * Finds the shortest distance between two boids on a toroidal canvas, prevents boids getting stuck at the edges
	 * 
	 * @param boid
	 * @param xWindowSize
	 * @param yWindowSize
	 * @return distance between this and another boid, on a torus with dimensions xWindowSize and yWindowSize
	 */
	public double boidDistance(Boid boid, int xWindowSize, int yWindowSize) {
		double xDistance = Math.abs(this.getPositionX() - boid.getPositionX());
		double yDistance = Math.abs(this.getPositionY() - boid.getPositionY());
		if (xDistance > xWindowSize/2) {
			xDistance = xWindowSize - xDistance;
		}
		if (yDistance > yWindowSize/2) {
			yDistance = yWindowSize - yDistance;
		}
		return Math.hypot(xDistance, yDistance);
	}

	/**
	 * Moves this boid forward based on time elapsed and speed parameters
	 * 
	 * @param deltaTime
	 */
	public void update(int deltaTime) {
		// putPenDown();
		move((int) (speed * PIXELS_PER_MS * deltaTime));
	}




}
