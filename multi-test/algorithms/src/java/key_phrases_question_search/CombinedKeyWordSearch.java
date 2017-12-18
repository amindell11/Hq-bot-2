package key_phrases_question_search;

import java.util.Arrays;

import com.google.gson.Gson;

import algorithm.QuestionAlgorithm;
import algorithm.Util;
import edu.stanford.nlp.simple.Sentence;
import question.Question;

public class CombinedKeyWordSearch implements QuestionAlgorithm {

	@Override
	public int answerQuestion(Question question) {
		int[] scores = getScores(question);
		Arrays.stream(scores).forEach(System.out::println);
		String lowerCase = question.getQuestion().toLowerCase();
		if (lowerCase.contains("not") || lowerCase.contains("never")) {
			return Util.minAt(scores);
		} else {
			return Util.maxAt(scores);
		}
	}

	public int[] getScores(Question question) {
		String pennString = new Sentence(question.getQuestion()).parse().pennString();
		// System.out.println(pennString);
		if (pennString.contains("JJS")) {
			return new KeyWordSearch2().getScores(question);
		} else {
			int[] scores = new KeyWordSearch().getScores(question);
			int max = Util.max(scores);
			double ratio = (double) (max - Util.min(scores)) / max;
			if (ratio < 0.14) {
				return new KeyWordSearch2().getScores(question);
			} else {
				return scores;
			}
		}
	}

	public static void main(String[] args) {
		Question question = new Gson().fromJson(
				"{\"answers\":[\"Steel\",\"Financial services\",\"Tech\"],\"question\":\"Three of the top seven best- performing stocks of last year were in which industry?\",\"oddOneOut\":false,\"correctAnswer\":0}\r\n"
						+ "",
				Question.class);
		System.out.println(new Sentence(question.getQuestion()).parse().pennString());
		System.out.println(new CombinedKeyWordSearch().answerQuestion(question));
	}

}
