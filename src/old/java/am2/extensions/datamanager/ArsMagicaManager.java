package am2.extensions.datamanager;

import java.util.ArrayList;

import com.google.common.collect.ImmutableList;

public class ArsMagicaManager {
	private static final ArrayList<SavedObject<?>> SAVED_OBJECTS = new ArrayList<>();
	
	public static <T> SavedObject<T> createSavedObject(TypeSerializer<T> serializer) {
		SavedObject<T> object = new SavedObject<T>(serializer, SAVED_OBJECTS.size());
		SAVED_OBJECTS.add(object);
		return object;
	}
	
	public static ImmutableList<SavedObject<?>> getSavedObjects() {
		return ImmutableList.copyOf(SAVED_OBJECTS);
	}
	
	@SuppressWarnings("rawtypes")
	protected static SavedObject getById(int id) {
		if (id > SAVED_OBJECTS.size()) return null;
		return SAVED_OBJECTS.get(id);
	}
}
