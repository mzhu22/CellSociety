package cellsociety_team08;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage myStage;
	private CALoop myLoop;
	public static final int HEIGHT = 800;
	public static final int WIDTH = 1200;
	
	@Override
	public void start(Stage mainStage){
		myStage = new Stage();
		myStage.setTitle("Visual Automata");
		
		myLoop = new CALoop();
		
		//initialize Scene
		Scene scene = myLoop.init(myStage, WIDTH, HEIGHT); 
		myStage.setScene(scene);
		myStage.show();
		
		
		//initialize animations
		KeyFrame frame = myLoop.start();
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
