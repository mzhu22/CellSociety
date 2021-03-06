package shapeFactories;

import javafx.scene.shape.Polygon;

/** 
 * @author Brian Bolze
 * Inheritance hierarchy used to build shapes for the grid to use
 */
public abstract class ShapeFactory {

	private String myDescription;
	protected double myWidth;
	protected double myHeight;

	public ShapeFactory(String description) {
		myDescription = description;
	}

	public Polygon makeShape(double paddedHeight, double paddedWidth,
			double rows, double cols) {
		setDimensions(paddedHeight, paddedWidth, rows, cols);
		Polygon shape = new Polygon();
		shape.getPoints().setAll(setCoords());
		return shape;
	}

	protected abstract void setDimensions(double paddedHeight,
			double paddedWidth, double rows, double cols);

	protected abstract Double[] setCoords();

	public abstract void move(Polygon shape, int row, int col, double vPad,
			double hPad);

	public String getDescription() {
		return myDescription;
	}

}
