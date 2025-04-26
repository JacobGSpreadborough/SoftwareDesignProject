package main;

import javax.swing.JFrame;

import drawing.Canvas;
import tools.Utils;
import turtle.Boid;

public class TestCohesion {
	private static final int WINDOW_SIZE_X = 800;
	private static final int WINDOW_SIZE_Y = 800;
	private static final double COHESION = 0.8;
	public static JFrame window = new JFrame();
	public static Canvas canvas = new Canvas();
	private Boid seeker;
	private Boid target;

	public TestCohesion() {
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
		target = new Boid(canvas, 200, 400);
		seeker = new Boid(canvas, 400, 200);

	}

	public void gameLoop() {
		double angleToTurn = 0;
		int deltaTime = 100;
		while (true) {
			seeker.unDraw();
			seeker.update(deltaTime);
			seeker.wrapPosition(WINDOW_SIZE_Y, WINDOW_SIZE_X);
			// print current angle of seeker
			System.out.print("angle to target: " + seeker.angle(target) + " current angle: " + seeker.getCurrentAngle());
			// calculate angle to turn towards target
			angleToTurn = (seeker.angle(target) - seeker.getCurrentAngle()) * COHESION;
			// print angle and turn
			System.out.println(" angle to turn: " + angleToTurn);
			seeker.turn((int)angleToTurn);
			seeker.draw(10);
			Utils.pause(deltaTime);
		}
	}

	public static void main(String[] args) {
		TestCohesion test = new TestCohesion();
		test.gameLoop();
	}
}
