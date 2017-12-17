package algorithm;

import edu.stanford.nlp.simple.Sentence;
import question.AttributeFunction;
import question.Question;

public class KeyPhrases implements AttributeFunction<String> {

	@Override
	public String apply(Question t) {
		return String.join(",", new Sentence(t.getQuestion()).algorithms().keyphrases());
	}

	public String getName() {
		return "Key Phrases";
	}
}
