package geometry;

public class CartesianCoordinate {

	private final double xPosition;
	private final double yPosition;

	public CartesianCoordinate(double xPosition, double yPosition) {
		super();
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	/**
	 * Returns distance between two points
	 * 
	 * @param point
	 * @return distance between this point and some other point
	 */
	public double distance(CartesianCoordinate point) {
		return new LineSegment(this, point).getLength();
	}

	/**
	 * 
	 * @param point
	 * @param xWindowSize
	 * @param yWindowSize
	 * @return
	 */
	public double toroidDistance(CartesianCoordinate point, int xWindowSize, int yWindowSize) {
		double xDistance = Math.abs(this.getX() - point.getX());
		double yDistance = Math.abs(this.getY() - point.getY());
		if (xDistance > xWindowSize / 2) {
			xDistance = xWindowSize - xDistance;
		}
		if (yDistance > yWindowSize / 2) {
			yDistance = yWindowSize - yDistance;
		}
		return Math.hypot(xDistance, yDistance);
	}

	/**
	 * Returns angle between two point
	 * 
	 * @param point
	 * @return angle between this point and some other point
	 */
	public double angle(CartesianCoordinate point) {
		return new LineSegment(this, point).getAngle();
	}

	public double getX() {
		return xPosition;
	}

	public double getY() {
		return yPosition;
	}

	public String toString() {
		return "X: " + getX() + " Y: " + getY();
	}

}
