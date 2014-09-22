package cellsociety_team08;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AnimatorLoop {
	public static final int HEIGHT_OFFSET = 112;
	public static final int WIDTH_OFFSET = 40;
	public static final int GRID_BORDER_WIDTH = 1;
	private static final double V_PAD = 5;
	private static final double H_PAD = 5;
	private double PADDED_WIDTH;
	private double PADDED_HEIGHT;
	private int HEIGHT;
	private int WIDTH;
	private int NUM_ROWS;
	private int NUM_COLS;

	private GridPane myGridPane;

	private boolean initialized = false;

	private Grid myGrid;
	private Rectangle[][] myGUICells;
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
		PADDED_WIDTH = WIDTH - 2*V_PAD;
		PADDED_HEIGHT = HEIGHT - 2*V_PAD;
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
	 * Change the colors of the Rectangle representations of Cells to animate
	 * them
	 */
	private void updateGUICells() {
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[i].length; j++) {
				if (myPatches[i][j].getCell() == null) {
					myGUICells[i][j].setFill(Color.WHITE);
				} else {
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
	public GridPane initGridPane(int gridRows, int gridCols, String[][] gridArray) {
		NUM_ROWS = gridRows;
		NUM_COLS = gridCols;

		// Create a scene graph to organize the scene
		//myGridPane = new Group();
		myGridPane = new GridPane();
		myGridPane.setVgap(GRID_BORDER_WIDTH);
		myGridPane.setHgap(GRID_BORDER_WIDTH);
		// Make some shapes and set their properties

		addCellsToGridPane(gridArray);

		return myGridPane;
	}

	private GridPane addCellsToGridPane(String[][] gridArray) {
		double cellHeight = HEIGHT / NUM_COLS;
		double cellWidth = WIDTH / NUM_ROWS;
		myGridPane.setGridLinesVisible(true);

		switch (CASettings.myGridShape) {

		case ("Triangular"):
			for (int i = 0; i < NUM_ROWS; i++) {
				for (int j = 0; j < NUM_COLS; j++) {
					//Polygon patch = new Polygon();
					//Code to construct points here;
					Rectangle patch = new Rectangle(cellWidth, cellHeight,
							Color.WHITE);
					myGridPane.add(patch, j, i);
					myGUICells[i][j] = patch;
				}
			}
			break;
		case ("Rectangular"): // different than "rectangle"
			double rectangleWidth = PADDED_WIDTH/NUM_COLS;
			double rectangleHeight = PADDED_HEIGHT/NUM_ROWS;
			
			for (int row = 0; row < NUM_ROWS; row++){
				for (int col = 0; col < NUM_COLS; col++) {
					Polygon patch = new Polygon();
					patch.setFill(Color.BLUE);
					patch.setStroke(Color.GREY);
					patch.getPoints().setAll(
							H_PAD + col*rectangleWidth, V_PAD + row*rectangleHeight, // Top Left corner
							H_PAD + (col+1)*rectangleWidth, V_PAD + row*rectangleHeight, // Top Right corner
							H_PAD + (col+1) * rectangleWidth, V_PAD + (row+1)*rectangleHeight, // Bottom Left corner
							H_PAD + col * rectangleWidth, V_PAD + (row+1)*rectangleHeight); // Bottom Right corner
					//myGridPane.getChildren.add(patch);
					//myGUICells[row][col] = patch;
				}
			}
			
		default: //Rectangular
			for (int i = 0; i < NUM_ROWS; i++) {
				for (int j = 0; j < NUM_COLS; j++) {
					Rectangle patch = new Rectangle(cellWidth, cellHeight,
							Color.WHITE);
					myGridPane.add(patch, j, i);
					myGUICells[i][j] = patch;
				}
			}
			break;
		}

		return myGridPane;
	}

	public GridPane readXMLAndInitializeGrid(File XMLFile) {
		XMLReader reader = new XMLReader();
		CASettings settings = reader.read(XMLFile);
		myGUICells = new Rectangle[settings.getRows()][settings.getColumns()];
		//myGUICells = new Polygon[settings.getRows()][settings.getColumns()];

		myGridPane = initGridPane(settings.getRows(), settings.getColumns(),
				settings.getGrid());
		myGrid = new Grid(settings.getType(), settings.getParameters(),
				settings.getRows(), settings.getColumns(), settings.getGrid());
		initialized = true;
		return myGridPane;
	}

}