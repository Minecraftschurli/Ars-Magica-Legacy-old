package am2.extensions.datamanager;

import am2.packet.AMDataReader;
import am2.packet.AMDataWriter;

public class SavedObject<T> {
	
	private TypeSerializer<T> serializer;
	private final int id;
	
	protected SavedObject(TypeSerializer<T> serializer, int id) {
		this.serializer = serializer;
		this.id = id;
	}
	
	public void serialize(AMDataWriter buf, T data) throws Throwable {
		serializer.serialize(buf, data);
	}
	
	public T deserialize(AMDataReader buf) throws Throwable {
		return serializer.deserialize(buf);
	}
	
	public int getId() {
		return id;
	}
}
