package am2.extensions.datamanager;

import am2.packet.AMDataReader;
import am2.packet.AMDataWriter;

public interface TypeSerializer<T> {
	
	public T deserialize(AMDataReader buf) throws Throwable;

	public void serialize(AMDataWriter buf, T value) throws Throwable;
}
