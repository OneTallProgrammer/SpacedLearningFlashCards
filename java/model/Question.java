package model;

import java.time.LocalDate;
import java.lang.StringBuilder;

public class Question {
	private String topic;
	private String question;
	private String answer;
	private int understanding;
	private int correct;
	LocalDate datePracticed;
	LocalDate nextPractice;

	public Question(String topic, String question, String answer, int understanding, 
					int correct, String datePracticed, String nextPractice)
	{
		this.topic         = topic;
		this.question      = question;
		this.answer        = answer;
		this.understanding = understanding;		
		this.correct       = correct;
		
		// parse LocalTime objects
		if(datePracticed.equals(""))
			this.datePracticed = null;	
		else
			this.datePracticed = LocalDate.parse(datePracticed);
		
		if(nextPractice.equals(""))
			this.nextPractice = LocalDate.now();
		else
		{ 
			this.nextPractice = LocalDate.parse(nextPractice);
			
			if(this.overdue())
				this.decreaseUnderstanding();
		}
	}
	
	// Getters
	public String getTopic()
	{
		return this.topic;
	}

	public String getQuestion()
	{
		return this.question;
	}

	public String getAnswer()
	{
		return this.answer;
	}
	
	public int getUnderstanding()
	{
		return this.understanding;
	}
	
	public int getCorrect()
	{
		return this.correct;
	}

	public LocalDate getDatePracticed()
	{
		return this.datePracticed;
	}

	public LocalDate getNextPractice()
	{
		return this.nextPractice;
	}

	// interface

	public void increaseCorrect()
	{
		this.correct++;
		if(this.correct == 3)
		{
			this.increaseUnderstanding();
		}

		this.datePracticed = LocalDate.now();
		this.resetNextPractice();
	}

	public void decreaseUnderstanding()
	{
		this.understanding = 0;	
		this.correct = 0;
		this.resetNextPractice();
	}

	public String getCsvLine()
	{
		StringBuilder bldr = new StringBuilder();

		bldr.append("\"");
		bldr.append(this.question);
		bldr.append("\",");

		bldr.append("\"");		
		bldr.append(this.answer);
		bldr.append("\",");		

		bldr.append(this.understanding);
		bldr.append(",");
		
		bldr.append(this.correct);
		bldr.append(",");

		bldr.append(this.datePracticed);
		bldr.append(",");
		
		bldr.append(this.nextPractice);
		
		return bldr.toString();
	}
	
	// return true if today >= nextPractice 
	// return false otherwise
	public boolean include()
	{
		return !LocalDate.now().isBefore(this.nextPractice);
	}

	public void print()
	{
		System.out.println(
			String.format("Question: %s\nAnswer: %s\nUnderstanding: %d\nCorrect: %d\nLast Practiced: %s\nNext Practice: %s\n",
			this.question,
			this.answer,
			this.understanding,
			this.correct,
			this.datePracticed,
			this.nextPractice)
		);
	}
	
	// implementation
	
	// Return true if the day hasn't been answered in a week
	private boolean overdue()
	{
		int week = 7; // days
		return LocalDate.now().isAfter(this.nextPractice.plusDays(week));
	}

	// set the deadline for the question
	private void resetNextPractice()
	{
		int offset = this.getSpacedInterval();
		this.nextPractice = LocalDate.now().plusDays(offset);
	}	

	// increase the understanding level
	// reset the correct count
	private void increaseUnderstanding()
	{
		this.understanding++;
		this.correct = 0;
	}

	// return the number of days that can pass 
	// before problem must be practiced
	private int getSpacedInterval()
	{
		switch(this.understanding)
		{
		case 0:
			return 1;
		case 1:
			return 1;
		case 2:
			return 3;
		case 3:
			return 3;
		case 4:
			return 7;
		case 5:
			return 7;
		case 6:
			return 30;
		case 7:
			return 30;
		case 8:
			return 60;
		case 9:
			return 60;
		case 10:
			return 365;
		default:
			return -1;
		}
	}
}
