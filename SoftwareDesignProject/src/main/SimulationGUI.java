package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import drawing.Canvas;

public class SimulationGUI {

	private final Dimension WINDOW_SIZE = new Dimension(1800, 1000);
	private JPanel lowerPanel = new JPanel();
	private JLabel statusMonitor = new JLabel();

	private SimulationEngine engine;
	private Canvas canvas;

	public SimulationGUI(Canvas canvas, SimulationEngine engine) {

		this.canvas = canvas;
		this.engine = engine;

		sliderSetup();
		buttonSetup();
		windowSetup();

	}

	/**
	 * intializes the game window
	 */
	private void windowSetup() {
		JFrame window = new JFrame();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(WINDOW_SIZE);
		// make the lower panel a grid with 2 columns
		lowerPanel.setLayout(new GridLayout(0, 2));
		lowerPanel.setBackground(Color.GRAY);
		canvas.add(statusMonitor);

		window.add(canvas);
		window.add(getLowerPanel(), BorderLayout.SOUTH);
		window.setTitle("Flocking Simulation");
		window.setVisible(true);
		window.validate();
	}

	/**
	 * sets up and adds the buttons to add and remove boids
	 */
	private void buttonSetup() {
		JButton addBoidButton = new JButton("Add Boid");
		addBoidButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				engine.setPopulation(engine.getPopulation() + 1);
				engine.addBoid();
			}
		});
		JButton removeBoidButton = new JButton("Remove Boid");
		removeBoidButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized (engine.getBoids()) {
					if (engine.getPopulation() > 0) {
						engine.setPopulation(engine.getPopulation() - 1);
						;
						engine.getBoids().get(0).unDraw();
						engine.getBoids().remove(0);
					} else if (engine.getPopulation() <= 0) {
						System.out.println("No boids to remove");
					}
				}
			}
		});

		getLowerPanel().add(addBoidButton);
		getLowerPanel().add(removeBoidButton);
	}

	/**
	 * creates sliders for the various parameters
	 */
	private void sliderSetup() {
		JSlider cohesionSlider = new JSlider();
		cohesionSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				engine.setCohesion((double) cohesionSlider.getValue() / 1000);
				System.out.println("cohesion: " + engine.getCohesion());
			}
		});
		JSlider separationSlider = new JSlider();
		separationSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				engine.setSeparation((double) separationSlider.getValue() / 1000);
				System.out.println("separation: " + engine.getSeparation());
			}
		});
		JSlider alignmentSlider = new JSlider();
		alignmentSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				engine.setAlignment((double) alignmentSlider.getValue() / 1000);
				System.out.println("alignment: " + engine.getAlignment());
			}
		});
		JSlider rangeSlider = new JSlider();
		rangeSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// minimum visual range of 200
				engine.setRange((rangeSlider.getValue() * 10));
				System.out.println("range: " + engine.getRange());
			}
		});
		getLowerPanel().add(sliderLabel(cohesionSlider, "Cohesion", 0, 1, 5));
		getLowerPanel().add(sliderLabel(separationSlider, "Separation", 0, 1, 5));
		getLowerPanel().add(sliderLabel(alignmentSlider, "Alignment", 0, 1, 5));
		getLowerPanel().add(sliderLabel(rangeSlider, "Range", 0, 1000, 5));
	}

	/**
	 * creates a subpanel to contain a slider and its title adds numeric labels to
	 * the slider based on the input arguments assumes the slider is the default 0 -
	 * 100 range
	 * 
	 * @param slider
	 * @param name
	 * @param min    minumum value on the slider label
	 * @param max    maximum value on the slider label
	 * @param ticks  amount of numbers on the slider label
	 * @return a panel consisting of a slider with ticks and numeric labels below
	 *         it, and a title above it
	 */
	private JPanel sliderLabel(JSlider slider, String name, int min, int max, int ticks) {
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		// calculate spacing on the slider, along with values to place on the label
		for (int i = 0; i < ticks; i++) {
			int sliderValue = (100 / (ticks - 1)) * i;
			double labelValue = ((max / (double) (ticks - 1)) * i) + min;
			labelTable.put(Integer.valueOf(sliderValue), new JLabel(Double.toString(labelValue)));
		}

		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setLabelTable(labelTable);
		slider.setPaintLabels(true);
		JPanel sliderAndLabel = new JPanel();
		sliderAndLabel.setLayout(new BoxLayout(sliderAndLabel, BoxLayout.Y_AXIS));
		JLabel sliderLabel = new JLabel(name);
		sliderAndLabel.add(sliderLabel);
		sliderAndLabel.add(slider);
		sliderAndLabel.setBackground(Color.GRAY);
		slider.setBackground(Color.GRAY);
		return sliderAndLabel;
	}

	public JPanel getLowerPanel() {
		return lowerPanel;
	}

	public JLabel getstatusMonitor() {
		return statusMonitor;
	}

}
