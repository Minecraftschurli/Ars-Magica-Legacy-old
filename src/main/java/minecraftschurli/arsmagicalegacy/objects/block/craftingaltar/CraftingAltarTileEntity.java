package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import com.google.common.collect.ImmutableList;
import javafx.util.Pair;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.CraftingAltarStructureMaterials;
import minecraftschurli.arsmagicalegacy.api.multiblock.Structure;
import minecraftschurli.arsmagicalegacy.api.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.api.network.TEClientSyncPacket;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.SpellIngredientList;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableTileEntity;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LecternTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2019-12-13
 */
public class CraftingAltarTileEntity extends TileEntity implements ITickableTileEntity {
    private static final Supplier<BlockState> AIR = Blocks.AIR::getDefaultState;
    private static final Supplier<BlockState> WALL = () -> ModBlocks.MAGIC_WALL.lazyMap(Block::getDefaultState).get();
    private static final Supplier<BlockState> LECTERN = Blocks.LECTERN::getDefaultState;
    private static final Supplier<BlockState> LEVER = Blocks.LEVER::getDefaultState;
    private static final Supplier<BlockState> ALTAR = () -> ModBlocks.ALTAR_CORE.lazyMap(Block::getDefaultState).get();

    private static final ModelProperty<ResourceLocation> CAMO_RL = new ModelProperty<>();
    
    private final ModelDataMap modelData = new ModelDataMap.Builder().withInitial(CAMO_RL, ModBlocks.ALTAR_CORE.getId()).build();

    private int checkTimer = 0;
    private boolean multiblockState;
    private boolean powerFlag;
    private int currentStage;
    private boolean first = true;
    private BlockPos lecternPos;
    private LecternTileEntity lectern;
    private ItemStack book;

    private final AtomicReference<BlockState> cap = new AtomicReference<>();
    private final AtomicReference<BlockState> main = new AtomicReference<>();
    private final Supplier<BlockState> stairBottom1 = () -> CraftingAltarStructureMaterials.getStairForBlock(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, Direction.EAST);
    private final Supplier<BlockState> stairBottom2 = () -> CraftingAltarStructureMaterials.getStairForBlock(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, Direction.WEST);
    private final Supplier<BlockState> stairBottom3 = () -> CraftingAltarStructureMaterials.getStairForBlock(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.FACING, Direction.NORTH);
    private final Supplier<BlockState> stairBottom4 = () -> CraftingAltarStructureMaterials.getStairForBlock(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.FACING, Direction.SOUTH);
    private final Supplier<BlockState> stairBottom5 = () -> CraftingAltarStructureMaterials.getStairForBlock(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.FACING, Direction.EAST);
    private final Supplier<BlockState> stairBottom6 = () -> CraftingAltarStructureMaterials.getStairForBlock(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.FACING, Direction.WEST);
    @SuppressWarnings("unchecked")
    final Structure STRUCTURE = new CraftingAltarStructure(new Supplier[][][]{{
            {main::get, main::get, main::get, main::get, main::get},
            {main::get, main::get, main::get, main::get, main::get},
            {main::get, main::get, cap::get, main::get, main::get},
            {main::get, main::get, main::get, main::get, main::get},
            {main::get, main::get, main::get, main::get, main::get}
    }, {
            {AIR, AIR, AIR, AIR, AIR},
            {main::get, AIR, AIR, AIR, main::get},
            {WALL, AIR, AIR, AIR, WALL},
            {main::get, AIR, AIR, AIR, main::get},
            {AIR, AIR, AIR, AIR, LECTERN}
    }, {
            {AIR, AIR, AIR, AIR, AIR},
            {main::get, AIR, AIR, AIR, main::get},
            {WALL, AIR, AIR, AIR, WALL},
            {main::get, AIR, AIR, AIR, main::get},
            {LEVER, AIR, AIR, AIR, AIR}
    }, {
            {AIR, AIR, AIR, AIR, AIR},
            {main::get, stairBottom1, AIR, stairBottom2, main::get},
            {WALL, AIR, AIR, AIR, WALL},
            {main::get, stairBottom1, AIR, stairBottom2, main::get},
            {AIR, AIR, AIR, AIR, AIR}
    }, {
            {AIR, AIR, AIR, AIR, AIR},
            {cap::get, stairBottom3, stairBottom3, stairBottom3, cap::get},
            {stairBottom6, main::get, ALTAR, main::get, stairBottom5},
            {cap::get, stairBottom4, stairBottom4, stairBottom4, cap::get},
            {AIR, AIR, AIR, AIR, AIR}
    }}
    );

