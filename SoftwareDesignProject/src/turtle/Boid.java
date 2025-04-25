package turtle;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import geometry.LineSegment;

public class Boid extends Turtle {

	/**
	 * 
	 * @param canvas
	 * @param startingPoint
	 */
	
	public Boid(Canvas canvas, CartesianCoordinate startingPoint) {
		super(canvas, startingPoint);
	}
	
	/**
	 * 
	 * @param canvas
	 * @param x
	 * @param y
	 */
	public Boid(Canvas canvas, double x, double y) {
		super(canvas, new CartesianCoordinate(x,y));
	}

	/**
	 * Moves this boid forward based on time elapsed and speed parameters
	 * 
	 * @param deltaTime
	 */
	public void update(int deltaTime) {
		putPenDown();
		move((int) (speed * PIXELS_PER_MS * deltaTime));
	}


	/**
	 * Returns distance between two boids
	 * 
	 * @param boid
	 * @return distance between this boid and some other boid
	 */
	public double distance(Boid boid) {
		return new LineSegment(this.currentPosition, boid.currentPosition).getLength();
	}

	/**
	 * Returns angle between two boids
	 * 
	 * @param boid
	 * @return angle between this boid and some other boid
	 */
	public double angle(Boid boid) {
		return new LineSegment(this.currentPosition, boid.currentPosition).getAngle();
	}

}
