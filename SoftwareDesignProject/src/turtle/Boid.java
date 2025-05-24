package turtle;

import java.awt.Color;
import java.util.List;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import main.SimulationObject;
import obstacle.Obstacle;

public class Boid extends Turtle implements SimulationObject {

	// speed in pixels per second
	private double speed = 25;
	private int canvasWidth;
	private int canvasHeight;
	private double collisionRadius;
	private static final double MAX_SPEED = 40;

	/**
	 * 
	 * @param canvas
	 * @param startingPoint
	 * @param canvasHeight
	 * @param canvasWidth
	 */

	public Boid(Canvas canvas, CartesianCoordinate startingPoint, int canvasWidth, int canvasHeight, Color color) {
		super(canvas, startingPoint, color);
	}

	/**
	 * 
	 * @param canvas
	 * @param x
	 * @param y
	 */
	public Boid(Canvas canvas, double x, double y, int canvasWidth, int canvasHeight, Color color) {
		super(canvas, new CartesianCoordinate(x, y), color);
	}

	/**
	 * 
	 * @param boid
	 */
	public void flocking(List<Boid> flock, double cohesion, double separation, double alignment, double range) {
		double angleToTurn = 0;
		// separation
		angleToTurn += ((currentPosition.angle(nearestBoid(flock).getCurrentPosition()) + 180 - currentAngle)
				* separation);
		// cohesion
		angleToTurn += ((currentPosition.angle(localCenterOfGravity(flock, range)) - currentAngle) * cohesion);
		// aligment
		angleToTurn += (localAverageAngle(flock, range) - currentAngle) * alignment;
		turn((int) angleToTurn);
	}

	/**
	 * 
	 * @param obstacles
	 */
	public void avoidObstacles(List<Obstacle> obstacles) {
		double angleToTurn = 0;
		for (Obstacle obstacle : obstacles) {
			double distanceToObstacle = currentPosition.toroidDistance(obstacle.getCenter(), canvasHeight,
					canvasHeight) - obstacle.getCollisionRadius();
			angleToTurn += ((currentPosition.angle(obstacle.getCenter()) + 180) - currentAngle) / distanceToObstacle;
		}
		turn((int) angleToTurn);
	}

	/**
	 * 
	 * @param flock
	 * @return
	 */
	private Boid nearestBoid(List<Boid> flock) {
		Boid nearestBoid = flock.get(0);
		double min =  currentPosition.toroidDistance(nearestBoid.currentPosition, canvasWidth, canvasHeight);
		for (Boid boid : flock) {
			double distance = currentPosition.toroidDistance(boid.currentPosition, canvasWidth, canvasHeight);
			if (distance <= min) {
				System.out.println("in here");
				min = distance;
				nearestBoid = boid;
			}
		}
		return nearestBoid;
	}

	/**
	 * Calculates the center of gravity of a local flock around boid, including
	 * itself
	 * 
	 * @param boid
	 * @return center of gravity of all boids within RANGE pixels of boid
	 */
	private CartesianCoordinate localCenterOfGravity(List<Boid> flock, double range) {
		int xTotal = 0;
		int yTotal = 0;
		int localPopulation = 0;
		for (Boid boid : flock) {
			if (currentPosition.distance(boid.getCurrentPosition()) <= range) {
				localPopulation++;
				xTotal += boid.getPositionX();
				yTotal += boid.getPositionY();
			}
		}
		return new CartesianCoordinate(xTotal / localPopulation, yTotal / localPopulation);
	}

	/**
	 * 
	 * @param flock
	 * @param range
	 * @return
	 */
	private double localAverageAngle(List<Boid> flock, double range) {
		double totalAngle = 0;
		double localPopulation = 0;
		for (Boid boidB : flock) {
			if (currentPosition.distance(boidB.getCurrentPosition()) < range) {
				localPopulation++;
				totalAngle += boidB.getCurrentAngle();
			}
		}
		return totalAngle / localPopulation;
	}

	/**
	 * Finds the shortest distance between two boids on a toroidal canvas, prevents
	 * boids getting stuck at the edges
	 * 
	 * @param boid
	 * @param xWindowSize
	 * @param yWindowSize
	 * @return distance between this and another boid, on a torus with dimensions
	 *         xWindowSize and yWindowSize
	 */
	public double boidDistance(Boid boid, int xWindowSize, int yWindowSize) {
		return currentPosition.toroidDistance(boid.currentPosition, xWindowSize, yWindowSize);
	}

	/**
	 * Moves this boid forward based on time elapsed and speed parameters
	 * 
	 * @param deltaTime
	 */
	public void update(int deltaTime) {
		move((int) (speed * PIXELS_PER_MS * deltaTime));
	}

	/**
	 * Rotates the turtle clockwise by the specified angle in degrees. Slows the
	 * turtle down depending on the magnitude of the turn
	 * 
	 * @param angleToTurn The number of degrees to turn.
	 */
	public void turn(int angleToTurn) {
		// limits angle to +/-180 degrees
		while (angleToTurn > 180) {
			angleToTurn -= 360;
		}
		while (angleToTurn < -180) {
			angleToTurn += 360;
		}
		speed = MAX_SPEED * (1 - (Math.abs((double) angleToTurn) / 45));
		currentAngle += angleToTurn;
	}

	@Override
	/**
	 * 
	 * Draws an equilateral triangle with the 'top' corner at the turtle's location
	 * 
	 */
	public void draw() {
		CartesianCoordinate corner1 = new CartesianCoordinate(
				currentPosition.getX() - 10 * Math.sin(Math.toRadians(70 - currentAngle)),
				currentPosition.getY() + 10 * Math.cos(Math.toRadians(70 - currentAngle)));
		CartesianCoordinate corner2 = new CartesianCoordinate(
				currentPosition.getX() - (10 * Math.sin(Math.toRadians(70 + currentAngle))),
				currentPosition.getY() - (10 * Math.cos(Math.toRadians(70 + currentAngle))));

		canvas.drawLineBetweenPoints(currentPosition, corner1, color);
		canvas.drawLineBetweenPoints(corner1, corner2, color);
		canvas.drawLineBetweenPoints(corner2, currentPosition, color);
	}

	@Override
	public boolean collisionCheck(SimulationObject object) {
		return (currentPosition.toroidDistance(object.getPosition(), canvasWidth, canvasHeight)) < collisionRadius;
	}

	@Override
	public CartesianCoordinate getPosition() {
		return currentPosition;
	}

	@Override
	public double getCollisionRadius() {
		return collisionRadius;
	}

}
