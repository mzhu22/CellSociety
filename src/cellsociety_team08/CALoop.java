package cellsociety_team08;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;


public class CALoop {
	public static final int MENU_HEIGHT = 105;
	public static final int GRID_BORDER_WIDTH = 1;
	
	private Grid myGrid;
	private GridPane myGridPane;
	private List<Rectangle> myCells = new ArrayList<>();
		
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
	 * Constructor
	 * @param width = width of stage in pixels
	 * @param height = height of stage MINUS height of the menu on the top
	 */
	public CALoop(int width, int height){
		myWidth = width;
		myHeight = height-MENU_HEIGHT;
	}
	
	/**
	 *  Function to do each game frame
	 */
    private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent evt) {
			updateGUICells();
		}
	};
	

	/**
	 * Change the sprites properties to animate them
	 * 
	 * Note, there are more sophisticated ways to animate shapes, 
	 * but these simple ways work too.
	 */
	//TODO: GET RID OF SOME OF THIS TESTING SHIT 
	private void updateGUICells () {
		Random r = new Random();
		for(Shape s: myCells){
			if(r.nextInt(100)==1)
			s.setFill(Color.BLACK);
		}
	}
	
	/**
	 * Create the game's frame
	 */
	public KeyFrame start () {
		return new KeyFrame(Duration.millis(1000), oneFrame);
	}
	
	/**
	 * Create the game's scene
	 */
	public GridPane initGrid (int gridRows, int gridCols, String[][] gridArray) {
		myRows = gridRows;
		myCols = gridCols;
				
		// Create a scene graph to organize the scene
		myGridPane = new GridPane();
		myGridPane.setVgap(GRID_BORDER_WIDTH);
		myGridPane.setHgap(GRID_BORDER_WIDTH);
		// Make some shapes and set their properties
						
		addCellsToGridPane(gridArray);
		
		return myGridPane;
	}

	private GridPane addCellsToGridPane(String[][] gridArray) {
		double cellHeight = myHeight/myCols; 
		double cellWidth = myWidth/myRows;
		
		myCells = new ArrayList<>();
		
		myGridPane.setGridLinesVisible(true);
		for(int i=0; i<myRows; i++){
			for(int j=0; j<myCols; j++){
				if(gridArray[i][j].equals("0")){
					Rectangle cell = new Rectangle(cellWidth, cellHeight, Color.RED);
					myGridPane.add(cell, j, i);
					myCells.add(cell);
				}
				else{
					Rectangle patch = new Rectangle(cellWidth, cellHeight, Color.WHITE);
					myGridPane.add(patch, j, i);
					myCells.add(patch);
				}
			}
		}
		return myGridPane;
	}
	
	public GridPane readXML(File XMLFile) {
		XMLReader reader = new XMLReader();
		CASettings settings = reader.read(XMLFile);
		myGridPane = initGrid(settings.getRows(), settings.getColumns(), settings.getGrid());
		
		myGrid = new Grid(settings.getType(), settings.getRows(), settings.getColumns(), myHeight, myWidth);
		return myGridPane;
	}

}