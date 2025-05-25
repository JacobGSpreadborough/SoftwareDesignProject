package turtle;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import obstacle.Obstacle;
import simulationObject.SimulationObject;

public class Boid extends Turtle implements SimulationObject {

	// speed in pixels per second
	private double speed = 30;
	private Dimension canvasSize;
	private double collisionRadius;

	/**
	 * 
	 * @param canvas
	 * @param startingPoint
	 * @param canvasSize
	 * @param color
	 */
	public Boid(Canvas canvas, CartesianCoordinate startingPoint, Dimension canvasSize, Color color) {
		super(canvas, startingPoint, color);
		this.canvasSize = canvasSize;
	}

/**
 * 
 * @param canvas
 * @param x
 * @param y
 * @param canvasSize
 * @param color
 */
	public Boid(Canvas canvas, double x, double y, Dimension canvasSize, Color color) {
		super(canvas, new CartesianCoordinate(x, y), color);
		this.canvasSize = canvasSize;
	}

	/**
	 * 
	 * @param flock
	 * @param cohesion
	 * @param separation
	 * @param alignment
	 * @param range
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
			double distanceToObstacle = currentPosition.toroidDistance(obstacle.getCenter(), canvasSize.height,
					canvasSize.width) - obstacle.getCollisionRadius();
			angleToTurn += ((currentPosition.angle(obstacle.getCenter()) + 180) - currentAngle) / distanceToObstacle;
		}
		turn((int) angleToTurn);
	}

	/**
	 * 
	 * @param flock
	 * @return
	 */
	public Boid nearestBoid(List<Boid> flock) {
		Boid nearestBoid = flock.get(0);
		double min = currentPosition.toroidDistance(nearestBoid.currentPosition, canvasSize.width, canvasSize.height);
		for (Boid boid : flock) {
			double distance = currentPosition.toroidDistance(boid.currentPosition, canvasSize.width, canvasSize.height);
			if (distance <= min) {
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

	@Override
	public boolean collisionCheck(SimulationObject object) {
		return (currentPosition.toroidDistance(object.getPosition(), canvasSize.width,
				canvasSize.height)) < collisionRadius;
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
