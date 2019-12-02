package am2.entity;

import java.util.ArrayList;

import com.google.common.collect.ArrayListMultimap;

import am2.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class SpawnBlacklists{
	private static ArrayListMultimap<Integer, Class<? extends Entity>> blacklistedDimensionSpawns = ArrayListMultimap.create();
	private static ArrayListMultimap<Integer, Class<? extends Entity>> blacklistedBiomeSpawns = ArrayListMultimap.create();
	private static ArrayList<Integer> blacklistedWorldgenDimensions = new ArrayList<Integer>();
	private static ArrayList<Class<? extends Entity>> progenyBlacklist = new ArrayList<>();
	private static ArrayList<Class<? extends Entity>> butcheryBlacklist = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public static void addBlacklistedDimensionSpawn(String entityClass, Integer dimensionID){
		Class<? extends Entity> clazz;
		try{
			clazz = (Class<? extends Entity>) Class.forName(entityClass);
			blacklistedDimensionSpawns.put(dimensionID, clazz);
			LogHelper.info("Blacklisted %s from spawning in dimension %d.", entityClass, dimensionID);
		}catch (ClassNotFoundException e){
			LogHelper.info("Unable to parse class name %s from IMC!  This needs to be corrected by the other mod author!", entityClass);
		}
	}

	@SuppressWarnings("unchecked")
	public static void addBlacklistedBiomeSpawn(String entityClass, Integer biomeID){
		Class<? extends Entity> clazz;
		try{
			clazz = (Class<? extends Entity>) Class.forName(entityClass);
			blacklistedBiomeSpawns.put(biomeID, clazz);
			LogHelper.info("Blacklisted %s from spawning in biome %d.", entityClass, biomeID);
		}catch (ClassNotFoundException e){
			LogHelper.info("Unable to parse class name %s from IMC!  This needs to be corrected by the other mod author!", entityClass);
		}
	}

	public static boolean entityCanSpawnHere(BlockPos pos, World world, EntityLivingBase entity){
		if (blacklistedDimensionSpawns.containsEntry(world.provider.getDimension(), entity.getClass()))
			return false;
		Biome biome = world.getBiome(pos);
		if (blacklistedBiomeSpawns.containsEntry(biome.getBiomeName(), entity.getClass()))
			return false;

		return true;
	}

	public static void addBlacklistedDimensionForWorldgen(int dimensionID){
		if (dimensionID == 0 || dimensionID == -1)
			return;
		blacklistedWorldgenDimensions.add(dimensionID);
	}

	public static boolean worldgenCanHappenInDimension(int dimensionID){
		return !blacklistedWorldgenDimensions.contains(dimensionID);
	}

	public static void addButcheryBlacklist(Class<? extends Entity> clazz){
		if (!butcheryBlacklist.contains(clazz))
			butcheryBlacklist.add(clazz);
	}

	public static void addProgenyBlacklist(Class<? extends Entity> clazz){
		if (!progenyBlacklist.contains(clazz))
			progenyBlacklist.add(clazz);
	}

	public static boolean canButcheryAffect(Class<? extends Entity> clazz){
		return !butcheryBlacklist.contains(clazz);
	}

	public static boolean canProgenyAffect(Class<? extends Entity> clazz){
		return !progenyBlacklist.contains(clazz);
	}
}
