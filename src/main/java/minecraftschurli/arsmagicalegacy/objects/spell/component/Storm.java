package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Storm extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        applyEffect(caster, world);
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        applyEffect(caster, world);
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    private void applyEffect(LivingEntity caster, World world) {
        float rainStrength = world.getRainStrength(1.0f);
        if (rainStrength > 0.9D) {
            if (!world.isRemote) {
                int xzradius = 50;
                int random = world.rand.nextInt(100);
                if (random < 20) {
                    int randPosX = (int) caster.getPosX() + world.rand.nextInt(xzradius * 2) - xzradius;
                    int randPosZ = (int) caster.getPosZ() + world.rand.nextInt(xzradius * 2) - xzradius;
                    int posY = (int) caster.getPosY();
                    while (!world.canBlockSeeSky(new BlockPos(randPosX, posY, randPosZ))) posY++;
                    while (world.getBlockState(new BlockPos(randPosX, posY - 1, randPosZ)).getBlock().equals(Blocks.AIR))
                        posY--;
                    LightningBoltEntity bolt = new LightningBoltEntity(world, randPosX, posY, randPosZ, false);
                    world.addEntity(bolt);
                } else if (random < 80) {
                    List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, caster.getBoundingBox().expand(xzradius, 10D, xzradius));
                    if (entities.size() <= 0) return;
                    Entity target = entities.get(world.rand.nextInt(entities.size()));
                    if (target != null && world.canBlockSeeSky(target.getPosition())) {
                        if (caster instanceof PlayerEntity)
                            target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) caster), 1);
                        LightningBoltEntity bolt = new LightningBoltEntity(world, target.getPosX(), target.getPosY(), target.getPosZ(), false);
                        world.addEntity(bolt);
                    }
                }
            }
        } else if (!world.isRemote) world.getWorldInfo().setRaining(true);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 15;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "symbols", x, y - 1, z);
//        if (particle != null) {
//            particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.2f, 2, false).SetTargetDistance(1));
//            particle.setMaxAge(40);
//            particle.setParticleScale(0.1f);
//            if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.LIGHTNING, Affinity.NATURE);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.YELLOW_RUNE.get())),
                new ItemTagSpellIngredient(ModTags.Items.GEMS_TOPAZ),
                new ItemStackSpellIngredient(new ItemStack(Items.GHAST_TEAR))
        };
    }

    //    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.00001f;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
