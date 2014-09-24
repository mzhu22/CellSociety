package guiElements;

import javafx.scene.shape.Polygon;

public abstract class ShapeBuilder {
	
	private String myDescription;
	protected double myWidth;
	protected double myHeight;
	
	public ShapeBuilder(String description) {
		myDescription = description;
	}
	
	public Polygon makeShape(double paddedHeight, double paddedWidth, double rows, double cols) {	
		setDimensions(paddedHeight, paddedWidth, rows, cols);		
		Polygon shape = new Polygon();
		shape.getPoints().setAll(setCoords());
		return shape;
	}
	
	protected abstract void setDimensions(double paddedHeight, double paddedWidth,
			double rows, double cols);

	protected abstract Double[] setCoords();
	
	public abstract void move(Polygon shape, int row, int col, double vPad, double hPad);
	
	public String getDescription() {
		return myDescription;
	}

}
