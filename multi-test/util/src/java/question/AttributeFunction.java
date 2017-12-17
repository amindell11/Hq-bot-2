package question;

import java.util.function.Function;

public interface AttributeFunction<T> extends Function<Question, T> {
	public default String getName() {
		throw new NoAttributeNameException();
	}

	public class NoAttributeNameException extends RuntimeException {
		private static final long serialVersionUID = -7978886027171875489L;

		@Override
		public String getMessage() {
			return "No name was set for this attribute";
		}
	}
}
