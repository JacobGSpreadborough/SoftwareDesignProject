package main;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import geometry.LineSegment;
import tools.Utils;
import turtle.Boid;

public class Main {

	private static final int WINDOW_SIZE_X = 800;
	private static final int WINDOW_SIZE_Y = 800;
	private static final int POPULATION = 2;
	public static JFrame window = new JFrame();
	public static Canvas canvas = new Canvas();
	public ArrayList<Boid> boids = new ArrayList<Boid>();

	public Main() {
		windowSetup();
	}

	private void boidSetup() {
		for (int i = 0; i < POPULATION; i++) {
			boids.add(new Boid(canvas, Utils.randomInt(0, WINDOW_SIZE_X), Utils.randomInt(0, WINDOW_SIZE_Y), Color.RED));
		}
	}

	private void windowSetup() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		window.setVisible(true);
		window.add(canvas);
	}

	public void testBoidAngle() {
		boids.add(new Boid(canvas, 100,100,Color.BLACK));
		boids.add(new Boid(canvas, 50, 50,Color.BLACK));
		// System.out.println("the angle between boids 1 and 2 is " + boids.get(0).angle(boids.get(1)));
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.testBoidAngle();
		// penis
	}

}
