package am2.spell.shape;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.UUID;

import am2.api.spell.SpellModifiers;
import am2.api.spell.SpellShape;
import am2.defs.ItemDefs;
import am2.extensions.EntityExtension;
import am2.items.ItemOre;
import am2.items.ItemSpellBase;
import am2.items.SpellBase;
import am2.spell.SpellCastResult;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Toggle extends SpellShape {

	@Override
	public boolean isChanneled() {
		return false;
	}

	@Override
	public float manaCostMultiplier(ItemStack spellStack) {
		return 0.7f;
	}

	@Override
	public boolean isTerminusShape() {
		return false;
	}

	@Override
	public boolean isPrincipumShape() {
		return true;
	}

	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount) {
		String current = stack.getTagCompound().getString("ToggleShapeID");
		ArrayList<ItemStack> rs = EntityExtension.For(caster).runningStacks;
		int foundID = -1;
		for (int i = 0; i < rs.size(); i++) {
			ItemStack is = rs.get(i);
			if (is != null && is.getTagCompound() != null && is.getTagCompound().getString("ToggleShapeID").equals(current)) {
				foundID = i;
				break;
			}
		}
		if (foundID != -1) {
			EntityExtension.For(caster).runningStacks.remove(foundID);
			if (caster instanceof EntityPlayer) {
				InventoryPlayer inv = ((EntityPlayer)caster).inventory;
				for (int i = 0; i < inv.getSizeInventory(); i++) {
					ItemStack is = inv.getStackInSlot(i);
					if (is != null && is.getItem() instanceof SpellBase && is.getTagCompound() != null && is.getTagCompound().getString("ToggleShapeID").equals(current)) {
						is.getTagCompound().setBoolean("HasEffect", false);
					}
				}
			}
		} else {
			EntityExtension.For(caster).runningStacks.add(stack.copy());
			if (caster instanceof EntityPlayer) {
				InventoryPlayer inv = ((EntityPlayer)caster).inventory;
				for (int i = 0; i < inv.getSizeInventory(); i++) {
					ItemStack is = inv.getStackInSlot(i);
					if (is != null && is.getItem() instanceof SpellBase && is.getTagCompound() != null && is.getTagCompound().getString("ToggleShapeID").equals(current)) {
						is.getTagCompound().setBoolean("HasEffect", true);
					}
				}
			}
		}
		return SpellCastResult.SUCCESS;
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] {
				new ItemStack(Blocks.LEVER),
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM),
				new ItemStack(ItemDefs.greaterFocus)
		};
	}

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {
		tag.setString("ToggleShapeID", UUID.randomUUID().toString());
	}

	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.noneOf(SpellModifiers.class);
	}

}
