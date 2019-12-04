package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.blocks.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.Event.*;

import java.util.*;

public class Grow extends SpellComponent {
    private final static ArrayList<BlockAMFlower> growableAMflowers = new ArrayList<BlockAMFlower>(Arrays.asList(ModBlocks.cerublossom, ModBlocks.desertNova, ModBlocks.wakebloom, ModBlocks.aum, ModBlocks.tarmaRoot));

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        BlockState block = world.getBlockState(pos);
        BonemealEvent event = new BonemealEvent(DummyPlayerEntity.fromEntityLiving(caster), world, pos, world.getBlockState(pos));
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return false;
        }
        if (event.getResult() == Result.ALLOW) {
            return true;
        }
        //EoD: Spawn AM2 flowers with 3% chance. This has to be the first one in the list to override all others
        if (world.rand.nextInt(100) < 3 && block.isNormalCube()) {
            // shuffle the flower list every time we want to try to find one.
            Collections.shuffle(growableAMflowers);
            for (BlockAMFlower flower : growableAMflowers) {
                if (flower.canGrowOn(world, pos.up())) {
                    if (!world.isRemote) {
                        world.setBlockState(pos.up(), flower.getDefaultState());
                    }
                    return true;
                }
            }
            // We did not find a flower or we have been executed on the wrong block. Either way, we continue
        }
        //Grow huge mushrooms 10% of the time.
        if (block.getBlock() instanceof BlockMushroom) {
            if (!world.isRemote && world.rand.nextInt(10) < 1) {
                ((BlockMushroom) block.getBlock()).grow(world, world.rand, pos, block);
            }
            return true;
        }
        //If the spell is executed in water, check if we have space for a wakebloom above and create one 3% of the time.
        if (block.getBlock() == Blocks.WATER) {
            if (world.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
                if (!world.isRemote && world.rand.nextInt(100) < 3) {
                    world.setBlockState(pos.up(), ModBlocks.wakebloom.getDefaultState());
                }
                return true;
            }
        }
        //EoD: If there is already tallgrass present, let's grow it further 20% of the time.
        if (block.getBlock() == Blocks.TALLGRASS) {
            if (Blocks.TALLGRASS.canBlockStay(world, pos, Blocks.TALLGRASS.getDefaultState())) {
                if (!world.isRemote && world.rand.nextInt(10) < 2) {
                    world.setBlockState(pos, Blocks.TALLGRASS.getDefaultState());
                }
                return true;
            }
        }
        //EoD: If there is already deadbush present, let's revitalize it 20% of the time.
        //     This works only on podzol in vanilla MC.
        if (block.getBlock() == Blocks.DEADBUSH) {
            if (Blocks.DEADBUSH.canBlockStay(world, pos, Blocks.DEADBUSH.getDefaultState())) {
                if (!world.isRemote && world.rand.nextInt(10) < 2) {
                    world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
                }
                return true;
            }
        }
        // EoD: Apply vanilla bonemeal effect to growables 30% of the time. This is the generic grow section.
        //      See ItemDye.applyBonemeal().
        if (block instanceof IGrowable) {
            IGrowable igrowable = (IGrowable) block;
            //AMCore.log.getLogger().info("Grow component found IGrowable");
            if (igrowable.canGrow(world, pos, block, world.isRemote)) {
                if (!world.isRemote && world.rand.nextInt(10) < 3) {
                    if (igrowable.canUseBonemeal(world, world.rand, pos, block)) {
                        igrowable.grow(world, world.rand, pos, block);
                    }
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 17.4f;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "plant", x + 0.5, y + 1, z + 0.5);
            if (particle != null) {
                particle.addRandomOffset(1, 1, 1);
                particle.AddParticleController(new ParticleFloatUpward(particle, 0, 0.1f, 1, false));
                particle.AddParticleController(new ParticleOrbitPoint(particle, x + 0.5, y + 0.5, z + 0.5, 2, false).setIgnoreYCoordinate(true).SetOrbitSpeed(0.1f).SetTargetDistance(0.3f + rand.nextDouble() * 0.3));
                particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.05f).setKillParticleOnFinish(true));
                particle.setMaxAge(20);
                particle.setParticleScale(0.1f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.NATURE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.GREEN_RUNE.get()),
                new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage()),
                //TODO BlocksCommonProxy.witchwoodLog
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.02f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}