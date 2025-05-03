package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import tools.Utils;
import turtle.Boid;

public class MainTest {
	private static final int WINDOW_SIZE_X = 1800;
	private static final int WINDOW_SIZE_Y = 1000;
	private static double cohesion = 0.5;
	private static double separation = 0.5;
	private static double alignment = 0.5;
	private static double range = 500;
	private static final int POPULATION = 50;
	private static final int BOID_SIZE = 10;
	public static JFrame window = new JFrame();
	public static Canvas canvas = new Canvas();
	public static JPanel upperPanel = new JPanel();
	public static JPanel lowerPanel = new JPanel();
	private ArrayList<Boid> boids = new ArrayList<Boid>();

	public MainTest() {
		windowSetup();
		sliderSetup();
		boidSetup();
		window.validate();
	}

	private void windowSetup() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		// make the lower panel a grid with 2 columns
		lowerPanel.setLayout(new GridLayout(0, 2));
		window.add(canvas);
		window.add(lowerPanel, BorderLayout.SOUTH);
		window.setVisible(true);
	}

	private void sliderSetup() {
		JSlider cohesionSlider = new JSlider();
		cohesionSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				cohesion = (double) cohesionSlider.getValue() / 200;
				System.out.println("cohesion: " + cohesion);
			}
		});
		JSlider separationSlider = new JSlider();
		separationSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				separation = (double) separationSlider.getValue() / 200;
				System.out.println("separation: " + separation);
			}
		});
		JSlider alignmentSlider = new JSlider();
		alignmentSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				alignment = (double) alignmentSlider.getValue() / 200;
				System.out.println("alignment: " + alignment);
			}
		});
		JSlider rangeSlider = new JSlider();
		rangeSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				range = rangeSlider.getValue() * 10;
				System.out.println("range: " + range);
			}
		});
		sliderLabelSetup(cohesionSlider, "Cohesion");
		sliderLabelSetup(separationSlider, "Separation");
		sliderLabelSetup(alignmentSlider, "Alignment");
		sliderLabelSetup(rangeSlider, "Range");
	}

	private void sliderLabelSetup(JSlider slider, String name) {
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(Integer.valueOf(0), new JLabel("0"));
		labelTable.put(Integer.valueOf(25), new JLabel("0.25"));
		labelTable.put(Integer.valueOf(50), new JLabel("0.5"));
		labelTable.put(Integer.valueOf(75), new JLabel("0.75"));
		labelTable.put(Integer.valueOf(100), new JLabel("1"));
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setLabelTable(labelTable);
		slider.setPaintLabels(true);
		JPanel sliderAndLabel = new JPanel();
		sliderAndLabel.setLayout(new BoxLayout(sliderAndLabel, BoxLayout.Y_AXIS));
		JLabel sliderLabel = new JLabel(name);
		sliderAndLabel.add(sliderLabel);
		sliderAndLabel.add(slider);
		lowerPanel.add(sliderAndLabel);
	}

	private void boidSetup() {
		for (int i = 0; i < POPULATION; i++) {
			boids.add(new Boid(canvas, Utils.randomInt(0, WINDOW_SIZE_X), Utils.randomInt(0, WINDOW_SIZE_Y),
					Color.BLACK));
		}
		for (Boid boid : boids) {
			// boid.turn(Utils.randomInt(-180, 180));
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
			if (boid.getCurrentPosition().distance(boidB.getCurrentPosition()) < range) {
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
			if (boid.getCurrentPosition().distance(boidB.getCurrentPosition()) < range) {
				localPopulation++;
				totalAngle += boidB.getCurrentAngle();
			}
		}
		return totalAngle / localPopulation;
	}

	/**
	 * 
	 * @param boid
	 */
	private void flocking(Boid boid) {
		double angleToTurn = 0;
		try {
			angleToTurn += ((boid.getCurrentPosition().angle(localCenterOfGravity(boid)) + 180 - boid.getCurrentAngle())
					* separation);
			angleToTurn += ((boid.getCurrentPosition().angle(localCenterOfGravity(boid)) - boid.getCurrentAngle())
					* cohesion);
		} catch (NullPointerException npe) {
			angleToTurn += 0;
		} finally {

			angleToTurn += (localAverageAngle(boid) - boid.getCurrentAngle()) * alignment;
			boid.turn((int) angleToTurn);
		}
	}

	/**
	 * runs the simulation
	 */
	public void gameLoop() {
		int deltaTime = 10;
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
