package minecraftschurli.arsmagicalegacy.capabilities;

import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.capability.IResearchStorage;
import minecraftschurli.arsmagicalegacy.api.registry.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class ResearchCapability implements ICapabilitySerializable<INBT> {
    private LazyOptional<IResearchStorage> pointsInstance = LazyOptional.of(CapabilityHelper.getResearchCapability()::getDefaultInstance);

    public static void register() {
        CapabilityManager.INSTANCE.register(IResearchStorage.class, new Capability.IStorage<IResearchStorage>() {
                    @Override
                    public INBT writeNBT(Capability<IResearchStorage> capability, IResearchStorage instance, Direction side) {
                        CompoundNBT compoundNBT = new CompoundNBT();
                        for (SkillPoint type : SkillPointRegistry.SKILL_POINT_REGISTRY.values().stream().filter(SkillPoint::canRender).collect(Collectors.toList())) {
                            compoundNBT.putInt("skillpoint" + type.getTier(), instance.get(type.getTier()));
                        }
                        ListNBT learned = new ListNBT();
                        instance.getLearned()
                                .stream()
                                .map(ResourceLocation::toString)
                                .map(StringNBT::valueOf)
                                .forEach(learned::add);
                        compoundNBT.put("learned", learned);
                        return compoundNBT;
                    }

                    @Override
                    public void readNBT(Capability<IResearchStorage> capability, IResearchStorage instance, Direction side, INBT nbt) {
                        for (SkillPoint type : SkillPointRegistry.SKILL_POINT_REGISTRY.values().stream().filter(SkillPoint::canRender).collect(Collectors.toList())) {
                            instance.set(type.getTier(), ((CompoundNBT) nbt).getInt("skillpoint" + type.getTier()));
                        }
                        ((CompoundNBT) nbt)
                                .getList("learned", Constants.NBT.TAG_STRING)
                                .stream()
                                .map(INBT::getString)
                                .map(ResourceLocation::new)
                                .forEach(instance::learn);
                    }
                },
                ResearchStorage::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityHelper.getResearchCapability() ? pointsInstance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityHelper.getResearchCapability().getStorage().writeNBT(CapabilityHelper.getResearchCapability(), this.pointsInstance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityHelper.getResearchCapability().getStorage().readNBT(CapabilityHelper.getResearchCapability(), this.pointsInstance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
