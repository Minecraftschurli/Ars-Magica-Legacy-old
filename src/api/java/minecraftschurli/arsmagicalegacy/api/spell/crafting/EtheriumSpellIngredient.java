package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import com.google.common.collect.ImmutableSet;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumConsumer;
import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class EtheriumSpellIngredient implements ISpellIngredient {
    public static final String TYPE = "essence";
    private int amount;
    private Set<EtheriumType> essenceType;
    private EtheriumConsumer consumer;
    private boolean consuming;

    EtheriumSpellIngredient(CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    public EtheriumSpellIngredient(int amount, EtheriumType... type) {
        this.essenceType = ImmutableSet.copyOf(type);
        this.amount = amount;
    }

    @SafeVarargs
    public EtheriumSpellIngredient(int amount, Supplier<EtheriumType>... type) {
        this.essenceType = Arrays.stream(type).map(Supplier::get).collect(ImmutableSet.toImmutableSet());
        this.amount = amount;
    }

    public EtheriumSpellIngredient(EtheriumType type) {
        this( 1, type);
    }

    public EtheriumSpellIngredient(int amount) {
        this(amount, new EtheriumType[0]);
    }

    @Override
    public void writeToNBT(CompoundNBT nbt) {
        ListNBT essence = new ListNBT();
        for (EtheriumType type : essenceType) {
            essence.add(StringNBT.valueOf(type.getRegistryName().toString()));
        }
        nbt.put("essence", essence);
        nbt.putInt("amount", amount);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        essenceType = nbt.getList("essence", Constants.NBT.TAG_STRING)
                .stream()
                .map(INBT::getString)
                .map(ResourceLocation::tryCreate)
                .filter(Objects::nonNull)
                .map(RegistryHandler.getEtheriumRegistry()::getValue)
                .collect(Collectors.toSet());
        amount = nbt.getInt("amount");
    }

    @Override
    public boolean canCombine(ISpellIngredient other) {
        return (other instanceof EtheriumSpellIngredient) && ((EtheriumSpellIngredient) other).essenceType.equals(this.essenceType);
    }

    @Override
    public ISpellIngredient combine(ISpellIngredient other) {
        return new EtheriumSpellIngredient(amount + ((EtheriumSpellIngredient) other).amount, this.essenceType.toArray(new EtheriumType[0]));
    }

    @Override
    public ITextComponent getTooltip() {
        if (this.essenceType.isEmpty())
            return new TranslationTextComponent(ArsMagicaAPI.MODID+".essence_type.any");
        return this.getEssenceTypes()
                .stream()
                .map(EtheriumType::getDisplayName)
                .reduce((iTextComponent, component) -> iTextComponent.appendText(" | ").appendSibling(component))
                .orElse(new TranslationTextComponent(ArsMagicaAPI.MODID+".essence_type.any"));
    }

    @Override
    public boolean consume(World world, BlockPos pos) {
        //TODO implement
        this.consuming = !this.consumer.tick();
        return !this.consuming;
    }

    @Override
    public String toString() {
        return "EssenceSpellIngredient{" +
                "amount=" + amount +
                ", essenceType=" + Arrays.toString(essenceType.toArray(new EtheriumType[0])) +
                '}';
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public int getAmount() {
        return amount;
    }

    public Set<EtheriumType> getEssenceTypes() {
        return this.essenceType;
    }
}
