package guiElements;

import javafx.scene.shape.Polygon;

public class Rectangle extends ShapeBuilder {

	private static final String RECTANGLE = "Rectangle";

	public Rectangle() {
		this(RECTANGLE);
	}

	public Rectangle(String description) {
		super(description);
	}

	@Override
	public Polygon makeShape(double paddedHeight, double paddedWidth,
			double rows, double cols) {

		myHeight = paddedHeight / rows;
		myWidth = paddedWidth / cols;

		Double[] points = new Double[] { 0., 0., myWidth, 0., myWidth, myHeight,
				0., myHeight };
		
		Polygon shape = new Polygon();
		shape.getPoints().setAll(points);

		return shape;
	}

	@Override
	public void move(Polygon shape, int row, int col, double vPad, double hPad) {		
		shape.setTranslateX(hPad + col*myWidth);
		shape.setTranslateY(vPad + row*myHeight);
		return;
	}

}
