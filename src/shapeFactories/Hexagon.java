// This entire file is part of my masterpiece.
// Brian Bolze

package shapeFactories;

import javafx.scene.shape.Polygon;

public class Hexagon extends ShapeFactory {

	private static final String HEXAGON = "Hexagon";

	public Hexagon() {
		this(HEXAGON);
	}

	public Hexagon(String description) {
		super(description);
	}
	
	protected void setDimensions(double paddedHeight, double paddedWidth,
			double rows, double cols) {
		myWidth = paddedWidth / (cols / 2. + 0.5);
		myHeight = (2./3.) * paddedHeight / rows;
		myWidth += 2. * myWidth / cols;
	}
	
	protected Double[] setCoords() {
		double edge = myHeight / 2.;
		double z = myHeight / 4.;
		Double[] points = new Double[] { 0., 0., // top
				myWidth / 2., z, // top right
				myWidth / 2., z + edge, // bottom right
				0., myHeight, // bottom
				-myWidth / 2., z + edge, // bottom left
				-myWidth / 2., z // top left
		};
		return points;
	}

	public void move(Polygon shape, int row, int col, double vPad, double hPad) {
		shape.setTranslateX(hPad + (double) col * myWidth / 2.);
		shape.setTranslateY(vPad + (3. * myHeight / 2.) * (double) row);
		if (col % 2 == 1)
			shape.setTranslateY(shape.getTranslateY() + (3. * myHeight / 4.));
	}

}
