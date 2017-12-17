package question;

public class StoredAttribute<T> {
	public final T attributeVal;

	public StoredAttribute(Question q, AttributeFunction<T> attributeFunction) {
		this.attributeVal = attributeFunction.apply(q);
	}
}
