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
