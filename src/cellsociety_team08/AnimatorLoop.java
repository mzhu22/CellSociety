package cellsociety_team08;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;


public class AnimatorLoop {
	public static final int HEIGHT_OFFSET = 112;
	public static final int WIDTH_OFFSET = 40;
	public static final int GRID_BORDER_WIDTH = 1;
	
	private GridPane myGridPane;

	private Grid myGrid;
	private Rectangle[][] myGUICells;
	private Patch[][] myPatches;
	
		
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
	public AnimatorLoop(int width, int height){
		myWidth = width-WIDTH_OFFSET;
		myHeight = height-HEIGHT_OFFSET;
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
	
	private void updateCells(){
		
	}

	/**
	 * Change the colors of the Rectangle representations of Cells to animate them
	 */
	//TODO: GET RID OF SOME OF THIS TESTING SHIT 
	private void updateGUICells () {
//		myGrid.update();
		Random r = new Random();
//		for(Shape s: myGUICells){
//			if(r.nextInt(100)==1)
//			s.setFill(Color.BLACK);
//		}
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
				
		myGridPane.setGridLinesVisible(true);
		for(int i=0; i<myRows; i++){
			for(int j=0; j<myCols; j++){
				if(gridArray[i][j].equals("0")){
					Rectangle cell = new Rectangle(cellWidth, cellHeight, Color.RED);
					myGridPane.add(cell, j, i);
					myGUICells[j][i] = cell;
				}
				else{
					Rectangle patch = new Rectangle(cellWidth, cellHeight, Color.WHITE);
					myGridPane.add(patch, j, i);
					myGUICells[i][j] = patch;
				}
			}
		}
		return myGridPane;
	}
	
	public GridPane readXMLAndInitializeGrid(File XMLFile) {
		XMLReader reader = new XMLReader();
		CASettings settings = reader.read(XMLFile);
		myGUICells = new Rectangle[settings.getRows()][settings.getColumns()];

		myGridPane = initGrid(settings.getRows(), settings.getColumns(), settings.getGrid());
		myGrid = new Grid(settings.getType(), settings.getParameters(), settings.getRows(), settings.getColumns());
		return myGridPane;
	}

}