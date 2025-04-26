package turtle;

import drawing.Canvas;
import geometry.CartesianCoordinate;

public class Turtle {
	private Canvas canvas;
	protected CartesianCoordinate currentPosition;
	private double currentAngle;
	private boolean penStatus;
	// speed in pixels per second
	protected int speed = 10;
	protected final static double PIXELS_PER_MS = 0.01;
	private static final double COSINE30 = 0.5;
	private static final double SINE30 = 0.5;

	public Turtle(Canvas canvas, CartesianCoordinate startingPoint) {
		this.canvas = canvas;
		this.currentPosition = new CartesianCoordinate(startingPoint.getX(), startingPoint.getY());
		currentAngle = 0;
		draw(10);
	}

	/**
	 * The turtle is moved in its current direction for the given number of pixels.
	 * If the pen is down when the robot moves, a line will be drawn on the floor.
	 * An angle of 0 corresponds to facing 'right' on the canvas, 90 degrees
	 * corresponds to facing 'up' etc.
	 * 
	 * @param distance The number of pixels to move.
	 */
	public void move(int distance) {
		double xDistance = distance * Math.cos(Math.toRadians(currentAngle));
		double yDistance = distance * -Math.sin(Math.toRadians(currentAngle));
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
	 * prevents turtles from moving outside of the window
	 */
	public void wrapPosition(int xLimit, int yLimit) {

		if (currentPosition.getX() < 0 || currentPosition.getX() > xLimit) {
			currentPosition = currentPosition.getX() > 0 ? new CartesianCoordinate(0, currentPosition.getY())
					: new CartesianCoordinate(xLimit, currentPosition.getY());
		}
		if (currentPosition.getY() < 0 || currentPosition.getY() > yLimit) {
			currentPosition = currentPosition.getY() > 0 ? new CartesianCoordinate(currentPosition.getX(), 0)
					: new CartesianCoordinate(currentPosition.getX(), yLimit);
		}
	}

	/**
	 * Draws an equilateral triangle with the 'top' corner at the turtle's location
	 * TODO make this good
	 */
	public void draw(int sideLength) {
		CartesianCoordinate corner1 = new CartesianCoordinate(currentPosition.getX() + sideLength*COSINE30, currentPosition.getY() + sideLength*SINE30);
		CartesianCoordinate corner2 = new CartesianCoordinate(currentPosition.getX() - sideLength*COSINE30, currentPosition.getY() + sideLength*SINE30);
		canvas.drawLineBetweenPoints(currentPosition, corner1);
		canvas.drawLineBetweenPoints(corner1, corner2);
		canvas.drawLineBetweenPoints(corner2, currentPosition);
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
		if (currentAngle > 180) {
			System.out.println("current angle is greater than 180");
			currentAngle = -currentAngle;
		} else if (currentAngle < -180) {
			System.out.println("current angle is less than -180");
			currentAngle = -currentAngle;
		}
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

	public double getCurrentAngle() {
		return currentAngle;
	}
}
