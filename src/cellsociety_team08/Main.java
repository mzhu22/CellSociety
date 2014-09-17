package cellsociety_team08;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	//GUI size constants
	public static final int HEIGHT = 800;
	public static final int WIDTH = 1000;
	
	public static final int CONTROL_BOX_SPACING = 5;
	public static final int SLIDER_WIDTH = 400;
	public static final int SLIDER_MAX_VALUE = 60;
	public static final int TEXTFIELD_WIDTH = 100;
	
	//All GUI parents
	private Stage myStage;
	private CALoop myLoop;
	private CASettings mySettings;
	private VBox myRoot;
	private HBox myControls;
	private MenuBar myMenuBar;
	private GridPane myGrid;
	
	//All controlBox elements. Instance variables so they can be easily accessed/passed to adjust behavior
	private Button myPauseButton;
	private Slider mySpeedSlider;
	private TextField myJumpToField;
	
	//All XML file reading components
	private FileChooser fileChooser = new FileChooser();
	private File XMLFile;
	
	@Override
	public void start(Stage mainStage){
		myStage = new Stage();
		myStage.setTitle("Visual Automata");
		
		myLoop = new CALoop();
				
		//Create a VBox to place the menu, then the grid in a vertical column. 
		//Create the GridPane used to hold all rectangular cells
		myRoot = new VBox();
		
		//Initialize Scene
		Scene scene = new Scene(myRoot, WIDTH, HEIGHT, Color.WHITE);

		//Initialize the menu
		myMenuBar = makeMenu();
		
		//Initialize the simulation controls (play/pause, speed slider, jumpTo, etc.)
		myControls = new HBox();
		myControls.setSpacing(CONTROL_BOX_SPACING);
		myPauseButton = new Button("Play/Pause");
		
		//Create slider with values from 1 to 20, default == 1, 
		mySpeedSlider = new Slider(0, SLIDER_MAX_VALUE, 10);
		mySpeedSlider.setShowTickMarks(true);
		mySpeedSlider.setShowTickLabels(true);
		mySpeedSlider.setSnapToTicks(true);
		mySpeedSlider.setMajorTickUnit(5f);
		mySpeedSlider.setBlockIncrement(5f);
		mySpeedSlider.setPrefWidth(SLIDER_WIDTH);
		Label sliderLabel = new Label(" Sim speed (rounds/second)");
		
		//Create textfield for jumping to a point
		myJumpToField = new TextField();
		myJumpToField.setPrefWidth(TEXTFIELD_WIDTH);
		Label textfieldLabel = new Label("Jump to round: ");
				
		myControls.getChildren().add(myPauseButton);
		myControls.getChildren().add(sliderLabel);
		myControls.getChildren().add(mySpeedSlider);
		myControls.getChildren().add(textfieldLabel);
		myControls.getChildren().add(myJumpToField);
		
		//Make a blank grid. Grid is created for real when XML files loaded
		myGrid = new GridPane();
						
		myRoot.getChildren().add(myMenuBar);
		myRoot.getChildren().add(myControls);
		myRoot.getChildren().add(myGrid);
		
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
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuSimulation = new Menu("Simulation");
		Menu menuHelp = new Menu("Help");
		
		//This allows you to load XML files. A class XMLReader handles this task, returns a CASettings object containing 
		MenuItem open = new MenuItem("Open");
		open.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent press) {
				XMLFile = fileChooser.showOpenDialog(myStage);
				XMLReader test = new XMLReader();
				mySettings = test.read(XMLFile);
				myGrid = myLoop.initGrid(WIDTH, HEIGHT, mySettings.getRows(), mySettings.getColumns(), mySettings.getGrid());
				
				//Always remove the last element of myRoot's children (the grid). Then readd it. This means new grids are made when new XML files are loaded
				myRoot.getChildren().remove(myRoot.getChildren().size()-1);
				myRoot.getChildren().add(myGrid);
			}
			
		});
				
		MenuItem exit = new MenuItem("Exit");
		menuFile.getItems().addAll(open, exit);
		
		MenuItem playPause = new MenuItem("Play/Pause");
		menuSimulation.getItems().addAll(playPause);
		
		MenuItem help = new MenuItem("Help");
		menuHelp.getItems().addAll(help);
	
		menuBar.getMenus().addAll(menuFile, menuSimulation, menuHelp);
		return menuBar;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
