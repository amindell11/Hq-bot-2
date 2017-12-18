package key_phrases_question_search;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import algorithm.QuestionAlgorithm;
import algorithm.Util;
import question.Question;

public class CombinedKeyWordSearch implements QuestionAlgorithm {

	@Override
	public int answerQuestion(Question question) {
		int[] vals = CompletableFuture.supplyAsync(() -> new KeyWordSearch().getScores(question)).thenCombine(
				CompletableFuture.supplyAsync(() -> new KeyWordSearch2().getScores(question)), (aa, bb) -> {
					for (int x = 0; x < aa.length; x++) {
						System.out.println(aa[x] + " " + 2 * bb[x]);
						aa[x] += 2*bb[x];
					}
					return aa;
				}).join();
		Arrays.stream(vals).forEach(System.out::println);
		return Util.maxAt(vals);
	}

}
