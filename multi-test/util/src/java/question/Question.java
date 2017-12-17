package question;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

public final class Question {
	private static final Gson SERIALIZER = new Gson();
	private final String[] answers;
	private final String question;
	private final boolean oddOneOut;

	public Question(String question, String[] answers) {
		this.question = question;
		this.answers = answers;
		this.oddOneOut = StringUtils.containsIgnoreCase(question, "not");
		System.out.println("oddOneOut: " + oddOneOut);
	}

	public String[] getAnswers() {
		return answers;
	}

	public String getQuestion() {
		return question;
	}

	public boolean isOddOneOut() {
		return oddOneOut;
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