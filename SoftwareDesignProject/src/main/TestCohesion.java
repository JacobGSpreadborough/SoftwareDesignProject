package main;

import javax.swing.JFrame;

import drawing.Canvas;
import tools.Utils;
import turtle.Boid;

public class TestCohesion {
	private static final int WINDOW_SIZE_X = 800;
	private static final int WINDOW_SIZE_Y = 800;
	private static final double COHESION = 0.7;
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
		target = new Boid(canvas, 600, 600);
		seeker = new Boid(canvas, 100, 100);

	}

	public void gameLoop() {
		double angleToTurn = 0;
		while (true) {
			seeker.unDraw();
			seeker.update(100);
			System.out.println("angle to target: " + seeker.angle(target));
			System.out.println("angle to turn:   " + angleToTurn);
			angleToTurn = (seeker.angle(target) - seeker.getCurrentAngle()) * COHESION;
			seeker.turn((int)angleToTurn);
			seeker.draw();
			Utils.pause(100);
		}
	}

	public static void main(String[] args) {
		TestCohesion test = new TestCohesion();
		test.gameLoop();
	}
}
