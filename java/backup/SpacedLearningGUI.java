// custom classes
import model.Question;

// standard libraries
import java.util.LinkedList;
import java.util.Collections;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.FileInputStream;


public class SpacedLearning extends Application{

	public static int WIDTH  = 600;
	public static int HEIGHT = 600;	

	// list of questions
	private LinkedList<Question> questions = new LinkedList<>(); 
	private Question currentQuestion;
	
	// controls
	private Label  headingLabel;
	private Label  rightLabel;
	private Label  leftLabel;
	private Button nextButton;
	private Button correctButton;
	private Button incorrectButton;
	
	@Override 
	public void start(Stage primaryStage) throws Exception 
	{
		//FileInputStream input = new FileInputStream("res/img/test.jpeg");
		//Image img = new Image(input); 
		//ImageView iv = new ImageView(img);
	
		headingLabel    = new Label("Blah");
		rightLabel      = new Label();
		leftLabel       = new Label();
		headingLabel.setWrapText(true);
		rightLabel.setWrapText(true);
		leftLabel.setWrapText(true);
		headingLabel.setTextAlignment(TextAlignment.JUSTIFY); 
		rightLabel.setTextAlignment(TextAlignment.JUSTIFY); 
		leftLabel.setTextAlignment(TextAlignment.JUSTIFY); 

		nextButton      = new Button("Show");
		correctButton   = new Button("Correct");
		incorrectButton = new Button("Incorrect");
		correctButton.setStyle("-fx-base: #70dc9c");
		incorrectButton.setStyle("-fx-base: #e72351");
		
		GridPane buttonPane = new GridPane();
		buttonPane.add(nextButton, 0, 0, 1, 1);
		buttonPane.add(correctButton, 1, 0, 1, 1);
		buttonPane.add(incorrectButton, 2, 0, 1, 1);

		// create the stage
		BorderPane root  = new BorderPane();

		// get scene graph node list
		//ObservableList<Node> list = root.getChildren();
		root.setTop(this.headingLabel);
		root.setRight(this.rightLabel);
		root.setLeft(this.leftLabel);
		root.setBottom(buttonPane);
		
		// create the scene
		Scene scene = new Scene(root, this.WIDTH, this.HEIGHT);
		scene.setFill(Color.web("#bffde0"));
		
		// set the scene
		primaryStage.setTitle("Spaced Learning");
		primaryStage.setScene(scene);

		// render window
		primaryStage.show();

		this.loadQuestions();
		this.shuffleQuestions();

		this.currentQuestion = this.questions.pop();
		displayQuestion();
	} 
	
	private void loadQuestions()
	{
		this.questions.add(
			new Question("History",
						 "What is History",
						 "In the past",
						 0,
						 2,
						 "",
						 ""			
			);
		this.questions.add(
			new Question("Chemistry",
						 "What is Chemistry",
						 "Chemials",
						 1,
						 0,
						 "",
						 ""			
			);
	}
	
	private void diplayQuestion()
	{
		this.headingLabel.setText(currentQuestion.getTopic());
		this.leftLabel.setText(currentQuestion.getQuestion());
	}

	private void shuffleQuestions()
	{
		Collections.shuffle(this.questions);
	}	

	public static void main(String args[])
	{
		launch(args);
	}
}
