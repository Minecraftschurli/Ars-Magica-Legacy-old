package am2.extensions.datamanager.serializer;

import java.util.HashMap;
import java.util.Map.Entry;

import am2.api.SkillPointRegistry;
import am2.api.skill.SkillPoint;
import am2.extensions.datamanager.TypeSerializer;
import am2.packet.AMDataReader;
import am2.packet.AMDataWriter;

public class SkillPointMapSerializer implements TypeSerializer<HashMap<SkillPoint, Integer>> {
	
	public static SkillPointMapSerializer INSTANCE = new SkillPointMapSerializer();
	
	private SkillPointMapSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, HashMap<SkillPoint, Integer> value) {
		if (value == null) return;
		buf.add(value.size());
		for (Entry<SkillPoint, Integer> entry : value.entrySet()) {
			buf.add(entry.getKey().getName());
			buf.add(entry.getValue() == null ? 0 : entry.getValue().intValue());
		}
	}

	@Override
	public HashMap<SkillPoint, Integer> deserialize(AMDataReader buf) {
		int size = buf.getInt();
		HashMap<SkillPoint, Integer> retMap = new HashMap<>(size);
		for (int i = 0; i < size; i++) {
			SkillPoint point = SkillPointRegistry.fromName(buf.getString());
			int num = buf.getInt();
			retMap.put(point, num);
		}
		return retMap;
	}

}
