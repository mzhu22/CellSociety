package cellsociety_team08;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class CALoop {
	public static final int MENU_HEIGHT = 105;
	public static final int GRID_BORDER_WIDTH = 1;
	
	/* 
	 * These constants constants are set when the XML file is read, specifying the size of the CA sim board.
	 * They will be used to determine the size of each Cell in the grid.
	 */
	
	//In pixels
	private int myHeight;
	private int myWidth;
	
	//In number of cells
	private int myRows;
	private int myCols;
	
	
	/**
	 *  Function to do each game frame
	 */
    private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent evt) {
			updateSprites();
		}
	};
	
	/**
	 * Create the game's frame
	 */
	public KeyFrame start () {
		return new KeyFrame(Duration.millis(1000/60), oneFrame);
	}
	
	/**
	 * Create the game's scene
	 */
	public GridPane initGrid (int width, int height, int gridRows, int gridCols, String[][] gridArray) {
		myWidth = width;
		myHeight = height-MENU_HEIGHT;
		
		myRows = gridRows;
		myCols = gridCols;
				
		// Create a scene graph to organize the scene
		GridPane grid = new GridPane();
		grid.setVgap(GRID_BORDER_WIDTH);
		grid.setHgap(GRID_BORDER_WIDTH);
		// Make some shapes and set their properties
						
		addCellsToGridPane(grid, gridArray);
		
		return grid;
	}

	private void addCellsToGridPane(GridPane grid, String[][] gridArray) {
		double cellHeight = myWidth/myCols; 
		double cellWidth = myHeight/myRows;
		
		grid.setGridLinesVisible(true);
		for(int i=0; i<myCols; i++){
			for(int j=0; j<myRows; j++){
				if(gridArray[i][j].equals("0")){
					Rectangle cell = new Rectangle(cellHeight, cellWidth, Color.RED);
					grid.add(cell, i, j);
				}
				else{
					Rectangle patch = new Rectangle(cellHeight, cellWidth, Color.WHITE);
					grid.add(patch, i, j);
				}
			}
		}
	}

	/**
	 * Change the sprites properties to animate them
	 * 
	 * Note, there are more sophisticated ways to animate shapes, 
	 * but these simple ways work too.
	 */
	private void updateSprites () {
		
	}
}