package geometry;

public class LineSegment {

	private final CartesianCoordinate startPoint;
	private final CartesianCoordinate endPoint;

	public LineSegment(CartesianCoordinate startPoint, CartesianCoordinate endPoint) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public double length() {
		// get distance in both dimensions
		double xDistance = Math.abs(startPoint.getX() - endPoint.getX());
		double yDistance = Math.abs(startPoint.getY() - endPoint.getY());
		// Pythagoras
		return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
	}

	public CartesianCoordinate getStartPoint() {
		return startPoint;
	}

	public CartesianCoordinate getEndPoint() {
		return endPoint;
	}
}
