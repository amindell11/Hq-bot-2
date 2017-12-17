import org.apache.commons.lang3.StringUtils;

public final class Question {
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
		return question + "," + String.join(",", answers);
	}
}