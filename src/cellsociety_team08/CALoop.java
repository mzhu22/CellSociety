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
	
	/* 
	 * These constants constants are set when the XML file is read, specifying the size of the CA sim board.
	 * They will be used to determine the size of each Cell in the grid.
	 */
	
	//In pixels
	private int myWindowHeight;
	private int myWindowWidth;
	
	//In number of cells
	private int myGridRows;
	private int myGridColumns;
	
	
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
		myWindowWidth = width;
		myWindowHeight = height-65;
		
		myGridRows = gridRows;
		myGridColumns = gridCols;
		
		System.out.println("width: " + myWindowWidth + " height: " + myWindowHeight);
		System.out.println("rows: " + myGridRows + " colums: " + myGridColumns);
		
		// Create a scene graph to organize the scene
		GridPane grid = new GridPane();
		grid.setVgap(1);
		grid.setHgap(1);
		// Make some shapes and set their properties
						
		addCellsToGridPane(grid, gridArray);
		
		return grid;
	}

	private void addCellsToGridPane(GridPane grid, String[][] gridArray) {
		double cellHeight = myWindowWidth/myGridColumns; 
		double cellWidth = myWindowHeight/myGridRows;
		System.out.println(cellHeight + " : " + cellWidth);
		
		grid.setGridLinesVisible(true);
		for(int i=0; i<myGridColumns; i++){
			for(int j=0; j<myGridRows; j++){
				if(gridArray[i][j].equals("0")){
					Rectangle cell = new Rectangle(cellHeight, cellWidth, Color.RED);
					grid.add(cell, i, j);
				}
			}
		}
	}

	private MenuBar makeMenu() {
		MenuBar menu = 	new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuSimulation = new Menu("Simulation");
		Menu menuHelp = new Menu("Help");
		
		MenuItem open = new MenuItem("Open");
		MenuItem exit = new MenuItem("Exit");
		menuFile.getItems().addAll(open, exit);
		
		MenuItem playPause = new MenuItem("Play/Pause");
		menuSimulation.getItems().addAll(playPause);
		
		MenuItem help = new MenuItem("Help");
		menuHelp.getItems().addAll(help);
	
		menu.getMenus().addAll(menuFile, menuSimulation, menuHelp);
		return menu;
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