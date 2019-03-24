// custom classes
import model.Question;
import parsing.Parser;

// standard libraries
import java.util.LinkedList;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.File;

// javafx libraries
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

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

public class SpacedLearning extends Application{

	public static int WIDTH  = 1000;
	public static int HEIGHT = 1200;	

	// list of questions
	private LinkedList<Question> questions = new LinkedList<>(); 
	private Question currentQuestion;
	
	// controls
	private Label  headingLabel;
	private Label  rightLabel;
	private Label  leftLabel;
	private Button flipButton;
	private Button correctButton;
	private Button incorrectButton;

	// control variables
	private boolean showingAnswer = false;
	
	@Override 
	public void start(Stage primaryStage) throws Exception 
	{
		//FileInputStream input = new FileInputStream("res/img/test.jpeg");
		//Image img = new Image(input); 
		//ImageView iv = new ImageView(img);
	
		this.headingLabel    = new Label();
		this.rightLabel      = new Label();
		this.leftLabel       = new Label();
		this.headingLabel.setFont(new Font(30));
		this.rightLabel.setFont(new Font(25));
		this.leftLabel.setFont(new Font(25));
		this.headingLabel.setWrapText(true);
		this.rightLabel.setWrapText(true);
		this.leftLabel.setWrapText(true);
		this.headingLabel.setTextAlignment(TextAlignment.JUSTIFY); 
		this.rightLabel.setTextAlignment(TextAlignment.JUSTIFY); 
		this.leftLabel.setTextAlignment(TextAlignment.JUSTIFY); 

		this.flipButton      = new Button("FLIP");
		this.correctButton   = new Button("Correct");
		this.incorrectButton = new Button("Incorrect");
		this.flipButton.setFont(new Font(30));
		this.correctButton.setFont(new Font(30));
		this.incorrectButton.setFont(new Font(30));
		this.correctButton.setDisable(true);
		this.incorrectButton.setDisable(true);
		this.correctButton.setStyle("-fx-base: #70dc9c");
		this.incorrectButton.setStyle("-fx-base: #e72351");

		this.flipButton.setOnAction(this::flipCard);
		this.correctButton.setOnAction(this::correct);
		this.incorrectButton.setOnAction(this::incorrect);
		
		GridPane buttonPane = new GridPane();
		buttonPane.add(flipButton, 0, 0, 1, 1);
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
		this.displayQuestion();
	} 

	private void nextQuestion()
	{
		this.questions.add(currentQuestion);
		this.currentQuestion = questions.pop();
		this.displayQuestion();
		this.showingAnswer = false;
		this.correctButton.setDisable(true);
		this.incorrectButton.setDisable(true);
	}

	// Button Callbacks
	private void correct(ActionEvent e)
	{
		this.currentQuestion.increaseCorrect();
		this.currentQuestion.print();
		this.nextQuestion();
	}

	private void incorrect(ActionEvent e)
	{
		this.currentQuestion.decreaseUnderstanding();
		this.currentQuestion.print();	
		this.nextQuestion();
	}
	
	private void flipCard(ActionEvent e)
	{
		this.correctButton.setDisable(false);
		this.incorrectButton.setDisable(false);
	
		if(this.showingAnswer)
			this.displayQuestion();
		else
			this.displayAnswer();
		
		this.showingAnswer = !this.showingAnswer;
	} 
	
	private void loadQuestions()
	{
		File dir     = new File("cpt_files/");
		File files[] = dir.listFiles();
		Parser p = new Parser();

		for(File f : files)
			this.questions.addAll(p.getQuestions(f));
	}

	private void displayAnswer()
	{
		if(this.currentQuestion.getAnswer().equals(""))
			this.leftLabel.setText("Sorry: No answer available...");
		else
			this.leftLabel.setText(this.currentQuestion.getAnswer());
	}
	
	private void displayQuestion()
	{
		this.headingLabel.setText("\nTOPIC: " + currentQuestion.getTopic() + "\n\n");
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
