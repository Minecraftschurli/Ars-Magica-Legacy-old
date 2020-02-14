package minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable;

import javafx.util.*;
import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.event.*;
import minecraftschurli.arsmagicalegacy.api.network.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.lore.*;
import minecraftschurli.arsmagicalegacy.objects.spell.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.util.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

import static minecraftschurli.arsmagicalegacy.lore.Story.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
public class InscriptionTableTileEntity extends TileEntity implements IInventory, ITickableTileEntity {
    public static final int MAX_STAGE_GROUPS = 5;
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
        for (int i = 0; i < MAX_STAGE_GROUPS; ++i) {
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

    private void resetModifierCount() {
        modifierCount.clear();
        for (SpellModifiers modifier : SpellModifiers.values()) {
            modifierCount.put(modifier, 0);
        }
    }

    public List<AbstractSpellPart> getCurrentRecipe() {
        return this.currentRecipe;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        return inscriptionTableItemStacks.isEmpty() || inscriptionTableItemStacks.stream().allMatch(ItemStack::isEmpty);
    }

    /**
     * Returns the stack in the given slot.
     *
     * @param index
     */
    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return inscriptionTableItemStacks.get(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     *
     * @param index
     * @param count
     */
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

    /**
     * Removes a stack from the given slot and returns it.
     *
     * @param index
     */
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

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param index
     * @param stack
     */
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

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     *
     * @param player
     */
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
        if (!this.world.isRemote) {
            this.markDirty();
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
        /*ticksToNextParticle--; //TODO @IchHabeHunger54

        if (isRenderingLeft()){
            if (ticksToNextParticle == 0 || ticksToNextParticle == 15){

                double particleX = 0;
                double particleZ = 0;

                switch (world.getBlockState(pos).getValue(BlockInscriptionTable.FACING)){
                    case SOUTH:
                        particleX = this.pos.getX() + 0.15;
                        particleZ = this.getPos().getZ() + 0.22;
                        break;
                    case NORTH:
                        particleX = this.getPos().getX() + 0.22;
                        particleZ = this.getPos().getZ() + 0.85;
                        break;
                    case WEST:
                        particleX = this.getPos().getX() + 0.78;
                        particleZ = this.getPos().getZ() + 0.85;
                        break;
                    case EAST:
                        particleX = this.getPos().getX() + 0.79;
                        particleZ = this.getPos().getZ() + 0.15;
                        break;
                }

                ticksToNextParticle = 30;
                AMParticle effect = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "fire", particleX, getPos().getY() + 1.32, particleZ);
                if (effect != null){
                    effect.setParticleScale(0.025f, 0.1f, 0.025f);
                    effect.AddParticleController(new ParticleHoldPosition(effect, 29, 1, false));
                    effect.setIgnoreMaxAge(false);
                    effect.setMaxAge(400);
                }

                if (world.rand.nextInt(100) > 80){
                    AMParticle smoke = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "smoke", particleX, getPos().getY() + 1.4, particleZ);
                    if (smoke != null){
                        smoke.setParticleScale(0.025f);
                        smoke.AddParticleController(new ParticleFloatUpward(smoke, 0.01f, 0.01f, 1, false));
                        smoke.setIgnoreMaxAge(false);
                        smoke.setMaxAge(20 + world.rand.nextInt(10));
                    }
                }
            }
            if (ticksToNextParticle == 10 || ticksToNextParticle == 25){


                double particleX = 0;
                double particleZ = 0;

                switch (world.getBlockState(pos).getValue(BlockInscriptionTable.FACING)){
                    case SOUTH:
                        particleX = this.getPos().getX() + 0.59;
                        particleZ = this.getPos().getZ() - 0.72;
                        break;
                    case NORTH:
                        particleX = this.getPos().getX() - 0.72;
                        particleZ = this.getPos().getZ() + 0.41;
                        break;
                    case EAST:
                        particleX = this.getPos().getX() + 0.41;
                        particleZ = this.getPos().getZ() + 1.72;
                        break;
                    case WEST:
                        particleX = this.getPos().getX() + 1.72;
                        particleZ = this.getPos().getZ() + 0.41;
                        break;
                }

                AMParticle effect = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "fire", particleX, getPos().getY() + 1.26, particleZ);
                if (effect != null){
                    effect.setParticleScale(0.025f, 0.1f, 0.025f);
                    effect.AddParticleController(new ParticleHoldPosition(effect, 29, 1, false));
                    effect.setIgnoreMaxAge(false);
                    effect.setMaxAge(400);
                }

                if (world.rand.nextInt(100) > 80){
                    AMParticle smoke = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "smoke", particleX, getPos().getY() + 1.4, particleZ);
                    if (smoke != null){
                        smoke.setParticleScale(0.025f);
                        smoke.AddParticleController(new ParticleFloatUpward(smoke, 0.01f, 0.01f, 1, false));
                        smoke.setIgnoreMaxAge(false);
                        smoke.setMaxAge(20 + world.rand.nextInt(10));
                    }
                }
            }
        }*/
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
                parts.add(tmp.getInt(SLOT_KEY), ArsMagicaAPI.getSpellPartRegistry().getValue(new ResourceLocation(tmp.getString(ID_KEY))));
            }
            this.shapeGroups.add(parts);
        }
        currentRecipe.clear();
        ListNBT recipe = nbt.getList(CURRENT_RECIPE_KEY, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < recipe.size(); i++) {
            CompoundNBT tmp = recipe.getCompound(i);
            currentRecipe.add(tmp.getInt(SLOT_KEY), ArsMagicaAPI.getSpellPartRegistry().getValue(new ResourceLocation(tmp.getString(ID_KEY))));
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
        int numStages = SpellUtils.numStages(stack);

        for (int i = 0; i < numStages; ++i) {
            SpellShape shape = SpellUtils.getShapeForStage(stack, i);
            this.currentRecipe.add(shape);
            List<SpellComponent> components = SpellUtils.getComponentsForStage(stack, i);
            this.currentRecipe.addAll(components);
            List<SpellModifier> modifiers = SpellUtils.getModifiersForStage(stack, i);
            this.currentRecipe.addAll(modifiers);
        }

        int numShapeGroups = SpellUtils.numShapeGroups(stack);
        for (int i = 0; i < numShapeGroups; ++i) {
            List<AbstractSpellPart> parts = SpellUtils.getShapeGroupParts(stack, i);
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
        if (!this.world.isRemote) {
            List<ServerPlayerEntity> players = this.world.getEntitiesWithinAABB(ServerPlayerEntity.class, new AxisAlignedBB(pos).expand(256, 256, 256));
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
            else if (bookstack.getTag().getBoolean("spellFinalized")) //don't overwrite a completed spell
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

                ISpellIngredient[] recipeItems = ArsMagicaLegacy.getSpellRecipeManager().getRecipe(part.getRegistryName());
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
                sb.append("Shape Group ").append(++sgCount).append("\n\n");
                Iterator<AbstractSpellPart> it = shapeGroup.iterator();
                shapeGroupCombos[sgCount - 1] = spellPartListToStringBuilder(it, sb, " -");
                sb.append(NEWPAGE);
            }

            sb.append("Combination:\n\n");
            Iterator<AbstractSpellPart> it = currentRecipe.iterator();
            String[] outputData = spellPartListToStringBuilder(it, sb, null);

            List<StringNBT> pages = Story.splitStoryPartIntoPages(sb.toString());

            sb = new StringBuilder();
            sb.append("Materials List:\n\n");
            sb.append(materialsList.getTooltip().stream()
                    .map(ITextComponent::getFormattedText)
                    .collect(Collectors.joining("\n")));

            pages.addAll(Story.splitStoryPartIntoPages(sb.toString()));

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
            for (Affinity aff : sorted.keySet()){
                float pct = (float)sorted.get(aff) / (float)cpCount * 100f;
                sb.append(String.format("%s: %.2f%%", aff.getName().getFormattedText(), pct));
                sb.append("\n");
            }
            pages.addAll(Story.splitStoryPartIntoPages(sb.toString()));
            Story.writePartToNBT(bookstack.getTag(), pages);
//            ArsMagicaLegacy.LOGGER.debug("after write: {}", bookstack.getTag());

            if (currentSpellName.equals(""))
                currentSpellName = "Spell Recipe";
            Story.finalizeStory(bookstack, new StringTextComponent(currentSpellName), player.getName().getFormattedText());
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
//            ArsMagicaLegacy.LOGGER.debug("fin: {}", bookstack.getTag());


            //world.playSound(getPos().getX(), getPos().getY(), getPos().getZ(), "arsmagica2:misc.inscriptiontable.takebook", 1, 1, true);
            this.markDirty();
            //world.markAndNotifyBlock(pos, world.getChunkFromBlockCoords(pos), world.getBlockState(pos), world.getBlockState(pos), 2);
        }
        return bookstack;
    }

    static class ValueComparator implements Comparator<Affinity>{

        Map<Affinity, Integer> base;

        ValueComparator(Map<Affinity, Integer> base){
            this.base = base;
        }

        @Override
        public int compare(Affinity a, Affinity b){
            Integer x = base.get(a);
            Integer y = base.get(b);
            if (x.equals(y)){
                return a.compareTo(b);
            }
            return x.compareTo(y);
        }
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
                for (int i = 0; i < partLength; ++i){
                    AbstractSpellPart spellPart = ArsMagicaLegacyAPI.getSpellPartRegistry().getValue(new ResourceLocation(rdr.getString()));
                    if (spellPart != null)
                        this.currentRecipe.add(spellPart);
                }

                this.shapeGroups.clear();
                int numGroups = rdr.getInt();
                for (int i = 0; i < numGroups; ++i){
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
        for (int i = 0; i < this.currentRecipe.size(); ++i){
            writer.add(ArsMagicaAPI.getSkillRegistry().getId(this.currentRecipe.get(i).getRegistryName()));
        }

        writer.add(this.shapeGroups.size());
        for (ArrayList<AbstractSpellPart> shapeGroup : this.shapeGroups){
            int[] groupData = new int[shapeGroup.size()];
            for (int i = 0; i < shapeGroup.size(); ++i){
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
            if (this.world.isRemote)
                this.sendDataToServer();
            countModifiers();
        }
    }

    public void removeSpellPartFromStageGroup(int index, int groupIndex) {
        List<AbstractSpellPart> group = this.shapeGroups.get(groupIndex);
        if (!this.currentSpellIsReadOnly) {
            group.remove(index);
            if (this.world.isRemote)
                this.sendDataToServer();
            countModifiers();
        }
    }

    public void removeMultipleSpellPartsFromStageGroup(int startIndex, int length, int groupIndex) {
        List<AbstractSpellPart> group = this.shapeGroups.get(groupIndex);
        if (!currentSpellIsReadOnly) {
            for (int i = 0; i <= length; ++i)
                group.remove(startIndex);
            countModifiers();
            if (this.world.isRemote)
                this.sendDataToServer();
        }
    }

    public void addSpellPart(AbstractSpellPart part) {
        if (!currentSpellIsReadOnly && this.currentRecipe.size() < 16) {
            this.currentRecipe.add(part);
            if (this.world.isRemote)
                this.sendDataToServer();
            countModifiers();
        }
    }

    public void removeSpellPart(int index) {
        if (!this.currentSpellIsReadOnly) {
            this.currentRecipe.remove(index);
            if (this.world.isRemote)
                this.sendDataToServer();
            countModifiers();
        }
    }

    public void removeMultipleSpellParts(int startIndex, int length) {
        if (!currentSpellIsReadOnly) {
            for (int i = 0; i <= length; ++i)
                this.getCurrentRecipe().remove(startIndex);
            countModifiers();
            if (this.world.isRemote)
                this.sendDataToServer();
        }
    }

    public int getNumStageGroups() {
        return this.numStageGroups;
    }

    private void countModifiers() {

        resetModifierCount();

        for (List<AbstractSpellPart> shapeGroup : this.shapeGroups) {
            countModifiersInList(shapeGroup);
        }

        List<List<AbstractSpellPart>> stages = SpellValidator.splitToStages(currentRecipe);
        if (stages.size() == 0) return;

        for (List<AbstractSpellPart> currentStage : stages) {
            countModifiersInList(currentStage);
        }
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
        ItemStack stack = SpellUtils.createSpellStack(shapeGroupSetup, curRecipeSetup);

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
}
