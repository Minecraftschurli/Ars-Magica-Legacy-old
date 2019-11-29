package minecraftschurli.arsmagicalegacy.capabilities.research;

import minecraftschurli.arsmagicalegacy.api.spellsystem.SkillPoint;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class CapabilityResearch implements ICapabilitySerializable<INBT> {

    /*@CapabilityInject(ISkillTreeStorage.class)
    public static Capability<ISkillTreeStorage> SKILL_TREE = null;*/

    @CapabilityInject(IResearchPointsStorage.class)
    public static Capability<IResearchPointsStorage> RESEARCH_POINTS = null;

    private LazyOptional<IResearchPointsStorage> pointsInstance = LazyOptional.of(RESEARCH_POINTS::getDefaultInstance);

    public static void register() {
        CapabilityManager.INSTANCE.register(IResearchPointsStorage.class, new Capability.IStorage<IResearchPointsStorage>() {
                    @Override
                    public INBT writeNBT(Capability<IResearchPointsStorage> capability, IResearchPointsStorage instance, Direction side) {
                        CompoundNBT compoundNBT = new CompoundNBT();
                        for (SkillPoint type : SkillPoint.TYPES) {
                            compoundNBT.putInt(type.getName(), instance.get(type.getName()));
                        }
                        return compoundNBT;
                    }

                    @Override
                    public void readNBT(Capability<IResearchPointsStorage> capability, IResearchPointsStorage instance, Direction side, INBT nbt) {
                        for (SkillPoint type : SkillPoint.TYPES) {
                            instance.set(type.getName(), ((CompoundNBT) nbt).getInt(type.getName()));
                        }
                    }
                },
                ResearchPointsStorage::new);
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
        return cap == RESEARCH_POINTS ? pointsInstance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return RESEARCH_POINTS.getStorage().writeNBT(RESEARCH_POINTS, this.pointsInstance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        RESEARCH_POINTS.getStorage().readNBT(RESEARCH_POINTS, this.pointsInstance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
