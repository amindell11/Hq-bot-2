import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import algorithm.QuestionAlgorithm;
import file.QuestionListReader;
import question.Question;
import random_guess.RandomGuess;

public class AlgorithmTester {
	public static final QuestionAlgorithm ALGORITHM = new RandomGuess();
	public static final String TEST_FILE = "question_samples/questions_1.txt";

	public static void main(String[] args) throws IOException {
		List<Double> processingTimes = new ArrayList<>();
		List<Boolean> correct = new ArrayList<>();
		QuestionListReader reader = new QuestionListReader(TEST_FILE);
		while (reader.hasNext()) {
			long time = System.currentTimeMillis();
			Question question = reader.nextQuestion();
			int answerIndex = ALGORITHM.answerQuestion(question);
			processingTimes.add((System.currentTimeMillis() - time) / 1000d);
			boolean thisCorrect = question.isCorrect(answerIndex);
			correct.add(thisCorrect);
			System.out.println(question.getAnswers()[answerIndex] + " " + (thisCorrect ? "correct" : "incorrect"));
		}
		long count = correct.stream().filter(p -> p).count();
		System.out
				.println("Percent Correct: " + new DecimalFormat("##.00").format(100d * count / correct.size()) + "%");
		System.out.println("Average time: " + new DecimalFormat("##.00")
				.format(processingTimes.stream().collect(Collectors.averagingDouble(d -> d))));

		reader.close();
	}
}