    public CraftingAltarTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        main.set(Blocks.PURPUR_BLOCK.getDefaultState());
        cap.set(ModBlocks.SUNSTONE_BLOCK.lazyMap(Block::getDefaultState).get());
        invalidateMB();
    }

    public CraftingAltarTileEntity() {
        this(ModTileEntities.ALTAR_CORE.get());
    }

    public boolean checkMultiblock(World world) {
        BlockPos pos = getPos();
        BlockPos basePos = pos.offset(Direction.DOWN, 4);

        // get and check cap block
        Block bottomCenter = world.getBlockState(basePos).getBlock();
        if (!CraftingAltarStructureMaterials.isValidCapMaterial(bottomCenter)) {
            
            return false;
        }
        this.cap.set(bottomCenter.getDefaultState());

        // get and check main block
        Block firstMain = world.getBlockState(basePos.north()).getBlock();
        if (!CraftingAltarStructureMaterials.isValidMainMaterial(firstMain)) {
            
            return false;
        }
        if (CraftingAltarStructureMaterials.getStairForBlock(firstMain) == null) {
            
            return false;
        }
        this.main.set(firstMain.getDefaultState());

        Direction direction = getStructureDirection(world, pos);
        if (direction == null) return false;
        return STRUCTURE.check(world, pos.down(4).offset(direction, 2).offset(direction.rotateYCCW(), 2), direction);
    }

    public void placeStructure(World world, Direction face) {
        if (face.getAxis().isVertical())
            return;
        STRUCTURE.place(world, getPos().down(4).offset(face, 2).offset(face.rotateYCCW(), 2), face);
    }

    private Direction getStructureDirection(World world, BlockPos pos) {
        Direction.Axis axis = getDirection(world, pos);
        if (world.getBlockState(pos.offset(Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, axis), 2).offset(Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, axis).rotateYCCW(), 2).down(3)).getBlock() == Blocks.LECTERN) {
            return Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, axis);
        } else if (world.getBlockState(pos.offset(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, axis), 2).offset(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, axis).rotateYCCW(), 2).down(3)).getBlock() == Blocks.LECTERN) {
            return Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, axis);
        } else {
            return null;
        }
    }

    private Direction.Axis getDirection(World world, BlockPos pos) {
        if (world.isAirBlock(pos.offset(Direction.NORTH, 2)))
            return Direction.Axis.Z;
        else if (world.isAirBlock(pos.offset(Direction.WEST, 2)))
            return Direction.Axis.X;
        else return null;
    }

    @Override
    public void tick() {
        
        if (this.getWorld() == null) {
            return;
        }
        checkTimer++;
        if (checkTimer < 5)
            return;
        checkTimer = 0;
        if (first)
            invalidateMB();
        first = false;
        boolean check = checkMultiblock(this.getWorld());
        if (check) {
            Direction direction = getStructureDirection(getWorld(), getPos());
            
            if (direction != null) {
                TileEntity te = getWorld().getTileEntity(getPos().offset(direction.getOpposite(), 2).offset(direction.rotateY(), 2).down(3));
                
                if (te instanceof LecternTileEntity) {
                    this.lecternPos = te.getPos();
                    if (!this.getWorld().isRemote()) {
                        //boolean flag = this.powerFlag;
                        this.powerFlag = checkAltarPower();
                        sync();
                    }
                    if (!isMultiblockFormed()) {
                        this.multiblockState = true;
                        this.modelData.setData(CAMO_RL, getWorld().getBlockState(getPos().offset(direction.rotateY())).getBlock().getRegistryName());
                        getWorld().setBlockState(te.getPos().up(), ModBlocks.ALTAR_VIEW.map(Block::getDefaultState).orElse(Blocks.AIR.getDefaultState()));
                        //noinspection ConstantConditions
                        ((CraftingAltarViewTileEntity) getWorld().getTileEntity(te.getPos().up())).setAltarPos(getPos());
                        //ArsMagicaLegacy.LOGGER.debug("View1: {} {}", getWorld().getBlockState(te.getPos().up()), getWorld().getTileEntity(te.getPos().up()));
                    }
                } else {
                    if (isMultiblockFormed())
                        invalidateMB();
                    return;
                }
            } else {
                if (isMultiblockFormed())
                    invalidateMB();
                return;
            }
        } else {
            if (isMultiblockFormed())
                invalidateMB();
            return;
        }
        if (this.getRecipe() != null && this.hasEnoughPower()) {
            if (craft()) sync();
        }
    }

    private boolean checkAltarPower() {
        int count = 0;
        if (getBook().isEmpty() || !getBook().hasTag())
            return false;
        CompoundNBT tag = getBook().getTag();
        for (int i = 0; i < InscriptionTableTileEntity.MAX_STAGE_GROUPS; i++) {
            //noinspection ConstantConditions
            if (tag.contains("shapeGroupCombo_" + i)) {
                count += tag.getList("shapeGroupCombo_" + i, Constants.NBT.TAG_STRING)
                        .stream()
                        .map(INBT::getString)
                        .map(ResourceLocation::tryCreate).filter(Objects::nonNull)
                        .map(ArsMagicaAPI.getSpellPartRegistry()::getValue).filter(Objects::nonNull)
                        .count();
            }
        }
        count += getBook().getTag().getList("output_combo", Constants.NBT.TAG_STRING)
                .stream()
                .map(INBT::getString)
                .map(ResourceLocation::tryCreate).filter(Objects::nonNull)
                .map(ArsMagicaAPI.getSpellPartRegistry()::getValue).filter(Objects::nonNull)
                .count();
        int power = CraftingAltarStructureMaterials.getMainPower(main.get().getBlock()) + CraftingAltarStructureMaterials.getCapPower(cap.get().getBlock());
        return count < power;
    }

    private void sync() {
        if (getWorld() != null && !getWorld().isRemote())
            NetworkHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(getPos().getX(), getPos().getY(), getPos().getZ(), 96, getWorld().getDimension().getType())), new TEClientSyncPacket(this));
    }

    private void invalidateMB() {
        if (getWorld() != null && lecternPos != null) {
            BlockPos pos = lecternPos.up();
            //ArsMagicaLegacy.LOGGER.debug("View2: {}",getWorld().getTileEntity(pos));
            if(getWorld().getBlockState(pos).getBlock() == ModBlocks.ALTAR_VIEW.get())
                getWorld().removeBlock(pos, false);
        }
        this.multiblockState = false;
        this.powerFlag = false;
        this.currentStage = 0;
        this.lecternPos = null;
        this.lectern = null;
        this.modelData.setData(CAMO_RL, ModBlocks.ALTAR_CORE.getId());
        sync();
    }

    private boolean craft() {
        if (getWorld() == null || getRecipe() == null)
            return false;
        if (this.currentStage >= getRecipe().size()) {
            if (!getWorld().isRemote()) {
                InventoryHelper.spawnItemStack(getWorld(), getPos().getX(), getPos().getY() - 2, getPos().getZ(), createSpellStack());
                this.currentStage = 0;
            }
            return true;
        }
        
        ISpellIngredient ingredient = getRecipe().get(this.currentStage);
        if (ingredient.consume(getWorld(), getPos())) {
            this.currentStage++;
            return true;
        }
        return false;
    }

    private ItemStack createSpellStack() {
        List<Pair<List<AbstractSpellPart>, CompoundNBT>> shapeGroups = new ArrayList<>(5);
        for (int i = 0; i < InscriptionTableTileEntity.MAX_STAGE_GROUPS; i++) {
            CompoundNBT tag = getBook().getOrCreateTag();
            if (tag.contains("shapeGroupCombo_" + i)) {
                shapeGroups.add(
                        new Pair<>(tag.getList("shapeGroupCombo_" + i, Constants.NBT.TAG_STRING)
                                .stream()
                                .map(INBT::getString)
                                .map(ResourceLocation::tryCreate).filter(Objects::nonNull)
                                .map(ArsMagicaAPI.getSpellPartRegistry()::getValue).filter(Objects::nonNull)
                                .collect(Collectors.toList()),
                                new CompoundNBT())
                );
            } else {
                shapeGroups.add(new Pair<>(new ArrayList<>(), new CompoundNBT()));
            }
        }
        shapeGroups = ImmutableList.copyOf(shapeGroups);
        //noinspection ConstantConditions
        ItemStack stack = SpellUtils.createSpellStack(
                shapeGroups,
                getBook().getTag()
                        .getList("output_combo", Constants.NBT.TAG_STRING)
                        .stream()
                        .map(INBT::getString)
                        .map(ResourceLocation::tryCreate).filter(Objects::nonNull)
                        .map(ArsMagicaAPI.getSpellPartRegistry()::getValue).filter(Objects::nonNull)
                        .collect(Collectors.toList()),
                new CompoundNBT()
        );
        stack.getOrCreateTag().putString("suggestedName", this.getBook().getDisplayName().getFormattedText());
        return stack;
    }

    private SpellIngredientList readRecipe(ItemStack stack) {
        //noinspection ConstantConditions
        if (!stack.hasTag() || stack.getTag().get("spell_combo") == null)
            return null;
        ListNBT materials = stack.getTag().getList("spell_combo", Constants.NBT.TAG_COMPOUND);
        SpellIngredientList list = new SpellIngredientList();
        list.deserializeNBT(materials);
        return list;
    }

    ISpellIngredient getCurrentIngredient() {
        if (this.getRecipe() == null || currentStage >= this.getRecipe().size())
            return null;
        return this.getRecipe().get(currentStage);
    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        return modelData;
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (lecternPos != null) 
            compound.put("lectern", NBTUtil.writeBlockPos(lecternPos));
        compound.putInt("craft_state", currentStage);
        compound.putBoolean("power_flag", hasEnoughPower());
        if (book != null)
            compound.put("book", book.write(new CompoundNBT()));
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("lectern"))
            lecternPos = NBTUtil.readBlockPos(compound.getCompound("lectern"));
        this.currentStage = compound.getInt("craft_state");
        this.powerFlag = compound.getBoolean("power_flag");
        if (compound.contains("book"))
            this.book = ItemStack.read(compound.getCompound("book"));
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Nullable
    public SpellIngredientList getRecipe() {
        ItemStack book = getBook();
        if (book.isEmpty() || !book.hasTag())
            return null;
        return readRecipe(book);
    }

    @Nonnull
    public ItemStack getBook() {
        if (this.book == null)
            this.book = ItemStack.EMPTY;
        if (this.book.isEmpty() && getWorld() != null) {
            LecternTileEntity lectern = getLectern();
            if (lectern != null)
                this.book = lectern.getBook();
        }
        return this.book;
    }

    @Nullable
    public LecternTileEntity getLectern() {
        if (lectern == null && getWorld() != null && lecternPos != null) {
            TileEntity te = getWorld().getTileEntity(lecternPos);
            if (te instanceof LecternTileEntity) {
                this.lectern = (LecternTileEntity) te;
                sync();
            }
        }
        return this.lectern;
    }

    public boolean hasEnoughPower() {
        return powerFlag;
    }

    public boolean isMultiblockFormed() {
        return multiblockState;
    }
}
