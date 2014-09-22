package cellsociety_team08;

import java.io.File;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	//GUI size constants
	public static final int HEIGHT = 700;
	public static final int WIDTH = 950;
	
	public static final int SLIDER_MAX_VALUE = 100;
	public static final int SLIDER_WIDTH = 350;
	public static final int SLIDER_DEFAULT_VALUE = 10;
	public static final int TEXTFIELD_WIDTH = 100;
	public static final int CONTROL_BOX_SPACING = 5;
	
	//All GUI parents
	private Stage myStage;
	private AnimatorLoop myLoop;
	private VBox myRoot;
	private HBox myControls;
	private MenuBar myMenuBar;
	private Group myGrid;
	
	private Button myPauseButton;
	private Slider mySlider;
	private TextField myField;
	
	private Timeline myAnimation;
	
	//All XML file reading components
	private FileChooser fileChooser = new FileChooser();
	
	@Override
	public void start(Stage mainStage){
		myStage = new Stage();
		myStage.setTitle("Visual Automata");
		
		myLoop = new AnimatorLoop(WIDTH, HEIGHT);
				
		//Create a VBox to place the menu, then the grid in a vertical column. 
		//Create the GridPane used to hold all rectangular cells
		myRoot = new VBox();
		
		//Initialize Scene
		Scene scene = new Scene(myRoot, WIDTH, HEIGHT, Color.WHITE);

		//Initialize the menu and control box
		makeMenu();
		makeControlBox();
		
		//Make a blank grid. Grid is created for real when XML files loaded
		myGrid = new Group();
						
		myRoot.getChildren().add(myMenuBar);
		myRoot.getChildren().add(myControls);
		myRoot.getChildren().add(myGrid);
		
		myStage.setScene(scene);
		myStage.show();
		
		//Initialize animations
		KeyFrame frame = myLoop.start();
		myAnimation = new Timeline();
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().add(frame);
		myAnimation.play();
		
		useControls();
	}

	
	/**
	 * Creates a drop-down menu at the top of the window
	 * @return
	 */
	private void makeMenu() {
		myMenuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuHelp = new Menu("Help");
		
		//This allows you to load XML files. A class XMLReader handles this task, returns a CASettings object containing 
		MenuItem open = new MenuItem("Open");
		open.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent press) {
				readXML();
			}
		});
				
		MenuItem exit = new MenuItem("Exit");
		menuFile.getItems().addAll(open, exit);
				
		MenuItem help = new MenuItem("Help");
		menuHelp.getItems().addAll(help);
	
		myMenuBar.getMenus().addAll(menuFile, menuHelp);
	}
	
	public void makeControlBox() {
		//Initialize the simulation controls (play/pause, speed slider, jumpTo, etc.)
		myControls = new HBox();
		myControls.setSpacing(CONTROL_BOX_SPACING);
		myPauseButton = new Button("Play/Pause");
		
		//Create slider with values from 1 to 20, default == 1, 
		mySlider = new Slider(0, SLIDER_MAX_VALUE, SLIDER_DEFAULT_VALUE);
		mySlider.setShowTickMarks(true);
		mySlider.setShowTickLabels(true);
		mySlider.setSnapToTicks(true);
		mySlider.setMajorTickUnit(10f);
		mySlider.setBlockIncrement(10f);
		mySlider.setPrefWidth(SLIDER_WIDTH);
		Label sliderLabel = new Label(" Sim speed (ticks/second)");
		
		//Create textfield for jumping to a point
		myField = new TextField();
		myField.setPrefWidth(TEXTFIELD_WIDTH);
		Label textfieldLabel = new Label("Jump to tick: ");
				
		myControls.getChildren().add(myPauseButton);
		myControls.getChildren().add(sliderLabel);
		myControls.getChildren().add(mySlider);
		myControls.getChildren().add(textfieldLabel);
		myControls.getChildren().add(myField);
	}
	
	/**
	 * Handles controlBox controls (button, slider, textField)
	 * Pressing button plays/pauses the simulation
	 * Dragging the slider increases/decreases animation speed
	 * Entering a number into the TextField currently does nothing
	 */
	public void useControls() {
		myPauseButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				playPauseSim();
			}
		});
		mySlider.valueProperty().addListener(new ChangeListener<Number>() {			 
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
				myAnimation.setRate((double)newVal);
			}
	      });
	}
	
	/**
	 * Pause simulation when the Play/Pause key is hit
	 */
	public void playPauseSim(){
		if(myAnimation.getStatus().equals(Animation.Status.RUNNING)){
			myAnimation.pause();
		}
		else{
			myAnimation.play();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	private void readXML() {
		File XMLFile = fileChooser.showOpenDialog(myStage);
		myGrid = myLoop.readXMLAndInitializeGrid(XMLFile);
		//Always remove the last element of myRoot's children (the grid). Then readd it. This means new grids are made when new XML files are loaded
		myRoot.getChildren().remove(myRoot.getChildren().size()-1);
		myRoot.getChildren().add(myGrid);
	}
}
