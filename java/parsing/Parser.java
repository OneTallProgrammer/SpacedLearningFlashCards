package parsing;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader; 
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;
import java.lang.StringBuilder;
import java.util.ArrayList;

import model.Question;

public class Parser {

	// interface
	public LinkedList<Question> getQuestions(File f)
	{
		LinkedList<Question> questions = new LinkedList<>();
		
		ArrayList<String> lines = this.getLines(f);

		String topic = lines.get(0);

		for(int i = 1; i < lines.size(); i++)
		{	
			questions.add(getQuestion(topic, lines.get(i)));
		} 

		return questions;
	}

	// implementation
	private Question getQuestion(String topic, String line)
	{
		String question       = ""; 
		String answer         = "";
		int    understanding  = 0;
		int    correct        = 0;
		String datePracticed  = "";
		String nextPractice   = "";
		
		String split[] = line.split(",", -1);

		nextPractice   = split[split.length - 1];
		datePracticed  = split[split.length - 2];
		
		if(!split[split.length - 3].equals(""))
			correct = Integer.parseInt(
				split[split.length - 3]
			);

		if(!split[split.length - 4].equals(""))
			understanding = Integer.parseInt(
				split[split.length - 4]
			);
		
		// extract answer
		// if quotes are present, extract string inbetween quotes
		int i = 0;
		if(split[i].length() > 0 && split[i].charAt(0) == '"')
		{
			StringBuilder bldr = new StringBuilder();			

			split[i] = split[i].substring(1);			

			while(split[i].charAt(split[i].length() - 1) != '"')
			{
				bldr.append(split[i]);
				bldr.append(",");
				
				i++;
			}

			bldr.append(split[i].substring(0, split[i].length() - 1));
			
			question = bldr.toString();
		}
		else
		{	
			question = split[i];
		}
		
		// extract answer
		// if quotes are present, extract string inbetween quotes
		i++;
		if(split[i].length() > 0 && split[i].charAt(0) == '"')
		{
			StringBuilder bldr = new StringBuilder();			

			split[i] = split[i].substring(1);			

			while(split[i].charAt(split[i].length() - 1) != '"')
			{
				bldr.append(split[i]);
				bldr.append(",");
				
				i++;
			}

			bldr.append(split[i].substring(0, split[i].length() - 1));
			
			answer = bldr.toString();
		}
		else 
		{
			answer = split[i];
		}
		
		return new Question(topic, question, answer, understanding, correct, datePracticed, nextPractice);
	}

	// implementation	
	private ArrayList<String> getLines(File f)
	{
		ArrayList<String> lines = new ArrayList<>();		
		
		BufferedReader r;
		try
		{
			r = new BufferedReader(
					new FileReader(f)
					);

			String line;	
			while((line = r.readLine()) != null)
			{
				lines.add(line);
			}
			
		}
		catch(IOException e)
		{
			System.out.println(
					String.format("Error reading %s", f.getPath())
				);
		}

		return lines;
	}

};
