package frontEnd;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import shapeFactories.Hexagon;
import shapeFactories.Rectangle;
import shapeFactories.ShapeFactory;
import shapeFactories.Triangle;
import backEnd.GameOfLife;
import backEnd.Patch;
import backEnd.PredatorPrey;
import backEnd.RuleSet;
import backEnd.Segregation;
import backEnd.SpreadingFire;
import backEnd.SugarScape;

public class AnimatorLoop {

	// Used for menu bars
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
	private ShapeFactory myShapeBuilder;
	private static Map<String, ShapeFactory> myImplementedShapeFactories;
	private static Map<String, RuleSet> myImplementedRulesets;
	public RuleSet myRuleSet;

	private boolean initialized = false;

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
		makePossibleShapeFactories();
		makePossibleRules();
	}

	/**
	 * Create the game's frame
	 */
	public KeyFrame start() {
		return new KeyFrame(Duration.millis(1000), oneFrame);
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
			myPatches = myRuleSet.update();
			updateGUICells();
		}
	}

	/**
	 * Change the colors of the Polygon representations of Cells to animate them
	 */
	private void updateGUICells() {

		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				/*
				 * TODO Don't have this intentionally null. Make "Empty" state
				 * instead @Brian Bolze
				 */
				if (myPatches[i][j].getCell() == null)
					myGUICells[i][j].setFill(Color.WHITE);
				else {
					Color color = myPatches[i][j].getCell().getColor();
					myGUICells[i][j].setFill(color);
				}
			}
		}

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
		myShapeBuilder = myImplementedShapeFactories
				.get(mySettings.myGridShape);

		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				Polygon patch = myShapeBuilder.makeShape(PADDED_HEIGHT,
						PADDED_WIDTH, NUM_ROWS, NUM_COLS);
				myShapeBuilder.move(patch, row, col, V_PAD, H_PAD);
				patch.setFill(Color.WHITE);

				/*
				 * TODO : Add in ability to turn on/off gridlines
				 */
				patch.setStroke(Color.GREY);

				myNodes.getChildren().add(patch);
				myGUICells[row][col] = patch;
			}
		}

		return myNodes;
	}

	public Group readXMLAndInitializeGrid(File XMLFile) {
		XMLHandler reader = new XMLHandler();
		mySettings = reader.read(XMLFile);

		myGUICells = new Polygon[mySettings.getRows()][mySettings.getColumns()];

		myNodes = initGridPane(mySettings.getRows(), mySettings.getColumns(),
				mySettings.getGrid());

		myPatches = new Patch[NUM_ROWS][NUM_COLS];
		myRuleSet = myImplementedRulesets.get(mySettings.getType());
		myRuleSet.setParams(mySettings.getParameters());
		
		initGrid();
		myRuleSet.addGrid(myPatches);

		initialized = true;
		return myNodes;
	}
	
	private void initGrid() {
		String[][] grid = mySettings.getGrid();
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				myPatches[i][j] = myRuleSet.initializePatch(i, j, grid[i][j]);
				//myPatches[i][j] = myRuleSet.initializeRandom(i, j);
			}
		}
	}

	public void writeToXML() {
		XMLHandler writer = new XMLHandler();
		writer.write(mySettings);
	}

	private void makePossibleShapeFactories() {
		myImplementedShapeFactories = new HashMap<String, ShapeFactory>();

		myImplementedShapeFactories.put("Rectangular", new Rectangle());
		myImplementedShapeFactories.put("Triangular", new Triangle());
		myImplementedShapeFactories.put("Hexagonal", new Hexagon());
	}
	
	private void makePossibleRules() {
		myImplementedRulesets = new HashMap<>();

		myImplementedRulesets.put("PredatorPrey", new PredatorPrey());
		myImplementedRulesets.put("SpreadingFire", new SpreadingFire());
		myImplementedRulesets.put("GameOfLife", new GameOfLife());
		myImplementedRulesets.put("Segregation", new Segregation());
		myImplementedRulesets.put("SugarScape", new SugarScape());

	}

}