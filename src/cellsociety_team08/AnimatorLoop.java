package cellsociety_team08;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class AnimatorLoop {

	private static final String RECTANGULAR = "Rectangular";
	private static final String TRIANGULAR = "Triangular";
	private static final String HEXAGONAL = "Hexagonal";

	public static final int HEIGHT_OFFSET = 112;
	public static final int WIDTH_OFFSET = 40;
	public static final int GRID_BORDER_WIDTH = 1;
	private static final double V_PAD = 0;
	private static final double H_PAD = 0;
	private double PADDED_WIDTH;
	private double PADDED_HEIGHT;
	private int HEIGHT;
	private int WIDTH;
	private int NUM_ROWS;
	private int NUM_COLS;

	private Group myNodes;

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
		PADDED_WIDTH = WIDTH - 2 * V_PAD;
		PADDED_HEIGHT = HEIGHT - 2 * V_PAD;
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
	public Group initGridPane(int gridRows, int gridCols, String[][] gridArray) {
		NUM_ROWS = gridRows;
		NUM_COLS = gridCols;

		myNodes = new Group();

		addCellsToGridPane(gridArray);

		return myNodes;
	}

	private Group addCellsToGridPane(String[][] gridArray) {

		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				Polygon patch = new Polygon();
				patch.setFill(Color.WHITE);
				patch.setStroke(Color.GREY);
				setShapeCoords(row, col, patch);
				myNodes.getChildren().add(patch);
				myGUICells[row][col] = patch;
			}
		}

		return myNodes;
	}

	private void setShapeCoords(int row, int col, Polygon patch) {

		switch (CASettings.myGridShape) {

		case (TRIANGULAR):

			double TRIANGLE_WIDTH = 2*PADDED_WIDTH / NUM_COLS;
			double TRIANGLE_HEIGHT = PADDED_HEIGHT / NUM_ROWS;
			if ((col+row) % 2 == 1) { //Facing down
				col = col/2;
				patch.getPoints().setAll(
						H_PAD + TRIANGLE_WIDTH / 2 + col * TRIANGLE_WIDTH, V_PAD + row*TRIANGLE_HEIGHT, // Left corner
						H_PAD + TRIANGLE_WIDTH / 2 + (col + 1) * TRIANGLE_WIDTH, V_PAD + row*TRIANGLE_HEIGHT, // Right corner
						H_PAD + (col + 1) * TRIANGLE_WIDTH, V_PAD + (row+1)*TRIANGLE_HEIGHT // Bottom corner
				);
			} else { //Facing up
				col = col/2;
				patch.getPoints().setAll(
						H_PAD + TRIANGLE_WIDTH / 2 + col * TRIANGLE_WIDTH, V_PAD + row*TRIANGLE_HEIGHT, // Top corner
						H_PAD + col * TRIANGLE_WIDTH, V_PAD + (row+1)*TRIANGLE_HEIGHT, // Left corner
						H_PAD + (col + 1) * TRIANGLE_WIDTH, V_PAD + (row+1)*TRIANGLE_HEIGHT // Right corner
				);
			}
			break;

		default: // Rectangular
			double rectangleWidth = PADDED_WIDTH / NUM_COLS;
			double rectangleHeight = PADDED_HEIGHT / NUM_ROWS;
			patch.getPoints().setAll(
					H_PAD + col * rectangleWidth,
					V_PAD + row * rectangleHeight, // Top Left corner
					H_PAD + (col + 1) * rectangleWidth,
					V_PAD + row * rectangleHeight, // Top Right corner
					H_PAD + (col + 1) * rectangleWidth,
					V_PAD + (row + 1) * rectangleHeight, // Bottom Left corner
					H_PAD + col * rectangleWidth,
					V_PAD + (row + 1) * rectangleHeight // Bottom Right corner
			);
			break;

		}

	}

	public Group readXMLAndInitializeGrid(File XMLFile) {
		XMLReader reader = new XMLReader();
		CASettings settings = reader.read(XMLFile);
		myGUICells = new Polygon[settings.getRows()][settings.getColumns()];
		// myGUICells = new Polygon[settings.getRows()][settings.getColumns()];

		myNodes = initGridPane(settings.getRows(), settings.getColumns(),
				settings.getGrid());
		myGrid = new Grid(settings.getType(), settings.getParameters(),
				settings.getRows(), settings.getColumns(), settings.getGrid());
		initialized = true;
		return myNodes;
	}

}