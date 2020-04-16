package minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javafx.util.Pair;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.event.SpellRecipeItemsEvent;
import minecraftschurli.arsmagicalegacy.api.network.InscriptionTablePacket;
import minecraftschurli.arsmagicalegacy.api.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.registry.SpellRegistry;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.SpellIngredientList;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import minecraftschurli.arsmagicalegacy.objects.spell.SpellValidator;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
public class InscriptionTableTileEntity extends TileEntity implements IInventory, ITickableTileEntity {
    public static final int MAX_STAGE_GROUPS = 5;
    public static final String NEWPAGE = "<newpage>";
    private static final String ID_KEY = "ID";
    private static final String SLOT_KEY = "Slot";
    private static final String CURRENT_RECIPE_KEY = "CurrentRecipe";
    private static final String SHAPE_GROUPS_KEY = "ShapeGroups";
    private static final String NUM_SHAPE_GROUP_SLOTS_KEY = "numShapeGroupSlots";
    private static final String CURRENT_SPELL_NAME_KEY = "currentSpellName";
    private final List<AbstractSpellPart> currentRecipe;
    private final List<List<AbstractSpellPart>> shapeGroups;
    private final NonNullList<ItemStack> inscriptionTableItemStacks;
    private final Map<SpellModifiers, Integer> modifierCount;
    private boolean currentSpellIsReadOnly;
    private String currentSpellName;
    private int numStageGroups;
    private PlayerEntity currentPlayerUsing;

