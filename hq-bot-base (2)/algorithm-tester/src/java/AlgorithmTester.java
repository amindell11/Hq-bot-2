import java.io.IOException;

import algorithm.QuestionAlgorithm;
import file.QuestionListReader;
import question.Question;
import random_guess.RandomGuess;

public class AlgorithmTester {
	public static final QuestionAlgorithm ALGORITHM = new RandomGuess();
	public static final String TEST_FILE = "question_samples/questions_1.txt";

	public static void main(String[] args) throws IOException {
		// List<Double> processingTimes = new ArrayList<>();
		QuestionListReader reader = new QuestionListReader(TEST_FILE);
		while (reader.hasNext()) {
			Question question = reader.nextQuestion();
			int answerIndex = ALGORITHM.answerQuestion(question);
			System.out.println(question.getAnswers()[answerIndex] + " "
					+ (question.isCorrect(answerIndex) ? "correct" : "incorrect"));
		}
		reader.close();
	}

}
