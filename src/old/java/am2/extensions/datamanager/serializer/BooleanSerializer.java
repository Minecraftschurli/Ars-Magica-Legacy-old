package am2.extensions.datamanager.serializer;

import am2.extensions.datamanager.TypeSerializer;
import am2.packet.AMDataReader;
import am2.packet.AMDataWriter;

public class BooleanSerializer implements TypeSerializer<Boolean> {
	
	public static BooleanSerializer INSTANCE = new BooleanSerializer();
	
	private BooleanSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, Boolean value) {
		buf.add(value == null ? false : value.booleanValue());
	}

	@Override
	public Boolean deserialize(AMDataReader buf) {
		return buf.getBoolean();
	}

}
