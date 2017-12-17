package key_phrases_question_search;

import algorithm.KeyPhrases;
import algorithm.QuestionAlgorithm;
import question.Question;

public class Search implements QuestionAlgorithm {

	@Override
	public int answerQuestion(Question question) {
		String searchTerm = question.storeAttribute(new KeyPhrases()).attributeVal;
		WebUtil.
		return 0;
	}

}
