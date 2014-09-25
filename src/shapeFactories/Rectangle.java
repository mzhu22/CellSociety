package shapeFactories;

import javafx.scene.shape.Polygon;

public class Rectangle extends ShapeFactory {

	private static final String RECTANGLE = "Rectangle";

	public Rectangle() {
		this(RECTANGLE);
	}

	public Rectangle(String description) {
		super(description);
	}

	protected Double[] setCoords() {
		Double[] points = new Double[] { 0., 0., myWidth, 0., myWidth,
				myHeight, 0., myHeight };
		return points;
	}

	protected void setDimensions(double paddedHeight, double paddedWidth,
			double rows, double cols) {
		myHeight = paddedHeight / rows;
		myWidth = paddedWidth / cols;
	}

	@Override
	public void move(Polygon shape, int row, int col, double vPad, double hPad) {
		shape.setTranslateX(hPad + col * myWidth);
		shape.setTranslateY(vPad + row * myHeight);
		return;
	}

}
