package am2.extensions.datamanager.serializer;

import java.util.HashMap;
import java.util.Map.Entry;

import am2.api.ArsMagicaAPI;
import am2.api.affinity.Affinity;
import am2.extensions.datamanager.TypeSerializer;
import am2.packet.AMDataReader;
import am2.packet.AMDataWriter;
import net.minecraft.util.ResourceLocation;

public class AffinityMapSerializer implements TypeSerializer<HashMap<Affinity, Double>> {
	
	public static AffinityMapSerializer INSTANCE = new AffinityMapSerializer();
	
	private AffinityMapSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, HashMap<Affinity, Double> value) {
		if (value == null) return;
		buf.add(value.size());
		for (Entry<Affinity, Double> entry : value.entrySet()) {
			buf.add(entry.getKey().getRegistryName().toString());
			buf.add(entry.getValue() == null ? 0 : entry.getValue().doubleValue());
		}
	}

	@Override
	public HashMap<Affinity, Double> deserialize(AMDataReader buf) {
		int size = buf.getInt();
		HashMap<Affinity, Double> retMap = new HashMap<>(size);
		for (int i = 0; i < size; i++) {
			Affinity aff = ArsMagicaAPI.getAffinityRegistry().getObject(new ResourceLocation(buf.getString()));
			double depth = buf.getDouble();
			retMap.put(aff, depth);
		}
		return retMap;
	}

}
