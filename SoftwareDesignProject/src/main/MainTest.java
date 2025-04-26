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
	private static final double COHESION = 0;
	private static final double SEPARATION = 0.7;
	private static final int POPULATION = 1;
	private static final int BOID_SIZE = 10;
	private static final double RANGE = 1000;
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
	}

	public CartesianCoordinate localCenterOfGravity(Boid boid) {
		int xTotal = 0;
		int yTotal = 0;
		for (Boid boidB : boids) {
			if (boidB == boid) {
				continue;
			} else if (boid.getCurrentPosition().distance(boidB.getCurrentPosition()) < RANGE) {
				xTotal += boidB.getPositionX();
				yTotal += boidB.getPositionY();
			}
		}
		return new CartesianCoordinate(xTotal / POPULATION, yTotal / POPULATION);

	}

	public void gameLoop() {
		int deltaTime = 100;
		while (true) {
			for (Boid boid : boids) {
				boid.unDraw();
			}
			for (Boid boid : boids) {
				boid.wrapPosition(WINDOW_SIZE_X, WINDOW_SIZE_Y);
				boid.turn((int) (((boid.getCurrentPosition().angle(localCenterOfGravity(boid)) + 180) - boid.getCurrentAngle())
					* SEPARATION));
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
