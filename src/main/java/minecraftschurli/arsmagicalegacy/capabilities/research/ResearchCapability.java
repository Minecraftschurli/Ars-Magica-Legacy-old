package minecraftschurli.arsmagicalegacy.capabilities.research;

import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.skill.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;
import java.util.stream.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class ResearchCapability implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IResearchStorage.class)
    public static Capability<IResearchStorage> RESEARCH = null;

    private LazyOptional<IResearchStorage> pointsInstance = LazyOptional.of(RESEARCH::getDefaultInstance);

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
                                .map(StringNBT::new)
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

    /**
     * Retrieves the Optional handler for the capability requested on the specific side.
     * The return value <strong>CAN</strong> be the same for multiple faces.
     * Modders are encouraged to cache this value, using the listener capabilities of the Optional to
     * be notified if the requested capability get lost.
     *
     * @param cap
     * @param side
     * @return The requested an optional holding the requested capability.
     */
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == RESEARCH ? pointsInstance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return RESEARCH.getStorage().writeNBT(RESEARCH, this.pointsInstance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        RESEARCH.getStorage().readNBT(RESEARCH, this.pointsInstance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
