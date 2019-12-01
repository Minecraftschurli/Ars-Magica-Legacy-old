package am2.utils;

import java.util.ArrayList;

public class KeyValuePair<K, V> {
	
	public K key;
	public V value;
	
	public KeyValuePair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public static <T> ArrayList<T> merge(ArrayList<KeyValuePair<T, T>> pairs) {
		ArrayList<T> list = new ArrayList<>();
		for (KeyValuePair<T, T> pair : pairs) {
			list.add(pair.key);
			list.add(pair.value);
		}
		return list;
	}

}
