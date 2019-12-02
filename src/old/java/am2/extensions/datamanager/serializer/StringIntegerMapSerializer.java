package am2.extensions.datamanager.serializer;

import java.util.HashMap;
import java.util.Map.Entry;

import am2.extensions.datamanager.TypeSerializer;
import am2.packet.AMDataReader;
import am2.packet.AMDataWriter;

public class StringIntegerMapSerializer implements TypeSerializer<HashMap<String, Integer>> {
	
	public static StringIntegerMapSerializer INSTANCE = new StringIntegerMapSerializer();
	
	private StringIntegerMapSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, HashMap<String, Integer> value) {
		if (value == null) return;
		buf.add(value.size());
		for (Entry<String, Integer> entry : value.entrySet()) {
			buf.add(entry.getKey());
			buf.add(entry.getValue() == null ? 0 : entry.getValue().intValue());
		}
	}

	@Override
	public HashMap<String, Integer> deserialize(AMDataReader buf) {
		int size = buf.getInt();
		HashMap<String, Integer> retMap = new HashMap<>(size);
		for (int i = 0; i < size; i++) {
			String str = buf.getString();
			int param = buf.getInt();
			retMap.put(str, param);
		}
		return retMap;
	}

}
