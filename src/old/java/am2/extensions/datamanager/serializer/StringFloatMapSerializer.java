package am2.extensions.datamanager.serializer;

import java.util.HashMap;
import java.util.Map.Entry;

import am2.extensions.datamanager.TypeSerializer;
import am2.packet.AMDataReader;
import am2.packet.AMDataWriter;

public class StringFloatMapSerializer implements TypeSerializer<HashMap<String, Float>> {
	
	public static StringFloatMapSerializer INSTANCE = new StringFloatMapSerializer();
	
	private StringFloatMapSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, HashMap<String, Float> value) {
		if (value == null) return;
		buf.add(value.size());
		for (Entry<String, Float> entry : value.entrySet()) {
			buf.add(entry.getKey());
			buf.add(entry.getValue() == null ? 0 : entry.getValue().floatValue());
		}
	}

	@Override
	public HashMap<String, Float> deserialize(AMDataReader buf) {
		int size = buf.getInt();
		HashMap<String, Float> retMap = new HashMap<>(size);
		for (int i = 0; i < size; i++) {
			String str = buf.getString();
			float param = buf.getFloat();
			retMap.put(str, param);
		}
		return retMap;
	}

}
