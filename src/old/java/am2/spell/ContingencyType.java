package am2.spell;

public enum ContingencyType {
	/**
	 * Placeholder to avoid null
	 */
	NULL,
	/**
	 * When the entity dies
	 */
	DEATH,
	/**
	 * When the entity gets hit
	 */
	DAMAGE,
	/**
	 * When the entity hits the ground 
	 */
	FALL,
	/**
	 * When the entity goes under 25% hp
	 */
	HEALTH,
	/**
	 * When the entity is on fire
	 */
	FIRE;
	
	
	public static ContingencyType fromName(String name) {
		for (ContingencyType type : values()) {
			if (type.name().toLowerCase().equalsIgnoreCase(name))
				return type;
		}
		
		return NULL;
	}
}
