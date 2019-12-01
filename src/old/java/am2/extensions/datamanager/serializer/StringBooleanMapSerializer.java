package am2.extensions.datamanager.serializer;

import java.util.HashMap;
import java.util.Map.Entry;

import am2.extensions.datamanager.TypeSerializer;
import am2.packet.AMDataReader;
import am2.packet.AMDataWriter;

public class StringBooleanMapSerializer implements TypeSerializer<HashMap<String, Boolean>> {
	
	public static StringBooleanMapSerializer INSTANCE = new StringBooleanMapSerializer();
	
	private StringBooleanMapSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, HashMap<String, Boolean> value) {
		if (value == null) return;
		buf.add(value.size());
		for (Entry<String, Boolean> entry : value.entrySet()) {
			buf.add(entry.getKey());
			buf.add(entry.getValue() == null ? false : entry.getValue().booleanValue());
		}
	}

	@Override
	public HashMap<String, Boolean> deserialize(AMDataReader buf) {
		int size = buf.getInt();
		HashMap<String, Boolean> retMap = new HashMap<>(size);
		for (int i = 0; i < size; i++) {
			String str = buf.getString();
			boolean param = buf.getBoolean();
			retMap.put(str, param);
		}
		return retMap;
	}

}
