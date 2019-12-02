package am2.extensions.datamanager.serializer;

import am2.extensions.datamanager.TypeSerializer;
import am2.packet.AMDataReader;
import am2.packet.AMDataWriter;

public class IntegerSerializer implements TypeSerializer<Integer> {
	
	public static IntegerSerializer INSTANCE = new IntegerSerializer();
	
	private IntegerSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, Integer value) {
		buf.add(value == null ? 0 : value.intValue());
	}

	@Override
	public Integer deserialize(AMDataReader buf) {
		return buf.getInt();
	}

}
