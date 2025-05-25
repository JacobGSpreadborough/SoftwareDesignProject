package obstacle;

import java.awt.Color;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import simulationObject.SimulationObject;

public class MouseObstacle extends Obstacle implements SimulationObject {

	// creates an obstacle at a point with size 0
	public MouseObstacle(Canvas canvas, CartesianCoordinate position, double radius) {
		super(canvas, position, 0, 0, Color.BLACK);
		this.radius = radius;
	}

	/*
	 * The mouse obstacle is invisible
	 */
	public void draw() {
		// do nothing
	}

	public void unDraw() {
		// do nothing
	}

}
