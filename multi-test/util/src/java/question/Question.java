package question;

import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.Gson;

public final class Question {
	private static final Gson SERIALIZER = new Gson();
	private final String[] answers;
	private final String question;
	private int correctAnswer;

	private HashMap<String, StoredAttribute<?>> attributeMap;

	public Question(String question, String[] answers) {
		this.question = question;
		this.answers = answers;
	}

	public String[] getAnswers() {
		return answers;
	}

	public String getQuestion() {
		return question;
	}

	public void setCorrectAnswer(String answer) {
		this.correctAnswer = Arrays.asList(answers).indexOf(answer);
	}

	public boolean isCorrect(int answer) {
		return answer == correctAnswer;
	}

	public String toString() {
		return question + "\t[" + String.join(", ", answers) + "]";
	}

	public String getAnswerString() {
		return String.join(" ", answers);
	}

	public String getLoggableString() {
		return SERIALIZER.toJson(this);
	}

	public <T> StoredAttribute<T> storeAttribute(AttributeFunction<T> attributeFunction) {
		return storeAttribute(attributeFunction.getName(), attributeFunction);
	}

	public <T> StoredAttribute<T> storeAttribute(String name, AttributeFunction<T> attributeFunction) {
		StoredAttribute<T> storedAttribute = new StoredAttribute<>(this, attributeFunction);
		attributeMap.put(name, storedAttribute);
		return storedAttribute;
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String attributeName) {
		return (T) attributeMap.get(attributeName).attributeVal;
	}

	public static Question parseQuestion(String loggedString) {
		return SERIALIZER.fromJson(loggedString, Question.class);
	}

	public static void main(String[] args) {
		Gson gson = new Gson();
		String json = gson
				.toJson(new Question("why is ari sick", new String[] { "he just is", "idk", "lemme suck it" }));
		System.out.println(json);
	}
}