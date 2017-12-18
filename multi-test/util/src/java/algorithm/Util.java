package algorithm;

public class Util {
	public static int maxAt(int[] array) {
		int maxAt = 0;
		for (int i = 0; i < array.length; i++) {
			maxAt = array[i] > array[maxAt] ? i : maxAt;
		}
		return maxAt;
	}
}
