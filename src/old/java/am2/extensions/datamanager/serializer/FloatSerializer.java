package am2.extensions.datamanager.serializer;

import am2.extensions.datamanager.TypeSerializer;
import am2.packet.AMDataReader;
import am2.packet.AMDataWriter;

public class FloatSerializer implements TypeSerializer<Float> {
	
	public static FloatSerializer INSTANCE = new FloatSerializer();
	
	private FloatSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, Float value) {
		buf.add(value == null ? 0 : value.floatValue());
	}

	@Override
	public Float deserialize(AMDataReader buf) {
		return buf.getFloat();
	}

}
