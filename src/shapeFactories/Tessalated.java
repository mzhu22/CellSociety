package shapeFactories;

import javafx.scene.shape.Polygon;

public class Tessalated extends ShapeFactory {
	
	private static final String TESSALATED = "Tessalated";

	public Tessalated() {
		this(TESSALATED);
	}
	
	public Tessalated(String description) {
		super(description);
	}

	@Override
	protected void setDimensions(double paddedHeight, double paddedWidth,
			double rows, double cols) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Double[] setCoords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void move(Polygon shape, int row, int col, double vPad, double hPad) {
		// TODO Auto-generated method stub

	}

}
