package random_guess;

import java.util.Random;

import algorithm.QuestionAlgorithm;
import question.Question;

public class RandomGuess implements QuestionAlgorithm {

	@Override
	public int answerQuestion(Question question) {
		return new Random().nextInt(question.getAnswers().length);
	}

}
