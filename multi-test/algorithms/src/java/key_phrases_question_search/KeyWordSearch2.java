package key_phrases_question_search;

import java.util.List;
import java.util.concurrent.CompletableFuture;
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

public class KeyWordSearch2 implements QuestionAlgorithm {
	private WebUtil util = WebUtil.getInstance();

	public int answerQuestion(Question question) {
		int[] answerScores = getScores(question);
		return Util.maxAt(answerScores);
	}

	public int[] getScores(Question question) {
		System.out.println("started keyword 2 analysis");
		String searchTerm = String.join(", ", question.storeAttribute(new KeyPhrases()).attributeVal);
		int[] answerScores = { 0, 0, 0 };
		String[] answers = question.getAnswers();
		CompletableFuture<?>[] futures = new CompletableFuture<?>[3];
		for (int x = 0; x < answers.length; x++) {
			final int z = x;
			futures[x] = CompletableFuture.runAsync(() -> {
				Search search = util.runSearch(searchTerm + " AND " + answers[z], 4);
				List<String> pages = search.getItems().stream().map(this::getParseString).collect(Collectors.toList());
				for (int y = 0; y < pages.size(); y++) {
					answerScores[z] += FuzzySearch.partialRatio(answers[z], pages.get(y));
				}
			});
		}
		CompletableFuture.allOf(futures).join();
		System.out.println("finished keyword 2 analysis");
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
		System.out.println(q.getAnswers()[new KeyWordSearch2().answerQuestion(q)]);
	}
}
