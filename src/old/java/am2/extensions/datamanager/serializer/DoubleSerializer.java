package am2.extensions.datamanager.serializer;

import am2.extensions.datamanager.TypeSerializer;
import am2.packet.AMDataReader;
import am2.packet.AMDataWriter;

public class DoubleSerializer implements TypeSerializer<Double> {
	
	public static DoubleSerializer INSTANCE = new DoubleSerializer();
	
	private DoubleSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, Double value) {
		buf.add(value == null ? 0 : value.doubleValue());
	}

	@Override
	public Double deserialize(AMDataReader buf) {
		return buf.getDouble();
	}

}
