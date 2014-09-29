package shapeFactories;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.shape.Polygon;

import org.junit.Test;

public class ShapeFactoryTest {

	@Test
	public void testImplementations() {
		Map<String, ShapeFactory> myPossibleShapes = new HashMap<String, ShapeFactory>();
		
		myPossibleShapes.put("Rectangle", new Rectangle());
		myPossibleShapes.put("Triangle", new Triangle());
		myPossibleShapes.put("Hexagon", new Hexagon());
		myPossibleShapes.put("Tessalated", new Tessalated());
	}
	
	@Test
	public void testRectangleAlignments() {
		ShapeFactory factory = new Rectangle();
		int numRows = 10;
		int numCols = 10;
		double paddedHeight = 200;
		double paddedWidth = 200;
		Polygon[][] shapes = new Polygon[numRows][numCols];
		
		for (int col = 0; col < numCols; col++) {
			for (int row = 0; row < numRows; row++) {
				Polygon shape = factory.makeShape(paddedHeight, paddedWidth, 10, 10);
				factory.move(shape, row, col, 0, 0);
				shapes[row][col] = shape;
			}
		}
		
		//Test bottom left corner and top left corner alignment
		double xCoordA = shapes[0][0].getTranslateX();
		double xCoordB = shapes[1][0].getTranslateX();
		double yCoordA = shapes[0][0].getTranslateY() + shapes[0][0].getPoints().get(7); //Get bottom left corner point
		double yCoordB = shapes[1][0].getTranslateY() + shapes[1][0].getPoints().get(1);
		
		if (!(xCoordA==xCoordB && yCoordA==yCoordB)){
			fail("Rectangle Misaligned!");
		}
	}
	
	@Test
	public void testHexagonAlignments() {
		ShapeFactory factory = new Hexagon();
		int numRows = 10;
		int numCols = 10;
		double paddedHeight = 200;
		double paddedWidth = 200;
		Polygon[][] shapes = new Polygon[numRows][numCols];
		
		for (int col = 0; col < numCols; col++) {
			for (int row = 0; row < numRows; row++) {
				Polygon shape = factory.makeShape(paddedHeight, paddedWidth, 10, 10);
				factory.move(shape, row, col, 0, 0);
				shapes[row][col] = shape;
			}
		}
		
		//Test bottom corner and top left corner alignment
		double xCoordA = shapes[0][0].getTranslateX() + shapes[0][0].getPoints().get(6);
		double xCoordB = shapes[0][1].getTranslateX() + shapes[0][1].getPoints().get(10);
		double yCoordA = shapes[0][0].getTranslateY() + shapes[0][0].getPoints().get(7);
		double yCoordB = shapes[0][1].getTranslateY() + shapes[0][1].getPoints().get(11);
		
		if (!(xCoordA==xCoordB && yCoordA==yCoordB)){
			System.out.println("xCoordA: " + Double.toString(xCoordA));
			System.out.println("xCoordB: " + Double.toString(xCoordB));
			System.out.println("yCoordA: " + Double.toString(yCoordA));
			System.out.println("yCoordB: " + Double.toString(yCoordB));
			fail("Hexagon Misaligned!");
		}
		
		//Test bottom corner and top right corner alignment
		double xCoordC = shapes[0][1].getTranslateX() + shapes[0][1].getPoints().get(6);
		double xCoordD = shapes[1][0].getTranslateX() + shapes[1][0].getPoints().get(2);
		double yCoordC = shapes[0][1].getTranslateY() + shapes[0][1].getPoints().get(7);
		double yCoordD = shapes[1][0].getTranslateY() + shapes[1][0].getPoints().get(3);
		
		if (!(xCoordC==xCoordD && yCoordC==yCoordD)){
			System.out.println("xCoordC: " + Double.toString(xCoordC));
			System.out.println("xCoordD: " + Double.toString(xCoordD));
			System.out.println("yCoordC: " + Double.toString(yCoordC));
			System.out.println("yCoordD: " + Double.toString(yCoordD));
			fail("Hexagon Misaligned!");
		}
	}

}
