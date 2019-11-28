package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.objects.entity.EntityThrownRock;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class FallingStar extends SpellComponent {

	@Override
	public ISpellIngredient[] getRecipe(){
		return new ISpellIngredient[]{
				//AffinityShiftUtils.getEssenceForAffinity(Affinity.ARCANE),
				new ItemStackSpellIngredient(new ItemStack(ModItems.ARCANE_ASH.get())),
				//AffinityShiftUtils.getEssenceForAffinity(Affinity.ARCANE),
//				new ItemStackSpellIngredient(new ItemStack(ModBlocks.MANE_BATTERY)),
				new ItemStackSpellIngredient(new ItemStack(Items.LAVA_BUCKET))
		};
	}

	private boolean spawnStar(ItemStack spellStack, LivingEntity caster, Entity target, World world, double x, double y, double z){

		List<EntityThrownRock> rocks = world.getEntitiesWithinAABB(EntityThrownRock.class, new AxisAlignedBB(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10));

		int damageMultitplier = SpellUtils.getModifiedIntMul(15, spellStack, caster, target, world, SpellModifiers.DAMAGE);
		for (EntityThrownRock rock : rocks){
			if (rock.getIsShootingStar())
				return false;
		}

		if (!world.isRemote){
			EntityThrownRock star = new EntityThrownRock(world);
			star.setPosition(x, world.getActualHeight(), z);
			star.setShootingStar(2 * damageMultitplier);
			star.setThrowingEntity(caster);
			star.setSpellStack(spellStack.copy());
			world.addEntity(star);
		}
		return true;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.of(SpellModifiers.DAMAGE, SpellModifiers.COLOR);
	}


	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster){
		return spawnStar(stack, caster, caster, world, impactX, impactY + 50, impactZ);
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target){
		return spawnStar(stack, caster, target, world, target.posX, target.posY + 50, target.posZ);
	}

	@Override
	public float getManaCost(LivingEntity caster){
		return 400;
	}

	@Override
	public ItemStack[] getReagents(LivingEntity caster){
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier){
	}

	/*@Override
	public Set<Affinity> getAffinity(){
		return Sets.newHashSet(Affinity.ARCANE);
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0.05f;
	}*/
	
	@Override
	public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
		
	}

}
