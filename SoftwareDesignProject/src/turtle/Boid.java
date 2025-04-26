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
	 * Moves this boid forward based on time elapsed and speed parameters
	 * 
	 * @param deltaTime
	 */
	public void update(int deltaTime) {
		putPenDown();
		move((int) (speed * PIXELS_PER_MS * deltaTime));
	}




}
