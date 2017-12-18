package algorithm;

public class Util {
	public static int maxAt(int[] array) {
		int maxAt = 0;
		for (int i = 0; i < array.length; i++) {
			maxAt = array[i] > array[maxAt] ? i : maxAt;
		}
		return maxAt;
	}

	public static int max(int[] array) {
		int max = 0;
		for (int i = 0; i < array.length; i++) {
			max = array[i] > max ? array[i] : max;
		}
		return max;
	}

	public static int minAt(int[] array) {
		int minAt = 0;
		for (int i = 0; i < array.length; i++) {
			minAt = array[i] < array[minAt] ? i : minAt;
		}
		return minAt;
	}

	public static int min(int[] array) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < array.length; i++) {
			min = array[i] < min ? array[i] : min;
		}
		return min;
	}
}
