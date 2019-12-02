package am2.extensions.datamanager.serializer;

import am2.extensions.datamanager.TypeSerializer;
import am2.packet.AMDataReader;
import am2.packet.AMDataWriter;

public class StringSerializer implements TypeSerializer<String> {
	
	public static StringSerializer INSTANCE = new StringSerializer();
	
	private StringSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, String value) {
		buf.add(value);
	}

	@Override
	public String deserialize(AMDataReader buf) {
		return buf.getString();
	}

}
