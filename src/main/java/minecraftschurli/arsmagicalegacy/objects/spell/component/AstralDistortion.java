package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class AstralDistortion extends SpellComponent {

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction facing, double impactX, double impactY, double impactZ, LivingEntity caster) {

		/*if (world.getBlockState(pos).getBlock().equals(Blocks.SPAWNER)){
			boolean hasMatch = RitualShapeHelper.instance.matchesRitual(this, world, pos);
			if (hasMatch){
				if (!world.isRemote){
					world.setBlockToAir(pos);
					RitualShapeHelper.instance.consumeReagents(this, world, pos);
					RitualShapeHelper.instance.consumeShape(this, world, pos);
					EntityItem item = new EntityItem(world);
					item.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
					item.setEntityItemStack(new ItemStack(BlockDefs.inertSpawner));
					world.spawnEntityInWorld(item);
				}else{

				}

				return true;
			}
		}*/

        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
            int duration = (int) SpellUtils.getModifiedIntMul(ModEffects.DEFAULT_BUFF_DURATION, stack, caster, target, world, SpellModifiers.DURATION);
            //duration = SpellUtils.modifyDurationBasedOnArmor(caster, duration);

            if (!world.isRemote)
                ((LivingEntity) target).addPotionEffect(new EffectInstance(ModEffects.ASTRAL_DISTORTION.get(), duration, SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack)));
            return true;
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.BUFF_POWER, SpellModifiers.DURATION);
    }


    @Override
    public float getManaCost(LivingEntity caster) {
        return 80;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
		/*for (int i = 0; i < 10; ++i){
			AMParticle particle = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "pulse", x, y, z);
			if (particle != null){
				particle.addRandomOffset(5, 4, 5);
				particle.AddParticleController(new ParticleFloatUpward(particle, 0.2f, 0, 1, false));
				particle.setMaxAge(25 + rand.nextInt(10));
				particle.setRGBColorF(0.7f, 0.2f, 0.9f);
				if (colorModifier > -1){
					particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
				}
			}
		}*/
    }

	/*@Override
	public Set<Affinity> getAffinity(){
		return Sets.newHashSet(Affinity.ENDER);
	}*/

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.PURPLE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.ENDER_EYE))
        };
    }

	/*@Override
	public float getAffinityShift(Affinity affinity){
		return 0.05f;
	}*/

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
