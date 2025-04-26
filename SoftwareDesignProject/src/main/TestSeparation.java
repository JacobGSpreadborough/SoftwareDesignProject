package main;

import javax.swing.JFrame;

import drawing.Canvas;
import tools.Utils;
import turtle.Boid;

import java.awt.Color;

public class TestSeparation {
	private static final int WINDOW_SIZE_X = 800;
	private static final int WINDOW_SIZE_Y = 800;
	private static final double SEPARATION = 0.1;
	public static JFrame window = new JFrame();
	public static Canvas canvas = new Canvas();
	private Boid seeker;
	private Boid target;

	public TestSeparation() {
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
		target = new Boid(canvas, 400, 400, Color.BLACK);
		seeker = new Boid(canvas, 200, 405, Color.BLACK);
	}

	public void gameLoop() {
		double angleToTurn = 0;
		int deltaTime = 100;
		while (true) {
			seeker.unDraw();
			seeker.update(deltaTime);
			seeker.wrapPosition(WINDOW_SIZE_Y, WINDOW_SIZE_X);
			// print current angle of seeker
			System.out.print("angle to target: " + seeker.getCurrentPosition().angle(target.getCurrentPosition())
					+ " current angle: " + seeker.getCurrentAngle());
			// calculate angle to turn towards target
			angleToTurn = ((seeker.getCurrentPosition().angle(target.getCurrentPosition()) + 180) - seeker.getCurrentAngle())
					* SEPARATION;
			// print angle and turn
			System.out.println(" angle to turn: " + angleToTurn);
			seeker.turn((int) angleToTurn);
			seeker.draw(10);
			Utils.pause(deltaTime);
		}
	}

	public static void main(String[] args) {
		TestSeparation test = new TestSeparation();
		test.gameLoop();
	}
}
