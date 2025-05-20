package geometry;

import java.awt.Color;

import drawing.Canvas;
import obstacle.Obstacle;

public class Rectangle implements Shape{

	private Canvas canvas;
	private Color color;
	private CartesianCoordinate topLeftCorner;
	private int xLength;
	private int yLength;
	private int area;
	private double radius;
	private CartesianCoordinate center;
	private CartesianCoordinate topRightCorner;
	private CartesianCoordinate bottomRightCorner;
	private CartesianCoordinate bottomLeftCorner;

	public Rectangle(Canvas canvas, CartesianCoordinate topLeftCorner, int xLength, int yLength, Color color) {
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


	@Override

	/**
	 * 
	 * @param point
	 * @param obstacle
	 * @return wether or not the specified point is inside the obstacle OR on its
	 *         border
	 */
	public boolean isInside(CartesianCoordinate point) {
		if (point.getX() >= topLeftCorner.getX()
				&& point.getX() <= topRightCorner.getX()
				&& point.getY() >= topLeftCorner.getY()
				&& point.getY() <= bottomLeftCorner.getY()) {
			return true;
		}
		return false;
	}
	
	/**
	 * draws the rectangle
	 */
	public void draw() {
		canvas.drawLineBetweenPoints(topLeftCorner, topRightCorner, color);
		canvas.drawLineBetweenPoints(topRightCorner, bottomRightCorner, color);
		canvas.drawLineBetweenPoints(bottomRightCorner, bottomLeftCorner, color);
		canvas.drawLineBetweenPoints(bottomLeftCorner, topLeftCorner, color);
	}

	/**
	 * erases the rectangle from the canvas
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
