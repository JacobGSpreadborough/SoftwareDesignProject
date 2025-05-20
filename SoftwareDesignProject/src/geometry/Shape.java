package geometry;

public interface Shape {
	
	public abstract boolean isInside(CartesianCoordinate point);
	
	public abstract void draw();
	
	
}