    public InscriptionTableTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        currentRecipe = new ArrayList<>();
        inscriptionTableItemStacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        modifierCount = new HashMap<>();
        shapeGroups = new ArrayList<>();
        currentSpellName = "";
        for (int i = 0; i < MAX_STAGE_GROUPS; i++) {
            shapeGroups.add(new ArrayList<>());
        }
        resetModifierCount();
        numStageGroups = 2;
    }

    public InscriptionTableTileEntity(World world) {
        this();
        this.world = world;
    }

    public InscriptionTableTileEntity() {
        this(ModTileEntities.INSCRIPTION_TABLE.get());
    }

    private static List<StringNBT> splitStoryPartIntoPages(String storyPart) {
        ArrayList<StringNBT> parts = new ArrayList<>();
        String[] words = storyPart.split(" ");
        String currentPage = "";
        for (String word : words) {
            if (word.contains(NEWPAGE)) {
                int idx = word.indexOf(NEWPAGE);
                String preNewPage = word.substring(0, idx);
                String postNewPage = word.substring(idx + NEWPAGE.length());
                while (preNewPage.endsWith("\n")) preNewPage = preNewPage.substring(0, preNewPage.lastIndexOf('\n'));
                if (getStringOverallLength(currentPage + preNewPage) > 256) {
                    parts.add(StringNBT.valueOf(currentPage));
                    currentPage = preNewPage.trim();
                } else currentPage += " " + preNewPage.trim();
                parts.add(StringNBT.valueOf(currentPage));
                while (postNewPage.startsWith("\n")) postNewPage = postNewPage.replaceFirst("\n", "");
                currentPage = postNewPage.trim();
                continue;
            }
            if (getStringOverallLength(currentPage + word) > 256) {
                parts.add(StringNBT.valueOf(currentPage));
                currentPage = word;
                if (getStringOverallLength(currentPage) > 256) {
                    int length = 0;
                    int index = 0;
                    for (int i = 0; i < currentPage.length(); i++) {
                        char c = currentPage.charAt(i);
                        if (c == '\n') length += length % 19;
                        else length++;
                        index++;
                    }
                    if (length <= 255) index--;
                    currentPage = currentPage.substring(0, index);
                    parts.add(StringNBT.valueOf(currentPage));
                    currentPage = "";
                }
                continue;
            }
            if (currentPage.equals("")) currentPage = word.trim();
            else currentPage += " " + word;
        }
        parts.add(StringNBT.valueOf(currentPage));
        return parts;
    }

    private static int getStringOverallLength(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\n') length += length % 19;
            else length++;
        }
        return length;
    }

    private void resetModifierCount() {
        modifierCount.clear();
        for (SpellModifiers modifier : SpellModifiers.values()) {
            modifierCount.put(modifier, 0);
        }
    }

    public List<AbstractSpellPart> getCurrentRecipe() {
        return this.currentRecipe;
    }

    @Override
    public int getSizeInventory() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        return inscriptionTableItemStacks.isEmpty() || inscriptionTableItemStacks.stream().allMatch(ItemStack::isEmpty);
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return inscriptionTableItemStacks.get(index);
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (!inscriptionTableItemStacks.get(index).isEmpty()) {
            if (inscriptionTableItemStacks.get(index).getCount() <= count) {
                ItemStack itemstack = inscriptionTableItemStacks.get(index);
                inscriptionTableItemStacks.set(index, ItemStack.EMPTY);
                return itemstack;
            }
            ItemStack itemstack1 = inscriptionTableItemStacks.get(index).split(count);
            if (inscriptionTableItemStacks.get(index).getCount() == 0) {
                inscriptionTableItemStacks.set(index, ItemStack.EMPTY);
            }
            return itemstack1;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (inscriptionTableItemStacks.get(index).isEmpty()) {
            ItemStack itemstack = inscriptionTableItemStacks.get(index);
            inscriptionTableItemStacks.set(index, ItemStack.EMPTY);
            return itemstack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inscriptionTableItemStacks.set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (player.world.getTileEntity(pos) != this) {
            return false;
        }
        return player.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) <= 64D;
    }

    public boolean isInUse(PlayerEntity player) {
        return currentPlayerUsing != null && currentPlayerUsing.getEntityId() != player.getEntityId();
    }

    public void setInUse(PlayerEntity player) {
        this.currentPlayerUsing = player;
        if (!world.isRemote) {
            markDirty();
            //world.markAndNotifyBlock(pos, world.getChunkFromBlockCoords(pos), world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }

    public PlayerEntity getCurrentPlayerUsing() {
        return this.currentPlayerUsing;
    }

    @Override
    public void openInventory(PlayerEntity player) {
    }

    @Override
    public void closeInventory(PlayerEntity player) {
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return false;
    }

    @Override
    public void tick() {
        if (world.getBlockState(pos).getBlock() != ModBlocks.INSCRIPTION_TABLE.get()) {
            this.remove();
            return;
        }
        if (world.isRemote && getUpgradeState() >= 3)
            candleUpdate();
        if (this.numStageGroups > MAX_STAGE_GROUPS)
            this.numStageGroups = MAX_STAGE_GROUPS;
        if (!world.isRemote) {
            this.world.setBlockState(pos, world.getBlockState(pos).with(InscriptionTableBlock.TIER, MathHelper.clamp(getUpgradeState(), 0, 3)), 2);
        }
        this.markDirty();
    }

    public int getUpgradeState() {
        return this.numStageGroups - 2;
    }

    private void candleUpdate() {
//        if (isRenderingLeft()){
//            if (ticksToNextParticle == 0 || ticksToNextParticle == 15){
//
//                double particleX = 0;
//                double particleZ = 0;
//
//                switch (world.getBlockState(pos).getValue(BlockInscriptionTable.FACING)){
//                    case SOUTH:
//                        particleX = this.pos.getX() + 0.15;
//                        particleZ = this.getPos().getZ() + 0.22;
//                        break;
//                    case NORTH:
//                        particleX = this.getPos().getX() + 0.22;
//                        particleZ = this.getPos().getZ() + 0.85;
//                        break;
//                    case WEST:
//                        particleX = this.getPos().getX() + 0.78;
//                        particleZ = this.getPos().getZ() + 0.85;
//                        break;
//                    case EAST:
//                        particleX = this.getPos().getX() + 0.79;
//                        particleZ = this.getPos().getZ() + 0.15;
//                        break;
//                }
//
//                ticksToNextParticle = 30;
//                AMParticle effect = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "fire", particleX, getPos().getY() + 1.32, particleZ);
//                if (effect != null){
//                    effect.setParticleScale(0.025f, 0.1f, 0.025f);
//                    effect.AddParticleController(new ParticleHoldPosition(effect, 29, 1, false));
//                    effect.setIgnoreMaxAge(false);
//                    effect.setMaxAge(400);
//                }
//
//                if (world.rand.nextInt(100) > 80){
//                    AMParticle smoke = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "smoke", particleX, getPos().getY() + 1.4, particleZ);
//                    if (smoke != null){
//                        smoke.setParticleScale(0.025f);
//                        smoke.AddParticleController(new ParticleFloatUpward(smoke, 0.01f, 0.01f, 1, false));
//                        smoke.setIgnoreMaxAge(false);
//                        smoke.setMaxAge(20 + world.rand.nextInt(10));
//                    }
//                }
//            }
//            if (ticksToNextParticle == 10 || ticksToNextParticle == 25){
//
//
//                double particleX = 0;
//                double particleZ = 0;
//
//                switch (world.getBlockState(pos).getValue(BlockInscriptionTable.FACING)){
//                    case SOUTH:
//                        particleX = this.getPos().getX() + 0.59;
//                        particleZ = this.getPos().getZ() - 0.72;
//                        break;
//                    case NORTH:
//                        particleX = this.getPos().getX() - 0.72;
//                        particleZ = this.getPos().getZ() + 0.41;
//                        break;
//                    case EAST:
//                        particleX = this.getPos().getX() + 0.41;
//                        particleZ = this.getPos().getZ() + 1.72;
//                        break;
//                    case WEST:
//                        particleX = this.getPos().getX() + 1.72;
//                        particleZ = this.getPos().getZ() + 0.41;
//                        break;
//                }
//
//                AMParticle effect = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "fire", particleX, getPos().getY() + 1.26, particleZ);
//                if (effect != null){
//                    effect.setParticleScale(0.025f, 0.1f, 0.025f);
//                    effect.AddParticleController(new ParticleHoldPosition(effect, 29, 1, false));
//                    effect.setIgnoreMaxAge(false);
//                    effect.setMaxAge(400);
//                }
//
//                if (world.rand.nextInt(100) > 80){
//                    AMParticle smoke = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "smoke", particleX, getPos().getY() + 1.4, particleZ);
//                    if (smoke != null){
//                        smoke.setParticleScale(0.025f);
//                        smoke.AddParticleController(new ParticleFloatUpward(smoke, 0.01f, 0.01f, 1, false));
//                        smoke.setIgnoreMaxAge(false);
//                        smoke.setMaxAge(20 + world.rand.nextInt(10));
//                    }
//                }
//            }
//        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getPos(), 300, this.write(new CompoundNBT()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.parseTagCompound(pkt.getNbtCompound());
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        clearCurrentRecipe();
        parseTagCompound(compound);
    }

    public void clearCurrentRecipe() {
        this.currentRecipe.clear();
        for (List<AbstractSpellPart> group : shapeGroups)
            group.clear();
        currentSpellName = "";
        currentSpellIsReadOnly = false;
    }

    private void parseTagCompound(CompoundNBT nbt) {
//        ArsMagicaLegacy.LOGGER.debug("parse: {}",nbt);
        ItemStackHelper.loadAllItems(nbt, inscriptionTableItemStacks);
        shapeGroups.clear();
        ListNBT shapeGroups = nbt.getList(SHAPE_GROUPS_KEY, Constants.NBT.TAG_LIST);
        for (net.minecraft.nbt.INBT shapeGroup : shapeGroups) {
            ListNBT tmplist = (ListNBT) shapeGroup;
            ArrayList<AbstractSpellPart> parts = new ArrayList<>();
            for (int j = 0; j < tmplist.size(); j++) {
                CompoundNBT tmp = tmplist.getCompound(j);
                parts.add(tmp.getInt(SLOT_KEY), RegistryHandler.getSpellPartRegistry().getValue(new ResourceLocation(tmp.getString(ID_KEY))));
            }
            this.shapeGroups.add(parts);
        }
        currentRecipe.clear();
        ListNBT recipe = nbt.getList(CURRENT_RECIPE_KEY, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < recipe.size(); i++) {
            CompoundNBT tmp = recipe.getCompound(i);
            currentRecipe.add(tmp.getInt(SLOT_KEY), RegistryHandler.getSpellPartRegistry().getValue(new ResourceLocation(tmp.getString(ID_KEY))));
        }
        this.numStageGroups = Math.max(nbt.getInt(NUM_SHAPE_GROUP_SLOTS_KEY), 2);
        this.currentSpellName = nbt.getString(CURRENT_SPELL_NAME_KEY);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, inscriptionTableItemStacks);
        ListNBT recipe = new ListNBT();
        for (int i = 0; i < currentRecipe.size(); i++) {
            CompoundNBT tmp = new CompoundNBT();
            tmp.putInt(SLOT_KEY, i);
            tmp.putString(ID_KEY, SpellRegistry.getSkillFromPart(currentRecipe.get(i)).getID());
            recipe.add(tmp);
        }
        ListNBT shapeGroups = new ListNBT();
        for (List<AbstractSpellPart> parts : this.shapeGroups) {
            ListNBT list = new ListNBT();
            for (int i = 0; i < parts.size(); i++) {
                CompoundNBT tmp = new CompoundNBT();
                tmp.putInt(SLOT_KEY, i);
                tmp.putString(ID_KEY, SpellRegistry.getSkillFromPart(parts.get(i)).getID());
                list.add(tmp);
            }
            shapeGroups.add(list);
        }
        compound.put(SHAPE_GROUPS_KEY, shapeGroups);
        compound.put(CURRENT_RECIPE_KEY, recipe);
        compound.putInt(NUM_SHAPE_GROUP_SLOTS_KEY, this.numStageGroups);
        compound.putString(CURRENT_SPELL_NAME_KEY, this.currentSpellName);
        return compound;
    }

    public String getSpellName() {
        return this.currentSpellName != null ? this.currentSpellName : "";
    }

    public void setSpellName(String name) {
        this.currentSpellName = name;
        sendDataToServer();
    }

    public void reverseEngineerSpell(ItemStack stack) {
        this.currentRecipe.clear();
        for (List<AbstractSpellPart> group : shapeGroups) {
            group.clear();
        }
        currentSpellName = "";
        this.currentSpellName = stack.getDisplayName().getFormattedText();
        int numStages = SpellUtil.stageNum(stack);
        for (int i = 0; i < numStages; i++) {
            SpellShape shape = SpellUtil.getShape(stack, i);
            this.currentRecipe.add(shape);
            List<SpellComponent> components = SpellUtil.getComponents(stack, i);
            this.currentRecipe.addAll(components);
            List<SpellModifier> modifiers = SpellUtil.getModifiers(stack, i);
            this.currentRecipe.addAll(modifiers);
        }
        int numShapeGroups = SpellUtil.numShapeGroups(stack);
        for (int i = 0; i < numShapeGroups; i++) {
            List<AbstractSpellPart> parts = SpellUtil.getGroupParts(stack, i);
            for (AbstractSpellPart partID : parts) {
                if (partID != null)
                    this.shapeGroups.get(i).add(partID);
            }
        }
        currentSpellIsReadOnly = true;
    }

    public int getShapeGroupSize(int groupIndex) {
        if (groupIndex > this.shapeGroups.size() || groupIndex < 0)
            return 0;
        return this.shapeGroups.get(groupIndex).size();
    }

    public AbstractSpellPart getShapeGroupPartAt(int groupIndex, int index) {
        return this.shapeGroups.get(groupIndex).get(index);
    }

    public void incrementUpgradeState() {
        this.numStageGroups++;
        if (!world.isRemote) {
            List<ServerPlayerEntity> players = world.getEntitiesWithinAABB(ServerPlayerEntity.class, new AxisAlignedBB(pos).expand(256, 256, 256));
            for (ServerPlayerEntity player : players) {
                player.connection.sendPacket(getUpdatePacket());
            }
        }
    }

    public ItemStack writeRecipeAndDataToBook(ItemStack bookstack, PlayerEntity player, String title) {
        this.world = player.world;
//        ArsMagicaLegacy.LOGGER.debug("world: {}", world != null ? world.isRemote : null);
//        ArsMagicaLegacy.LOGGER.debug(currentRecipe);
        if (bookstack.getItem() == Items.WRITTEN_BOOK && this.currentRecipe != null) {
            if (!currentRecipeIsValid().valid)
                return bookstack;
            if (!bookstack.hasTag())
                bookstack.setTag(new CompoundNBT());
            else if (bookstack.getTag().getBoolean("spellFinalized"))
                return bookstack;
            SpellIngredientList materialsList = new SpellIngredientList();
            materialsList.add(new ItemStackSpellIngredient(new ItemStack(ModItems.RUNE.get(), 1)));
            List<AbstractSpellPart> allRecipeItems = new ArrayList<>();
            for (List<AbstractSpellPart> shapeGroup : shapeGroups) {
                if (shapeGroup == null || shapeGroup.size() == 0)
                    continue;
                allRecipeItems.addAll(shapeGroup);
            }
            allRecipeItems.addAll(currentRecipe);
            for (AbstractSpellPart part : allRecipeItems) {
                if (part == null) {
                    ArsMagicaLegacy.LOGGER.error("Unable to write recipe to book. Recipe part is null!");
                    return bookstack;
                }
                ISpellIngredient[] recipeItems = ArsMagicaAPI.getSpellRecipeManager().getRecipe(part.getRegistryName());
                SpellRecipeItemsEvent event = new SpellRecipeItemsEvent(SpellRegistry.getSkillFromPart(part).getID(), recipeItems);
                MinecraftForge.EVENT_BUS.post(event);
                recipeItems = event.recipeItems;
                if (recipeItems == null) {
                    ArsMagicaLegacy.LOGGER.error("Unable to write recipe to book. Recipe items are null for part {}!", SpellRegistry.getSkillFromPart(part).getName().getUnformattedComponentText());
                    return bookstack;
                }
                materialsList.addAll(Arrays.asList(recipeItems));
            }
            materialsList.add(new ItemStackSpellIngredient(new ItemStack(ModItems.SPELL_PARCHMENT.get(), 1)));
            StringBuilder sb = new StringBuilder();
            int sgCount = 0;
            String[][] shapeGroupCombos = new String[shapeGroups.size()][];
            for (List<AbstractSpellPart> shapeGroup : shapeGroups) {
                if (shapeGroup.isEmpty()) continue;
                sb.append("Shape Group ").append(sgCount++).append("\n\n");
                Iterator<AbstractSpellPart> it = shapeGroup.iterator();
                shapeGroupCombos[sgCount - 1] = spellPartListToStringBuilder(it, sb, " -");
                sb.append(NEWPAGE);
            }
            sb.append("Combination:\n\n");
            Iterator<AbstractSpellPart> it = currentRecipe.iterator();
            String[] outputData = spellPartListToStringBuilder(it, sb, null);
            List<StringNBT> pages = splitStoryPartIntoPages(sb.toString());
            sb = new StringBuilder();
            sb.append("Materials List:\n\n");
            sb.append(materialsList.getTooltip().stream()
                    .map(ITextComponent::getFormattedText)
                    .collect(Collectors.joining("\n")));
            pages.addAll(splitStoryPartIntoPages(sb.toString()));
//            ArsMagicaLegacy.LOGGER.debug("before write: {}", pages);
            sb = new StringBuilder();
            sb.append("Affinity Breakdown:\n\n");
            HashMap<Affinity, Integer> affinityData = new HashMap<>();
            int cpCount = 0;
            for (AbstractSpellPart part : currentRecipe) {
                if (part instanceof SpellComponent) {
                    Set<Affinity> aff = ((SpellComponent) part).getAffinity();
                    for (Affinity affinity : aff) {
                        int qty = 1;
                        if (affinityData.containsKey(affinity)) {
                            qty = 1 + affinityData.get(affinity);
                        }
                        affinityData.put(affinity, qty);
                    }
                    cpCount++;
                }
            }
            ValueComparator vc = new ValueComparator(affinityData);
            TreeMap<Affinity, Integer> sorted = new TreeMap<>(vc);
            sorted.putAll(affinityData);
            for (Affinity aff : sorted.keySet()) {
                float pct = (float) sorted.get(aff) / (float) cpCount * 100f;
                sb.append(String.format("%s: %.2f%%", aff.getName().getFormattedText(), pct));
                sb.append("\n");
            }
            pages.addAll(splitStoryPartIntoPages(sb.toString()));
            ListNBT pageslist = new ListNBT();
            for (StringNBT page : pages) {
                StringNBT newPage = StringNBT.valueOf("{\"text\":\"" + page.getString() + "\"}");
                pageslist.add(newPage);
            }
            bookstack.getTag().put("pages", pageslist);
            if (currentSpellName.equals("")) currentSpellName = "Spell Recipe";
            if (bookstack.getTag() == null) return bookstack;
            bookstack.getTag().put("title", StringNBT.valueOf(new StringTextComponent(currentSpellName).getUnformattedComponentText()));
            bookstack.setDisplayName(new StringTextComponent(currentSpellName));
            bookstack.getTag().put("author", StringNBT.valueOf(player.getName().getFormattedText()));
//            ArsMagicaLegacy.LOGGER.debug("after finalize: {}", bookstack.getTag());
            bookstack.getTag().put("spell_combo", materialsList.serializeNBT());
            ListNBT list = new ListNBT();
            list.addAll(Arrays.stream(outputData).map(StringNBT::valueOf).collect(Collectors.toList()));
            bookstack.getTag().put("output_combo", list);
            bookstack.getTag().putInt("numShapeGroups", sgCount);
            int i = 0;
            for (String[] sgArray : shapeGroupCombos) {
                if (sgArray == null) continue;
                ListNBT nbt = new ListNBT();
                nbt.addAll(Arrays.stream(sgArray).map(StringNBT::valueOf).collect(Collectors.toList()));
                bookstack.getTag().put("shapeGroupCombo_" + i++, nbt);
            }
            bookstack.getTag().putString("spell_mod_version", ArsMagicaLegacy.instance.getVersion());
            this.currentRecipe.clear();
            for (List<AbstractSpellPart> group : shapeGroups)
                group.clear();
            this.currentSpellName = "";
            bookstack.getTag().putBoolean("spellFinalized", true);
            //world.playSound(getPos().getX(), getPos().getY(), getPos().getZ(), "arsmagica2:misc.inscriptiontable.takebook", 1, 1, true);
            this.markDirty();
            //world.markAndNotifyBlock(pos, world.getChunkFromBlockCoords(pos), world.getBlockState(pos), world.getBlockState(pos), 2);
        }
        return bookstack;
    }

    private String[] spellPartListToStringBuilder(Iterator<AbstractSpellPart> it, StringBuilder sb, String prefix) {
        ArrayList<String> outputCombo = new ArrayList<>();
        while (it.hasNext()) {
            AbstractSpellPart part = it.next();
            String displayName = SpellRegistry.getSkillFromPart(part).getName().getFormattedText();
            if (prefix != null) {
                sb.append(prefix).append(displayName).append("\n");
            } else {
                if (part instanceof SpellShape) {
                    sb.append(displayName).append("\n");
                } else {
                    sb.append("-").append(displayName).append("\n");
                }
            }
            outputCombo.add(part.getRegistryName().toString());
        }
        return outputCombo.toArray(new String[0]);
    }

    public SpellValidator.ValidationResult currentRecipeIsValid() {
        List<List<AbstractSpellPart>> segmented = SpellValidator.splitToStages(currentRecipe);
        return SpellValidator.instance.spellDefIsValid(shapeGroups, segmented);
    }

    public void handleUpdatePacket(byte[] data) {
        /*if (this.world == null)
            return;
        AMDataReader rdr = new AMDataReader(data);
        switch (rdr.ID){
            case FULL_UPDATE:
                if (!rdr.getBoolean()){
                    Entity e = this.world.getEntityByID(rdr.getInt());
                    if (e instanceof PlayerEntity){
                        PlayerEntity player = (PlayerEntity)e;
                        this.setInUse(player);
                    }else{
                        this.setInUse(null);
                    }
                }else{
                    this.setInUse(null);
                }

                currentRecipe.clear();
                int partLength = rdr.getInt();
                for (int i = 0; i < partLength; i++){
                    AbstractSpellPart spellPart = ArsMagicaLegacyAPI.getSpellPartRegistry().getValue(new ResourceLocation(rdr.getString()));
                    if (spellPart != null)
                        this.currentRecipe.add(spellPart);
                }

                this.shapeGroups.clear();
                int numGroups = rdr.getInt();
                for (int i = 0; i < numGroups; i++){
                    ArrayList<AbstractSpellPart> group = new ArrayList<>();
                    String[] partData = rdr.getStringArray();
                    for (String n : partData){
                        Skill part = ArsMagicaLegacyAPI.getSkillRegistry().getValue(n);
                        AbstractSpellPart spellPart = ArsMagicaAPI.getSpellRegistry().getObject(part.getRegistryName());
                        if (spellPart != null)
                            group.add(spellPart);
                    }
                    this.shapeGroups.add(group);
                }

                countModifiers();
                this.currentSpellName = rdr.getString();
                this.currentSpellIsReadOnly = rdr.getBoolean();
                break;
            case MAKE_SPELL:
                int entityID = rdr.getInt();
                EntityPlayer player = (EntityPlayer)world.getEntityByID(entityID);
                if (player != null){
                    createSpellForPlayer(player);
                }
                break;
            case RESET_NAME:
                entityID = rdr.getInt();
                player = (EntityPlayer)world.getEntityByID(entityID);
                if (player != null){
                    ((ContainerInscriptionTable)player.openContainer).resetSpellNameAndIcon();
                }
                break;
        }*/
    }

    private byte[] getUpdatePacketForServer() {
        /*AMDataWriter writer = new AMDataWriter();
        writer.add(FULL_UPDATE);
        writer.add(this.currentPlayerUsing == null);
        if (this.currentPlayerUsing != null) writer.add(this.currentPlayerUsing.getEntityId());

        writer.add(this.currentRecipe.size());
        for (int i = 0; i < this.currentRecipe.size(); i++){
            writer.add(ArsMagicaAPI.getSkillRegistry().getId(this.currentRecipe.get(i).getRegistryName()));
        }

        writer.add(this.shapeGroups.size());
        for (ArrayList<AbstractSpellPart> shapeGroup : this.shapeGroups){
            int[] groupData = new int[shapeGroup.size()];
            for (int i = 0; i < shapeGroup.size(); i++){
                groupData[i] = ArsMagicaAPI.getSkillRegistry().getId(shapeGroup.get(i).getRegistryName());
            }
            writer.add(groupData);
        }

        writer.add(currentSpellName);
        writer.add(currentSpellIsReadOnly);

        return writer.generate();*/
        return null;
    }

    private void sendDataToServer() {
        CompoundNBT nbt = this.write(new CompoundNBT());
//        ArsMagicaLegacy.LOGGER.debug("send: {}",nbt);
        NetworkHandler.INSTANCE.sendToServer(new InscriptionTablePacket(this.getPos(), nbt));
    }

    public void addSpellPartToStageGroup(int groupIndex, AbstractSpellPart part) {
        List<AbstractSpellPart> group = this.shapeGroups.get(groupIndex);
        if (!currentSpellIsReadOnly && group.size() < 4 && !(part instanceof SpellComponent)) {
            group.add(part);
            if (world.isRemote)
                sendDataToServer();
            countModifiers();
        }
    }

    public void removeSpellPartFromStageGroup(int index, int groupIndex) {
        List<AbstractSpellPart> group = this.shapeGroups.get(groupIndex);
        if (!currentSpellIsReadOnly) {
            group.remove(index);
            if (world.isRemote)
                sendDataToServer();
            countModifiers();
        }
    }

    public void removeMultipleSpellPartsFromStageGroup(int startIndex, int length, int groupIndex) {
        List<AbstractSpellPart> group = this.shapeGroups.get(groupIndex);
        if (!currentSpellIsReadOnly) {
            for (int i = 0; i <= length; i++)
                group.remove(startIndex);
            countModifiers();
            if (world.isRemote)
                sendDataToServer();
        }
    }

    public void addSpellPart(AbstractSpellPart part) {
        if (!currentSpellIsReadOnly && currentRecipe.size() < 16) {
            currentRecipe.add(part);
            if (world.isRemote)
                sendDataToServer();
            countModifiers();
        }
    }

    public void removeSpellPart(int index) {
        if (!currentSpellIsReadOnly) {
            currentRecipe.remove(index);
            if (world.isRemote)
                sendDataToServer();
            countModifiers();
        }
    }

    public void removeMultipleSpellParts(int startIndex, int length) {
        if (!currentSpellIsReadOnly) {
            for (int i = 0; i <= length; i++)
                getCurrentRecipe().remove(startIndex);
            countModifiers();
            if (world.isRemote)
                sendDataToServer();
        }
    }

    public int getNumStageGroups() {
        return numStageGroups;
    }

    private void countModifiers() {
        resetModifierCount();
        for (List<AbstractSpellPart> shapeGroup : shapeGroups) countModifiersInList(shapeGroup);
        List<List<AbstractSpellPart>> stages = SpellValidator.splitToStages(currentRecipe);
        if (stages.size() == 0) return;
        for (List<AbstractSpellPart> currentStage : stages) countModifiersInList(currentStage);
        //ArrayList<AbstractSpellPart> currentStage = stages.get(stages.size() - 1);
        //countModifiersInList(currentStage);
    }

    private void countModifiersInList(List<AbstractSpellPart> currentStage) {
        for (AbstractSpellPart part : currentStage) {
            if (part instanceof SpellModifier) {
                EnumSet<SpellModifiers> modifiers = ((SpellModifier) part).getAspectsModified();
                for (SpellModifiers modifier : modifiers) {
                    int count = modifierCount.get(modifier) + 1;
                    modifierCount.put(modifier, count);
                }
            }
        }
    }

    public int getModifierCount(SpellModifiers modifier) {
        return modifierCount.get(modifier);
    }

    public void createSpellForPlayer(PlayerEntity player) {
        /*if (world.isRemote){
            AMDataWriter writer = new AMDataWriter();
            writer.add(getPos().getX());
            writer.add(getPos().getY());
            writer.add(getPos().getZ());
            writer.add(MAKE_SPELL);
            writer.add(player.getEntityId());
            AMNetHandler.INSTANCE.sendPacketToServer(AMPacketIDs.INSCRIPTION_TABLE_UPDATE, writer.generate());
        }else{*/
        List<Pair<List<AbstractSpellPart>, CompoundNBT>> shapeGroupSetup = new ArrayList<>();
        Pair<List<AbstractSpellPart>, CompoundNBT> curRecipeSetup = new Pair<>(currentRecipe, new CompoundNBT());
        for (List<AbstractSpellPart> arr : shapeGroups) {
            shapeGroupSetup.add(new Pair<>(arr, new CompoundNBT()));
        }
        ItemStack stack = SpellUtil.makeSpellStack(shapeGroupSetup, curRecipeSetup);
        stack.getTag().putString("suggestedName", currentSpellName);
        player.inventory.addItemStackToInventory(stack);
        //}
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public boolean currentSpellDefIsReadOnly() {
        return this.currentSpellIsReadOnly;
    }

    public void resetSpellNameAndIcon(ItemStack stack, PlayerEntity player) {
        /*if (world.isRemote){
            AMDataWriter writer = new AMDataWriter();
            writer.add(getPos().getX());
            writer.add(getPos().getY());
            writer.add(getPos().getZ());
            writer.add(RESET_NAME);
            writer.add(player.getEntityId());
            AMNetHandler.INSTANCE.sendPacketToServer(AMPacketIDs.INSCRIPTION_TABLE_UPDATE, writer.generate());
        }
        stack.setItemDamage(0);*/
        stack.clearCustomName();
    }

    static class ValueComparator implements Comparator<Affinity> {
        Map<Affinity, Integer> base;

        ValueComparator(Map<Affinity, Integer> base) {
            this.base = base;
        }

        @Override
        public int compare(Affinity a, Affinity b) {
            Integer x = base.get(a);
            Integer y = base.get(b);
            if (x.equals(y)) {
                return a.compareTo(b);
            }
            return x.compareTo(y);
        }
    }
}
