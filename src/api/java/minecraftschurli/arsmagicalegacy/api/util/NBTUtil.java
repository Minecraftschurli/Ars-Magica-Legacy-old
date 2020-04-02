package minecraftschurli.arsmagicalegacy.api.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CollectionNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.math.Vec3d;

import java.util.Map;

/**
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public final class NBTUtil {
    public static ListNBT addCompoundList(CompoundNBT upper, String name) {
        if (upper == null) throw new IllegalStateException("Base Tag must exist");
        upper.getList(name, 10);
        ListNBT newTag = upper.getList(name, 10);
        upper.put(name, newTag);
        return newTag;
    }

    public static CompoundNBT addTag(CompoundNBT upper, String name) {
        if (upper == null) throw new IllegalStateException("Base Tag must exist");
        CompoundNBT newTag = new CompoundNBT();
        if (upper.contains(name)) newTag = upper.getCompound(name);
        upper.put(name, newTag);
        return newTag;
    }

    public static CompoundNBT addTag(CompoundNBT upper, CompoundNBT compound, String name) {
        if (upper == null) throw new IllegalStateException("Base Tag must exist");
        upper.put(name, compound);
        return upper;
    }

    public static boolean contains(CompoundNBT container, CompoundNBT check) {
        if (container == null) return true;
        if (check == null) return false;
        boolean match = true;
        for (String key : container.keySet()) {
            INBT tag = container.get(key);
            INBT checkTag = check.get(key);
            if (tag == null) continue;
            if (checkTag == null) return false;
            match = tag.equals(checkTag);
            if (!match) break;
        }
        return match;
    }

    public static CompoundNBT getAMLTag(CompoundNBT baseTag) {
        return addTag(baseTag, "AML");
    }

    public static CompoundNBT getEssenceTag(CompoundNBT baseTag) {
        return addTag(getAMLTag(baseTag), "Essence");
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

    public static INBT jsonToNBT(JsonElement json) {
        if (json.isJsonArray()) {
            ListNBT list = new ListNBT();
            for (JsonElement e : json.getAsJsonArray()) list.add(jsonToNBT(e));
            return list;
        }
        if (json.isJsonNull()) return StringNBT.valueOf("null");
        if (json.isJsonObject()) {
            CompoundNBT compound = new CompoundNBT();
            for (Map.Entry<String, JsonElement> jsonEntry : json.getAsJsonObject().entrySet())
                compound.put(jsonEntry.getKey(), jsonToNBT(jsonEntry.getValue()));
            return compound;
        }
        if (json.isJsonPrimitive()) {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isBoolean())
                return ByteNBT.valueOf(primitive.getAsBoolean());
            if (primitive.isString())
                return StringNBT.valueOf(primitive.getAsString());
            if (primitive.isNumber()) {
                Number number = primitive.getAsNumber();
                if (number instanceof Byte)
                    return ByteNBT.valueOf(number.byteValue());
                if (number instanceof Short)
                    return ShortNBT.valueOf(number.shortValue());
                if (number instanceof Integer)
                    return IntNBT.valueOf(number.intValue());
                if (number instanceof Long)
                    return LongNBT.valueOf(number.longValue());
                if (number instanceof Float)
                    return FloatNBT.valueOf(number.floatValue());
                if (number instanceof Double)
                    return DoubleNBT.valueOf(number.doubleValue());
                return IntNBT.valueOf(number.intValue());
            }
        }
        return new CompoundNBT();
    }

    public static JsonElement NBTToJson(INBT inbt) {
        if (inbt instanceof NumberNBT)
            return new JsonPrimitive(((NumberNBT) inbt).getAsNumber());
        if (inbt instanceof StringNBT) {
            if (inbt.getString().equals("null"))
                return JsonNull.INSTANCE;
            return new JsonPrimitive(inbt.getString());
        }
        if (inbt instanceof CompoundNBT) {
            JsonObject object = new JsonObject();
            for (String s : ((CompoundNBT) inbt).keySet()) object.add(s, NBTToJson(((CompoundNBT) inbt).get(s)));
            return object;
        }
        if (inbt instanceof CollectionNBT) {
            JsonArray array = new JsonArray();
            for (INBT o : ((CollectionNBT<? extends INBT>) inbt)) array.add(NBTToJson(o));
            return array;
        }
        return JsonNull.INSTANCE;
    }

    public static Vec3d readVecFromNBT(CompoundNBT nbt) {
        return new Vec3d(nbt.getDouble("X"), nbt.getDouble("Y"), nbt.getDouble("Z"));
    }

    public static void writeVecToNBT(Vec3d vec, CompoundNBT nbt) {
        nbt.putDouble("X", vec.x);
        nbt.putDouble("Y", vec.y);
        nbt.putDouble("Z", vec.z);
    }
}
