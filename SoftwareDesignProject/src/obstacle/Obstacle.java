package obstacle;

import java.awt.Color;
import java.util.ArrayList;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import geometry.LineSegment;

public class Obstacle {

	private Canvas canvas;
	private Color color;

	private final CartesianCoordinate topLeftCorner;
	private final CartesianCoordinate topRightCorner;
	private final CartesianCoordinate bottomLeftCorner;
	private final CartesianCoordinate bottomRightCorner;

	private final double radius;
	public final CartesianCoordinate center;
	private final int xLength;
	private final int yLength;
	private final int area;

	public Obstacle(Canvas canvas, CartesianCoordinate topLeftCorner, int xLength, int yLength, Color color) {
		this.canvas = canvas;
		this.color = color;
		this.topLeftCorner = topLeftCorner;
		this.xLength = xLength;
		this.yLength = yLength;
		this.area = xLength * yLength;
		this.radius = Math.hypot(xLength / 2, yLength / 2);
		this.center = new CartesianCoordinate(topLeftCorner.getX() + (xLength / 2),
				topLeftCorner.getY() + (yLength / 2));
		topRightCorner = new CartesianCoordinate(topLeftCorner.getX() + xLength, topLeftCorner.getY());
		bottomRightCorner = new CartesianCoordinate(topLeftCorner.getX() + xLength, topLeftCorner.getY() + yLength);
		bottomLeftCorner = new CartesianCoordinate(topLeftCorner.getX(), topLeftCorner.getY() + yLength);
		draw();
	}

	/**
	 * draws the obstacle
	 */
	public void draw() {
		canvas.drawLineBetweenPoints(topLeftCorner, topRightCorner, color);
		canvas.drawLineBetweenPoints(topRightCorner, bottomRightCorner, color);
		canvas.drawLineBetweenPoints(bottomRightCorner, bottomLeftCorner, color);
		canvas.drawLineBetweenPoints(bottomLeftCorner, topLeftCorner, color);
	}

	/**
	 * erases the obstacle from the canvas
	 */
	public void unDraw() {
		canvas.removeMostRecentLine();
		canvas.removeMostRecentLine();
		canvas.removeMostRecentLine();
		canvas.removeMostRecentLine();
	}

	public int getxLength() {
		return xLength;
	}

	public int getyLength() {
		return yLength;
	}

	public int getArea() {
		return area;
	}

	public CartesianCoordinate getTopLeftCorner() {
		return topLeftCorner;
	}

	public CartesianCoordinate getCenter() {
		return center;
	}

	public double getRadius() {
		return radius;
	}

}
