package main;

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
	private static final int POPULATION = 3;
	public static JFrame window = new JFrame();
	public static Canvas canvas = new Canvas();
	public ArrayList<Boid> boids = new ArrayList<Boid>();
	public Boid centerOfGravity;

	public Main() {
		boidSetup();
		windowSetup();
	}

	private void boidSetup() {
		for (int i = 0; i < POPULATION; i++) {
			boids.add(new Boid(canvas, Utils.randomInt(0, WINDOW_SIZE_X), Utils.randomInt(0, WINDOW_SIZE_Y)));
		}
		// 
		int xTotal = 0;
		int yTotal = 0;
		for(Boid boid : boids) {
			xTotal += boid.getPositionX();
			yTotal += boid.getPositionY();
		}
		centerOfGravity = new Boid(canvas, xTotal/POPULATION, yTotal/POPULATION);
	}

	private void windowSetup() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		window.setVisible(true);
		window.add(canvas);
	}

	

	public static void main(String[] args) {
		Main main = new Main();
	}

}
