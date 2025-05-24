package main;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import obstacle.MouseObstacle;
import obstacle.Obstacle;
import tools.Utils;
import turtle.Boid;

public class SimulationEngine {

	private int canvasWidth;
	private int canvasHeight;

	private double cohesion = 0.05;
	private double separation = 0.05;
	private double alignment = 0.05;
	private int population = 30;
	private int numObstacles = 3;
	private double range = 500;
	private List<Boid> boids = Collections.synchronizedList(new ArrayList<Boid>());
	private List<Obstacle> obstacles = Collections.synchronizedList(new ArrayList<Obstacle>());
	private Canvas canvas = new Canvas();
	private SimulationGUI gui;

	public SimulationEngine() {
		gui = new SimulationGUI(canvas, this);
		canvasWidth = canvas.getWidth();
		// make sure the simulation window fits with the GUI panel
		canvasHeight = canvas.getHeight();
		obstacleSetup();
		boidSetup();
		mouseSetup();
	}

	private void mouseSetup() {
		canvas.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// do nothing
			}

			@Override
			/**
			 * Creates an obstacle that tracks the mouse cursor on the screen the obstacle
			 * is added when the mouse enters the window, and destroyed when it exits
			 */
			public void mouseMoved(MouseEvent e) {
				CartesianCoordinate mousePosition = new CartesianCoordinate(e.getX(), e.getY());
				synchronized (obstacles) {
					obstacles.remove(obstacles.size() - 1);
					obstacles.add(new MouseObstacle(canvas, mousePosition, 100));
				}
			}

		});
		canvas.addMouseListener(new MouseListener() {

			CartesianCoordinate mousePosition;
			boolean leftMouseDown = false;

			@Override
			/**
			 * Right clicking removes the last obstacle added by the user The three starting
			 * obstacles cannot be removed
			 */
			public void mouseClicked(MouseEvent e) {
				// right click only
				if (e.getButton() == MouseEvent.BUTTON3) {
					if (obstacles.size() < 5) {
						System.out.println("Cannot remove that obstacle!");
					} else {
						synchronized (obstacles) {
							// The final index of the obstacle array is always occupied by the mouse
							// obstacle, most recent added obstacle will always be in the seconed to last
							// index
							obstacles.get(obstacles.size() - 2).unDraw();
							obstacles.remove(obstacles.size() - 2);
							numObstacles--;
						}
					}
				}

			}

			@Override
			/**
			 * On a left click, prepares to create a new obstacle
			 */
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					leftMouseDown = true;
					mousePosition = new CartesianCoordinate(e.getX(), e.getY());
					synchronized (obstacles) {
						// removes the mouse obstacle
						obstacles.remove(obstacles.size() - 1);
					}
				}
			}

			@Override
			/**
			 * Allows the use to create an obstacle by clicking and dragging the mouse
			 */
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					leftMouseDown = false;
					// only create the obstacle if the mouse is on the canvas
					if (e.getY() < canvasHeight && e.getX() < canvasWidth && e.getX() > 0 && e.getY() > 0) {
						// gets distance travelled since the mouse button was pressed
						int xLength = (int) (e.getX() - mousePosition.getX());
						int yLength = (int) (e.getY() - mousePosition.getY());
						synchronized (obstacles) {
							// create and draw the new obstacle
							obstacles.add(new Obstacle(canvas, mousePosition, xLength, yLength, Color.RED));
							obstacles.get(obstacles.size() - 1).draw();
							// get position of the mouse at release
							mousePosition = new CartesianCoordinate(e.getX(), e.getY());
							// create a new mouse obstacle
							obstacles.add(new MouseObstacle(canvas, mousePosition, 0));
							numObstacles++;

						}
					}
				}
			}

			@Override
			/**
			 * creates the mouse obstacle when the mouse enters the canvas
			 */
			public void mouseEntered(MouseEvent e) {
				CartesianCoordinate mousePosition = new CartesianCoordinate(e.getX(), e.getY());
				synchronized (obstacles) {
					obstacles.add(new MouseObstacle(canvas, mousePosition, 50));
				}
			}

			@Override
			/**
			 * removes the mouse obstacle when the mouse leaves the canvas
			 */
			public void mouseExited(MouseEvent e) {
				if (leftMouseDown == false) {
					synchronized (obstacles) {
						obstacles.remove(obstacles.size() - 1);
					}
				}

			}

		});
	}

	/**
	 * sets up obstacles for the boids to avoid
	 */
	private void obstacleSetup() {
		obstacles.add(new Obstacle(canvas, new CartesianCoordinate(100, 300), 120, 80, Color.RED));
		obstacles.add(new Obstacle(canvas, new CartesianCoordinate(350, 200), 80, 150, Color.RED));
		obstacles.add(new Obstacle(canvas, new CartesianCoordinate(550, 100), 150, 120, Color.RED));
		for (Obstacle obstacle : obstacles) {
			obstacle.draw();
		}
	}

	/**
	 * spawns in the boids at random positions and angles prevents boids from
	 * spawning inside obstacles
	 */
	private void boidSetup() {
		for (int i = 0; i < population; i++) {
			addBoid();
		}
		for (Boid boid : boids) {
			boid.turn(Utils.randomInt(-180, 180));
		}
	}

	/**
	 * function to add a boid, making sure it isn't inside of any obstacles
	 */
	public void addBoid() {
		CartesianCoordinate startingPoint = new CartesianCoordinate(Utils.randomInt(0, canvasWidth),
				Utils.randomInt(0, canvasHeight));
		for (Obstacle obstacle : obstacles) {
			// prevents boids spawning too close to obstacles
			if (startingPoint.toroidDistance(obstacle.getCenter(), canvasWidth, canvasHeight) < obstacle.getRadius()) {
				startingPoint = new CartesianCoordinate(0, 0);
			}
		}
		synchronized (boids) {
			boids.add(new Boid(canvas, startingPoint, canvasWidth, canvasHeight, Color.BLACK));
		}
	}

	/**
	 * draw objects on screen
	 */
	private void render() {
		for (Boid boid : boids) {
			boid.draw();
		}
		for (Obstacle obstacle : obstacles) {
			obstacle.draw();
		}
	}

	/**
	 * update movement
	 * 
	 * @param deltaTime
	 */
	private void update(int deltaTime) {
		for (Boid boid : boids) {
			boid.wrapPosition(canvasWidth, canvasHeight);
			boid.flocking(boids, getCohesion(), getSeparation(), getAlignment(), getRange());
			boid.avoidObstacles(obstacles);
			boid.update(deltaTime);
		}
	}

	/**
	 * clear everything off the screen
	 */
	private void clearScreen() {
		for (Boid boid : boids) {
			boid.unDraw();
		}
		for (Obstacle obstacle : obstacles) {
			obstacle.unDraw();
		}

	}

	/**
	 * runs the simulation
	 * 
	 * @param deltaTime
	 */
	public void start(int deltaTime) {
		while (true) {
			long startTime = System.currentTimeMillis();
			synchronized (obstacles) {
				synchronized (boids) {
					clearScreen();
					update(10);
					render();
				}
			}
			Utils.pause(deltaTime);
			// print out time elapsed while running the loop
			double framesPerSecond = 1000 / (System.currentTimeMillis() - startTime);
			gui.getstatusMonitor().setText("Obstacles: " + numObstacles + " Boids: " + population + " FPS: "
					+ (int) framesPerSecond);
		}
	}

	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public List<Boid> getBoids() {
		return boids;
	}

	public double getCohesion() {
		return cohesion;
	}

	public void setCohesion(double cohesion) {
		this.cohesion = cohesion;
	}

	public double getSeparation() {
		return separation;
	}

	public void setSeparation(double separation) {
		this.separation = separation;
	}

	public double getAlignment() {
		return alignment;
	}

	public void setAlignment(double alignment) {
		this.alignment = alignment;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public void setCanvasWidth(int canvasWidth) {
		this.canvasWidth = canvasWidth;
	}

	public void setCanvasHeight(int canvasHeight) {
		this.canvasHeight = canvasHeight;
	}
}
