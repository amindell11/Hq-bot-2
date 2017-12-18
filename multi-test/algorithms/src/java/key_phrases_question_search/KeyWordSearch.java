package key_phrases_question_search;

import java.util.List;
import java.util.stream.Collectors;

import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import com.google.gson.Gson;

import algorithm.KeyPhrases;
import algorithm.QuestionAlgorithm;
import algorithm.Util;
import algorithm.WebUtil;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import question.Question;

public class KeyWordSearch implements QuestionAlgorithm {
	private WebUtil util = WebUtil.getInstance();

	@Override
	public int answerQuestion(Question question) {
		int[] answerScores = getScores(question);
		return Util.maxAt(answerScores);
	}

	public int[] getScores(Question question) {
		String searchTerm = String.join(", ", question.storeAttribute(new KeyPhrases()).attributeVal);
		Search search = util.runSearch(searchTerm, 9);
		System.out.println(search.getSearchInformation().keySet());
		System.out.println(searchTerm);
		int[] answerScores = { 0, 0, 0 };
		String[] answers = question.getAnswers();
		List<String> pages = search.getItems().stream().map(this::getParseString).collect(Collectors.toList());
		for (int x = 0; x < answers.length; x++) {
			for (int y = 0; y < pages.size(); y++) {
				answerScores[x] += FuzzySearch.partialRatio(answers[x], pages.get(y));
			}
		}
		return answerScores;
	}

	public String getParseString(Result searchResult) {
		// return util.getSiteText(searchResult.getLink());
		return searchResult.getSnippet();
	}

	public static void main(String[] args) {
		Question q = new Gson().fromJson(
				"{\"answers\":[\"Overspeed Governor\",\"Landing Doors\",\"Roller Guide\"],\"question\":\"What device stops an elevator from running beyond its rated speed?\",\"oddOneOut\":false,\"correctAnswer\":0}\r\n"
						+ "",
				Question.class);
		System.out.println(q.getAnswers()[new KeyWordSearch().answerQuestion(q)]);
	}
}
