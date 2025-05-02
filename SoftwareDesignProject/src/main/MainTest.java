package main;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import tools.Utils;
import turtle.Boid;

public class MainTest {
	private static final int WINDOW_SIZE_X = 1000;
	private static final int WINDOW_SIZE_Y = 1000;
	private static final double COHESION = 0.8;
	private static final double SEPARATION = 0.7;
	private static final double ALIGNMENT = 0.1;
	private static final int POPULATION = 50;
	private static final int BOID_SIZE = 10;
	private static final double RANGE = 200;
	public static JFrame window = new JFrame();
	public static Canvas canvas = new Canvas();
	private ArrayList<Boid> boids = new ArrayList<Boid>();

	public MainTest() {
		windowSetup();
		boidSetup();
	}

	private void windowSetup() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		window.setVisible(true);
		window.add(canvas);
	}

	private void boidSetup() {
		for (int i = 0; i < POPULATION; i++) {
			boids.add(new Boid(canvas, Utils.randomInt(0, WINDOW_SIZE_X), Utils.randomInt(0, WINDOW_SIZE_Y),
					Color.BLACK));
		}
		for(Boid boid : boids) {
			boid.turn(Utils.randomInt(-180, 180));
		}
	}

	/**
	 * Calculates the center of gravity of a local flock around boid, including
	 * itself
	 * 
	 * @param boid
	 * @return center of gravity of all boids within RANGE pixels of boid
	 */
	private CartesianCoordinate localCenterOfGravity(Boid boid) {
		int xTotal = 0;
		int yTotal = 0;
		int localPopulation = 0;
		for (Boid boidB : boids) {
			if (boid.getCurrentPosition().distance(boidB.getCurrentPosition()) < RANGE) {
				localPopulation++;
				xTotal += boidB.getPositionX();
				yTotal += boidB.getPositionY();
			}
		}
		if (localPopulation <= 1) {
			return null;
		}
		return new CartesianCoordinate(xTotal / localPopulation, yTotal / localPopulation);
	}

	private double localAverageAngle(Boid boid) {
		double totalAngle = 0;
		double localPopulation = 0;
		for (Boid boidB : boids) {
			if (boid.getCurrentPosition().distance(boidB.getCurrentPosition()) < RANGE) {
				localPopulation++;
				totalAngle += boidB.getCurrentAngle();
			}
		}
		return totalAngle/localPopulation;
	}

	/**
	 * 
	 * @param boid
	 */
	private void flocking(Boid boid) {
		double angleToTurn = 0;
		try {
			angleToTurn += ((boid.getCurrentPosition().angle(localCenterOfGravity(boid)) + 180 - boid.getCurrentAngle())
					* SEPARATION);
			angleToTurn += ((boid.getCurrentPosition().angle(localCenterOfGravity(boid)) - boid.getCurrentAngle())
					* COHESION);
		} catch (NullPointerException npe) {
			angleToTurn += 0;
		} finally {
			
			angleToTurn += (localAverageAngle(boid) - boid.getCurrentAngle()) * ALIGNMENT;
			boid.turn((int) angleToTurn);
		}
	}

	/**
	 * runs the simulation
	 */
	public void gameLoop() {
		int deltaTime = 50;
		while (true) {
			for (Boid boid : boids) {
				boid.unDraw();
			}
			for (Boid boid : boids) {
				// TODO figure out the correct order for these
				boid.wrapPosition(WINDOW_SIZE_X, WINDOW_SIZE_Y);
				flocking(boid);
				boid.update(deltaTime);
			}
			for (Boid boid : boids) {
				boid.draw(BOID_SIZE);
			}
			Utils.pause(deltaTime);
		}
	}

	public static void main(String[] args) {
		MainTest test = new MainTest();
		test.gameLoop();
	}
}
