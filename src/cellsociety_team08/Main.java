package cellsociety_team08;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	public static final int HEIGHT = 800;
	public static final int WIDTH = 1200;
	
	private Stage myStage;
	private CALoop myLoop;
	
	private FileChooser fileChooser = new FileChooser();
	private File XMLFile;
	
	@Override
	public void start(Stage mainStage){
		myStage = new Stage();
		myStage.setTitle("Visual Automata");
		
		myLoop = new CALoop();
				
		//Create a VBox to place the menu, then the grid in a vertical column. 
		//Create the GridPane used to hold all rectangular cells
		VBox root = new VBox();
		GridPane grid = new GridPane();
		grid.setVgap(1);
		grid.setHgap(1);
		
		//Initialize Scene
		Scene scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);

		//Initialize the menu
		MenuBar menu = makeMenu();
						
		root.getChildren().add(grid);
		root.getChildren().add(menu);
		
		myStage.setScene(scene);
		myStage.show();
		
		//Initialize animations
		KeyFrame frame = myLoop.start();
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	/**
	 * Creates a drop-down menu at the top of the window
	 * @return
	 */
	private MenuBar makeMenu() {
		MenuBar menu = 	new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuSimulation = new Menu("Simulation");
		Menu menuHelp = new Menu("Help");
		
		MenuItem open = new MenuItem("Open");
		open.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent press) {
				XMLFile = fileChooser.showOpenDialog(myStage);
				XMLReader test = new XMLReader();
				test.read(XMLFile);
			}
			
		});
		
		MenuItem exit = new MenuItem("Exit");
		menuFile.getItems().addAll(open, exit);
		
		MenuItem playPause = new MenuItem("Play/Pause");
		menuSimulation.getItems().addAll(playPause);
		
		MenuItem help = new MenuItem("Help");
		menuHelp.getItems().addAll(help);
	
		menu.getMenus().addAll(menuFile, menuSimulation, menuHelp);
		return menu;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
