package geometry;

public class LineSegment {

	private final CartesianCoordinate startPoint;
	private final CartesianCoordinate endPoint;
	private final double xDistance;
	private final double yDistance;
	private final double length;
	private final double angle;

	public LineSegment(CartesianCoordinate startPoint, CartesianCoordinate endPoint) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.xDistance = Math.abs(startPoint.getX() - endPoint.getX());
		this.yDistance = Math.abs(startPoint.getY() - endPoint.getY());
		this.length = Math.hypot(xDistance, yDistance);
		this.angle = Math.atan2(xDistance, yDistance);
	}

	public CartesianCoordinate getStartPoint() {
		return startPoint;
	}

	public CartesianCoordinate getEndPoint() {
		return endPoint;
	}

	public double getLength() {
		return length;
	}

	public double getAngle() {
		return angle;
	}

}
