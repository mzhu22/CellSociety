package shapeFactories;

import javafx.scene.shape.Polygon;

public class Triangle extends ShapeFactory {

	private static final String TRIANGLE = "Triangle";

	public Triangle() {
		this(TRIANGLE);
	}

	public Triangle(String description) {
		super(description);
	}
	
	protected Double[] setCoords() {
		Double[] points = new Double[] { 0., 0., myWidth, 0., 0.5 * myWidth, myHeight};
		return points;
	}
	
	protected void setDimensions(double paddedHeight, double paddedWidth,
			double rows, double cols) {
		myHeight = paddedHeight / rows;
		myWidth = paddedWidth / (cols / 2. + 0.5);
		myWidth += 2.*myWidth/cols;
	}

	public void move(Polygon shape, int row, int col, double vPad, double hPad) {
		shape.setTranslateX(hPad - myWidth/2. + (double)col * myWidth/2.);
		shape.setTranslateY(vPad + myHeight * row);
		if (col%2 == 1) {
			shape.setRotate(180.);
		}	
		if (row%2 == 1) {
			shape.setRotate(shape.getRotate() + 180.);
		}

	}

}
