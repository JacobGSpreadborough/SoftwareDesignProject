package obstacle;

import java.awt.Color;
import java.util.ArrayList;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import geometry.LineSegment;
import geometry.Rectangle;

public class Obstacle extends Rectangle{

	
	public Obstacle(Canvas canvas, CartesianCoordinate topLeftCorner, int xLength, int yLength, Color color) {
		super(canvas, topLeftCorner, yLength, yLength, color);
	}


}
