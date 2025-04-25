package turtle;

import drawing.Canvas;
import geometry.CartesianCoordinate;

public class Turtle {
	private Canvas canvas;
	protected CartesianCoordinate currentPosition;
	private double currentAngle;
	private boolean penStatus;
	// speed in pixels per second
	protected int speed = 100;
	protected final static double PIXELS_PER_MS = 0.1;

	public Turtle(Canvas canvas, CartesianCoordinate startingPoint) {
		this.canvas = canvas;
		this.currentPosition = new CartesianCoordinate(startingPoint.getX(), startingPoint.getY());
		currentAngle = 0;
		draw();
	}

	/**
	 * The turtle is moved in its current direction for the given number of pixels.
	 * If the pen is down when the robot moves, a line will be drawn on the floor.
	 * An angle of 0 corresponds to facing 'right' on the canvas
	 * 
	 * @param distance The number of pixels to move.
	 */
	public void move(int distance) {
		double xDistance = distance * Math.cos(Math.toRadians(currentAngle));
		double yDistance = distance * Math.sin(Math.toRadians(currentAngle));
		CartesianCoordinate newPosition = new CartesianCoordinate(currentPosition.getX() + xDistance,
				currentPosition.getY() + yDistance);
		// draw line if pen is down
		if (penStatus == true) {
			canvas.drawLineBetweenPoints(currentPosition, newPosition);
		}
		// update position
		currentPosition = newPosition;
	}

	/**
	 * Draws an equilateral triangle with the 'top' corner at the turtle's location
	 */
	public void draw() {
		// save state of pen to restore after drawing the triangle
		boolean currentPenState = penStatus;
		putPenDown();
		turn(150);
		move(10);
		turn(120);
		move(10);
		turn(120);
		move(10);
		turn(-30);
		penStatus = currentPenState;
	}

	/**
	 * Removes the three lines drawn by the draw() method
	 */
	public void unDraw() {
		canvas.removeMostRecentLine();
		canvas.removeMostRecentLine();
		canvas.removeMostRecentLine();
	}

	/**
	 * Rotates the turtle clockwise by the specified angle in degrees.
	 * 
	 * @param angleToTurn The number of degrees to turn.
	 */
	public void turn(int angleToTurn) {
		currentAngle += angleToTurn;
		// limits angle to 360 degrees
		currentAngle -= currentAngle > 360 ? 360 : 0;
	}

	/**
	 * Moves the pen off the canvas so that the turtle's route isn't drawn for any
	 * subsequent movements.
	 */
	public void putPenUp() {
		penStatus = false;
	}

	/**
	 * Lowers the pen onto the canvas so that the turtle's route is drawn.
	 */
	public void putPenDown() {
		penStatus = true;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPositionX() {
		return (int) currentPosition.getX();
	}

	public int getPositionY() {
		return (int) currentPosition.getY();
	}

}
