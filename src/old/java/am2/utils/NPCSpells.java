package am2.utils;

import com.google.common.collect.Lists;

import am2.api.ArsMagicaAPI;
import am2.api.spell.AbstractSpellPart;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class NPCSpells{
	public static final NPCSpells instance = new NPCSpells();

	public final ItemStack lightMage_DiminishedAttack;
	public final ItemStack lightMage_NormalAttack;
	public final ItemStack lightMage_AugmentedAttack;

	public final ItemStack darkMage_DiminishedAttack;
	public final ItemStack darkMage_NormalAttack;
	public final ItemStack darkMage_AugmentedAttack;

	public final ItemStack enderGuardian_enderWave;
	public final ItemStack enderGuardian_enderBolt;
	public final ItemStack enderGuardian_enderTorrent;
	public final ItemStack enderGuardian_otherworldlyRoar;

	public final ItemStack dispel;
	public final ItemStack blink;
	public final ItemStack arcaneBolt;
	public final ItemStack meltArmor;
	public final ItemStack waterBolt;
	public final ItemStack fireBolt;
	public final ItemStack healSelf;
	public final ItemStack nauseate;
	public final ItemStack lightningRune;
	public final ItemStack scrambleSynapses;

	private NPCSpells(){
		lightMage_DiminishedAttack = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), PhysicalDamage()), new NBTTagCompound());

		lightMage_NormalAttack = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), FrostDamage(), Slow()), new NBTTagCompound());

		lightMage_AugmentedAttack = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), MagicDamage(), Blind(), Damage()), new NBTTagCompound());

		darkMage_DiminishedAttack = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), MagicDamage()), new NBTTagCompound());

		darkMage_NormalAttack = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), FireDamage(), Ignition()), new NBTTagCompound());

		darkMage_AugmentedAttack = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), LightningDamage(), Knockback(), Damage()), new NBTTagCompound());

		enderGuardian_enderWave = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Wave(), Radius(), Radius(), MagicDamage(), Knockback()), new NBTTagCompound());

		enderGuardian_enderBolt = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), MagicDamage(), RandomTeleport(), Damage()), new NBTTagCompound());

		enderGuardian_otherworldlyRoar = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(AoE(), Blind(), Silence(), Knockback(), Radius(), Radius(), Radius(), Radius(), Radius()), new NBTTagCompound());

		enderGuardian_enderTorrent = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), Silence(), Knockback(), Speed(), AoE(), ManaDrain(), LifeDrain()), new NBTTagCompound());

		dispel = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Self(), Dispel()), new NBTTagCompound());

		blink = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Self(), Blink()), new NBTTagCompound());

		arcaneBolt = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), MagicDamage()), new NBTTagCompound());

		meltArmor = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), MeltArmor()), new NBTTagCompound());

		waterBolt = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), WateryGrave(), Drown()), new NBTTagCompound());

		fireBolt = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), FireDamage(), Ignition()), new NBTTagCompound());

		healSelf = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Self(), Heal()), new NBTTagCompound());

		nauseate = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), Nauseate(), ScrambleSynapses()), new NBTTagCompound());

		lightningRune = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), Rune(), AoE(), LightningDamage(), Damage()), new NBTTagCompound());
		
		scrambleSynapses = SpellUtils.createSpellStack(Lists.newArrayList(), Lists.newArrayList(Projectile(), LightningDamage(), AoE(), ScrambleSynapses(), Radius(), Radius(), Radius(), Radius(), Radius()), new NBTTagCompound());
	}

	private AbstractSpellPart AoE() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "aoe"));}
	private AbstractSpellPart FrostDamage() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "frost_damage"));}
	private AbstractSpellPart MagicDamage() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "magic_damage"));}
	private AbstractSpellPart Radius() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "radius"));}
	private AbstractSpellPart PhysicalDamage() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "physical_damage"));}
	private AbstractSpellPart Projectile() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "projectile"));}
	private AbstractSpellPart ScrambleSynapses() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "scramble_synapses"));}
	private AbstractSpellPart Damage() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "damage"));}
	private AbstractSpellPart LightningDamage() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "lightning_damage"));}
	private AbstractSpellPart Slow() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "slow"));}
	private AbstractSpellPart Blind() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "blind"));}
	private AbstractSpellPart FireDamage() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "fire_damage"));}
	private AbstractSpellPart Ignition() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "ignition"));}
	private AbstractSpellPart Knockback() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "knockback"));}
	private AbstractSpellPart Wave() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "wave"));}
	private AbstractSpellPart RandomTeleport() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "random_teleport"));}
	private AbstractSpellPart Silence() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "silence"));}
	private AbstractSpellPart Speed() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "speed"));}
	private AbstractSpellPart ManaDrain() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "mana_drain"));}
	private AbstractSpellPart LifeDrain() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "life_drain"));}
	private AbstractSpellPart Self() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "self"));}
	private AbstractSpellPart Dispel() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "dispel"));}
	private AbstractSpellPart Blink() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "blink"));}
	private AbstractSpellPart MeltArmor() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "melt_armor"));}
	private AbstractSpellPart WateryGrave() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "watery_grave"));}
	private AbstractSpellPart Drown() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "drown"));}
	private AbstractSpellPart Heal() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "heal"));}
	private AbstractSpellPart Nauseate() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "nauseate"));}
	private AbstractSpellPart Rune() {return ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2", "rune"));}
}
