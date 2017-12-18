package algorithm;

import edu.stanford.nlp.simple.Sentence;
import question.AttributeFunction;
import question.Question;

public class KeyPhrases implements AttributeFunction<String[]> {

	@Override
	public String[] apply(Question t) {
		return new Sentence(t.getQuestion()).algorithms().keyphrases().toArray(new String[0]);
	}

	public String getName() {
		return "Key Phrases";
	}
}
