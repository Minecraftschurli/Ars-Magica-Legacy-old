package minecraftschurli.arsmagicalegacy.objects.entity;

import minecraftschurli.arsmagicalegacy.init.ModEntities;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ManaCreeperEntity extends CreeperEntity {
    private int timeSinceIgnited;

    public ManaCreeperEntity(World world) {
        super(ModEntities.MANA_CREEPER.get(), world);
    }

    public ManaCreeperEntity(EntityType<? extends CreeperEntity> type, World world) {
        super(type, world);
    }

    public void tick() {
        if (this.isAlive()) {
            int i = this.getCreeperState();
            if (i > 0 && this.timeSinceIgnited == 0) this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            this.timeSinceIgnited += i;
            if (this.timeSinceIgnited < 0) this.timeSinceIgnited = 0;
            if (this.timeSinceIgnited >= 30) {
                this.timeSinceIgnited = 30;
                if (!this.world.isRemote) {
//                    ManaVortexEntity vortex = new ManaVortexEntity(world);
//                    vortex.setPosition(this.getPosX(), this.getPosY() + 1, this.getPosZ());
//                    world.addEntity(vortex);
                }
            }
        }
        super.tick();
    }

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        for (EquipmentSlotType equipmentslottype : EquipmentSlotType.values()) {
            ItemStack itemstack = this.getItemStackFromSlot(equipmentslottype);
            float f = this.getDropChance(equipmentslottype);
            boolean flag = f > 1.0F;
            if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack) && (recentlyHitIn || flag) && Math.max(this.rand.nextFloat() - (float) looting * 0.01F, 0.0F) < f) {
                if (!flag && itemstack.isDamageable())
                    itemstack.setDamage(itemstack.getMaxDamage() - this.rand.nextInt(1 + this.rand.nextInt(Math.max(itemstack.getMaxDamage() - 3, 1))));
                this.entityDropItem(itemstack);
            }
        }
    }
}
