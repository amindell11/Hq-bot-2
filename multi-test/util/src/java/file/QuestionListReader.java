package file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import question.Question;

public class QuestionListReader extends BufferedReader {
	String currentLine;

	public QuestionListReader(String fileName) throws FileNotFoundException {
		super(new FileReader(fileName));
		try {
			currentLine = readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Question nextQuestion() {
		String quest = currentLine;
		try {
			currentLine = readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Question.parseQuestion(quest);
	}

	public boolean hasNext() {
		return currentLine != null;
	}

}
