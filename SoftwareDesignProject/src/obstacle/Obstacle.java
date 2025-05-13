package obstacle;

import java.awt.Color;
import java.util.ArrayList;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import geometry.LineSegment;

public class Obstacle {

	private Canvas canvas;
	private Color color;
	private final LineSegment[] sides = new LineSegment[4];
	private final LineSegment top;
	private final LineSegment bottom;
	private final LineSegment left;
	private final LineSegment right;
	private final CartesianCoordinate topLeftCorner;
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
		top = new LineSegment(topLeftCorner,
				new CartesianCoordinate(topLeftCorner.getX() + xLength, topLeftCorner.getY()));
		bottom = new LineSegment(new CartesianCoordinate(topLeftCorner.getX(), topLeftCorner.getY() + yLength),
				new CartesianCoordinate(topLeftCorner.getX() + xLength, topLeftCorner.getY() + yLength));
		left = new LineSegment(topLeftCorner,
				new CartesianCoordinate(topLeftCorner.getX(), topLeftCorner.getY() + yLength));
		right = new LineSegment(new CartesianCoordinate(topLeftCorner.getX() + xLength, topLeftCorner.getY()),
				new CartesianCoordinate(topLeftCorner.getX() + xLength, topLeftCorner.getY() + yLength));
		sides[0] = top;
		sides[1] = right;
		sides[2] = bottom;
		sides[3] = left;

		draw();
	}

	/**
	 * draws the obstacle
	 */
	public void draw() {
		canvas.drawLineSegments(sides, color);
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

}
