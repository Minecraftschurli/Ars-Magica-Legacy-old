package minecraftschurli.arsmagicalegacy.util;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public class NBTUtils {

    public static CompoundNBT getAM2Tag(CompoundNBT baseTag) {
        return addTag(baseTag, "AM2");
    }

    public static CompoundNBT getEssenceTag(CompoundNBT baseTag) {
        return addTag(getAM2Tag(baseTag), "Essence");
    }

    public static void writeVecToNBT(Vec3d vec, CompoundNBT nbt) {
        nbt.putDouble("X", vec.x);
        nbt.putDouble("Y", vec.y);
        nbt.putDouble("Z", vec.z);
    }

    public static Vec3d readVecFromNBT(CompoundNBT nbt) {
        return new Vec3d(nbt.getDouble("X"), nbt.getDouble("Y"), nbt.getDouble("Z"));
    }

    public static Object getValueAt(CompoundNBT baseTag, String tagName) {
        INBT base = baseTag.get(tagName);
        switch (base.getId()) {
            case 0:
                return null;
            case 1:
                return ((ByteNBT) base).getByte();
            case 2:
                return ((ShortNBT) base).getShort();
            case 3:
                return ((IntNBT) base).getInt();
            case 4:
                return ((LongNBT) base).getLong();
            case 5:
                return ((FloatNBT) base).getFloat();
            case 6:
                return ((DoubleNBT) base).getDouble();
            case 7:
                return ((ByteArrayNBT) base).getByteArray();
            case 8:
                return base.getString();
            case 9:
                return base;
            case 10:
                return ((IntArrayNBT) base).getIntArray();
            default:
                return null;
        }
    }

    public static CompoundNBT addTag(CompoundNBT upper, String name) {
        if (upper == null) throw new IllegalStateException("Base Tag must exist");
        CompoundNBT newTag = new CompoundNBT();
        if (upper.hasUniqueId(name)) {
            newTag = upper.getCompound(name);
        }
        upper.put(name, newTag);
        return newTag;
    }

    public static CompoundNBT addTag(CompoundNBT upper, CompoundNBT compound, String name) {
        if (upper == null) throw new IllegalStateException("Base Tag must exist");
        upper.put(name, compound);
        return upper;
    }

    public static ListNBT addList(CompoundNBT upper, int type, String name) {
        if (upper == null) throw new IllegalStateException("Base Tag must exist");
        ListNBT newTag = new ListNBT();
        if (upper.getList(name, type) != null) {
            newTag = upper.getList(name, type);
        }
        upper.put(name, newTag);
        return newTag;
    }

    public static ListNBT addCompoundList(CompoundNBT upper, String name) {
        return addList(upper, 10, name);
    }

    public static boolean contains(CompoundNBT container, CompoundNBT check) {
        if (container == null) return true;
        if (check == null) return false;
        boolean match = true;
        for (String key : container.keySet()) {
            INBT tag = container.get(key);
            INBT checkTag = check.get(key);
            if (tag == null)
                continue;
            if (checkTag == null) return false;
            if (tag instanceof CompoundNBT && checkTag instanceof CompoundNBT)
                match &= contains((CompoundNBT) tag, (CompoundNBT) checkTag);
            else
                match &= tag.equals(checkTag);
            if (!match)
                break;
        }
        return match;
    }

    public static ItemStack[] getItemStackArray(CompoundNBT tagCompound, String string) {
        ListNBT list = addCompoundList(tagCompound, string);
        ItemStack[] array = new ItemStack[list.size()];
        for (int i = 0; i < list.size(); i++) {
            CompoundNBT tmp = list.getCompound(i);
            array[tmp.getInt("ID")] = ItemStack.read(tmp);
        }
        return array;
    }

    public static void setItemStackArray(CompoundNBT tagCompound, String string, ItemStack[] recipeData) {
        ListNBT list = addCompoundList(tagCompound, string);
        for (int i = 0; i < recipeData.length; i++) {
            CompoundNBT tmp = new CompoundNBT();
            tmp.putInt("ID", i);
            recipeData[i].write(tmp);
            list.add(tmp);
        }
        tagCompound.put(string, list);
    }

}
