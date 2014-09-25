package frontEnd;

import guiShapes.Hexagon;
import guiShapes.Rectangle;
import guiShapes.ShapeBuilder;
import guiShapes.Triangle;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import backEnd.Grid;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class AnimatorLoop {

	public static final int HEIGHT_OFFSET = 112;
	public static final int WIDTH_OFFSET = 40;
	public static final int GRID_BORDER_WIDTH = 1;

	private CASettings mySettings;
	
	private static final double V_PAD = 0;
	private static final double H_PAD = 0;
	private double PADDED_WIDTH;
	private double PADDED_HEIGHT;
	private int HEIGHT;
	private int WIDTH;
	private int NUM_ROWS;
	private int NUM_COLS;

	private Group myNodes;
	private ShapeBuilder myShapeBuilder;
	private static Map<String, ShapeBuilder> myPossibleShapeBuilders = new HashMap<String, ShapeBuilder>();

	private boolean initialized = false;

	private Grid myGrid;
	private Polygon[][] myGUICells;
	private Patch[][] myPatches;

	/*
	 * These constants constants are set when the XML file is read, specifying
	 * the size of the CA sim board. They will be used to determine the size of
	 * each Cell in the grid.
	 */

	/**
	 * Constructor
	 * 
	 * @param width
	 *            = width of stage in pixels
	 * @param height
	 *            = height of stage MINUS height of the menu on the top
	 */
	public AnimatorLoop(int width, int height) {
		WIDTH = width - WIDTH_OFFSET;
		HEIGHT = height - HEIGHT_OFFSET;
		PADDED_WIDTH = WIDTH - 2 * H_PAD;
		PADDED_HEIGHT = HEIGHT - 2 * V_PAD;
		makePossibleBuilders();
	}

	private void makePossibleBuilders() {
		myPossibleShapeBuilders.put("Rectangular", new Rectangle());
		myPossibleShapeBuilders.put("Triangular", new Triangle());
		myPossibleShapeBuilders.put("Hexagonal", new Hexagon());
	}

	/**
	 * Function to do each game frame
	 */
	private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent evt) {
			updateCells();
		}
	};

	private void updateCells() {
		if (initialized) {
			myPatches = myGrid.myRuleSet.update();
			updateGUICells();
		}
	}

	/**
	 * Change the colors of the Polygon representations of Cells to animate
	 * them
	 */
	private void updateGUICells() {
		// For all patches, update their colors
		for (int i=0; i<myPatches.length; i++){
			for (int j=0; j<myPatches[0].length; j++){
				if (myPatches[i][j].getCell() == null) myGUICells[i][j].setFill(Color.WHITE);
				else {
					Color color = myPatches[i][j].getCell().getState()
							.getColor(); // We should make this shorter one day
											// @Mike Zhu
					myGUICells[i][j].setFill(color);
				}
			}		
		}
	}

	/**
	 * Create the game's frame
	 */
	public KeyFrame start() {
		return new KeyFrame(Duration.millis(1000), oneFrame);
	}

	/**
	 * Create the game's scene
	 */
	public Group initGridPane(int gridRows, int gridCols, String[][] gridArray) {
		NUM_ROWS = gridRows;
		NUM_COLS = gridCols;

		myNodes = new Group();

		addCellsToGrid(gridArray);

		return myNodes;
	}

	private Group addCellsToGrid(String[][] gridArray) {
		myShapeBuilder = myPossibleShapeBuilders.get(CASettings.myGridShape);

		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				Polygon patch = myShapeBuilder.makeShape(PADDED_HEIGHT, PADDED_WIDTH, NUM_ROWS, NUM_COLS);
				myShapeBuilder.move(patch, row, col, V_PAD, H_PAD);
				patch.setFill(Color.WHITE);
				
				/*
				 *  @TODO : Add in ability to turn on/off gridlines
				 */
				patch.setStroke(Color.GREY);
				
				myNodes.getChildren().add(patch);
				myGUICells[row][col] = patch;
			}
		}

		return myNodes;
	}
	
//	public GridPane readXMLAndInitializeGrid(File XMLFile) {
//		XMLHandler reader = new XMLHandler();
//		mySettings = reader.read(XMLFile);
//		myGUICells = new Rectangle[mySettings.getRows()][mySettings.getColumns()];
//
//		myGridPane = initGrid(mySettings.getRows(), mySettings.getColumns(), mySettings.getGrid());
//		myGrid = new Grid(mySettings.getType(), mySettings.getParameters(), mySettings.getRows(), mySettings.getColumns(), mySettings.getGrid());
//	}

	public Group readXMLAndInitializeGrid(File XMLFile) {
		XMLHandler reader = new XMLHandler();
		CASettings settings = reader.read(XMLFile);
		
		myGUICells = new Polygon[settings.getRows()][settings.getColumns()];

		myNodes = initGridPane(settings.getRows(), settings.getColumns(),
				settings.getGrid());
		
		myGrid = new Grid(settings.getType(), settings.getParameters(),
				settings.getRows(), settings.getColumns(), settings.getGrid());
		
		initialized = true;
		return myNodes;
	}
	
	public void writeToXML(){
		XMLHandler writer = new XMLHandler();
		writer.write(mySettings);
	}

}