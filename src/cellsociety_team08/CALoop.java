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
	public Scene init (Stage s, int width, int height) {
		// Create a scene graph to organize the scene
		VBox root = new VBox();
		GridPane grid = new GridPane();
		// Create a place to see the shapes
		Scene scene = new Scene(root, width, height, Color.WHITE);
		// Make some shapes and set their properties
		
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
		grid.setVgap(10);
		grid.setHgap(10);
		
		grid.setGridLinesVisible(true);
		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++){
				Rectangle cell = new Rectangle(80, 80, Color.RED);
				grid.add(cell, i, j);
			}
		}
		
		root.getChildren().add(menu);
		root.getChildren().add(grid);
		return scene;
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