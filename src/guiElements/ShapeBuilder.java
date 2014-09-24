package guiElements;

import javafx.scene.shape.Polygon;

public abstract class ShapeBuilder {
	
	private String myDescription;
	protected double myWidth;
	protected double myHeight;
	
	public ShapeBuilder(String description) {
		myDescription = description;
	}
	
	public abstract Polygon makeShape(double paddedHeight, double paddedWidth, double rows, double cols);
	
	public abstract void move(Polygon shape, int row, int col, double vPad, double hPad);
	
	public String getDescription() {
		return myDescription;
	}

}
