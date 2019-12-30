package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import com.google.common.collect.ImmutableList;
import javafx.util.Pair;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaLegacyAPI;
import minecraftschurli.arsmagicalegacy.api.SpellRegistry;
import minecraftschurli.arsmagicalegacy.api.multiblock.Structure;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.SpellIngredientList;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import minecraftschurli.arsmagicalegacy.network.TEClientSyncPacket;
import minecraftschurli.arsmagicalegacy.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableTileEntity;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LecternTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    private static final LinkedHashMap<Block, Integer> CAPS = new LinkedHashMap<>();
    private static final LinkedHashMap<Block, Integer> MAIN = new LinkedHashMap<>();
    private static final LinkedHashMap<Block, StairsBlock> STAIRS = new LinkedHashMap<>();
    private static final Supplier<BlockState> AIR = Blocks.AIR::getDefaultState;
    private static final Supplier<BlockState> WALL = () -> ModBlocks.MAGIC_WALL.lazyMap(Block::getDefaultState).get();
    private static final Supplier<BlockState> LECTERN = Blocks.LECTERN::getDefaultState;
    private static final Supplier<BlockState> LEVER = Blocks.LEVER::getDefaultState;
    private static final Supplier<BlockState> ALTAR = () -> ModBlocks.ALTAR_CORE.lazyMap(Block::getDefaultState).get();
    private AtomicReference<BlockState> cap = new AtomicReference<>();
    private AtomicReference<BlockState> main = new AtomicReference<>();
    private Supplier<BlockState> stairBottom1 = () -> STAIRS.get(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, Direction.EAST);
    private Supplier<BlockState> stairBottom2 = () -> STAIRS.get(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, Direction.WEST);
    private Supplier<BlockState> stairBottom3 = () -> STAIRS.get(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.FACING, Direction.NORTH);
    private Supplier<BlockState> stairBottom4 = () -> STAIRS.get(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.FACING, Direction.SOUTH);
    private Supplier<BlockState> stairBottom5 = () -> STAIRS.get(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.FACING, Direction.EAST);
    private Supplier<BlockState> stairBottom6 = () -> STAIRS.get(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.FACING, Direction.WEST);
    @SuppressWarnings("unchecked")
    final Structure STRUCTURE = new CraftingAltarStructure(new Supplier[][][]{{
                {    main::get,    main::get,    main::get,    main::get,    main::get},
                {    main::get,    main::get,    main::get,    main::get,    main::get},
                {    main::get,    main::get,     cap::get,    main::get,    main::get},
                {    main::get,    main::get,    main::get,    main::get,    main::get},
                {    main::get,    main::get,    main::get,    main::get,    main::get}
            }, {
                {          AIR,          AIR,          AIR,          AIR,          AIR},
                {    main::get,          AIR,          AIR,          AIR,    main::get},
                {         WALL,          AIR,          AIR,          AIR,         WALL},
                {    main::get,          AIR,          AIR,          AIR,    main::get},
                {          AIR,          AIR,          AIR,          AIR,      LECTERN}
            }, {
                {          AIR,          AIR,          AIR,          AIR,          AIR},
                {    main::get,          AIR,          AIR,          AIR,    main::get},
                {         WALL,          AIR,          AIR,          AIR,         WALL},
                {    main::get,          AIR,          AIR,          AIR,    main::get},
                {        LEVER,          AIR,          AIR,          AIR,          AIR}
            }, {
                {          AIR,          AIR,          AIR,          AIR,          AIR},
                {    main::get, stairBottom1,          AIR, stairBottom2,    main::get},
                {         WALL,          AIR,          AIR,          AIR,         WALL},
                {    main::get, stairBottom1,          AIR, stairBottom2,    main::get},
                {          AIR,          AIR,          AIR,          AIR,          AIR}
            }, {
                {          AIR,          AIR,          AIR,          AIR,          AIR},
                {     cap::get, stairBottom3, stairBottom3, stairBottom3,     cap::get},
                { stairBottom6,    main::get,        ALTAR,    main::get, stairBottom5},
                {     cap::get, stairBottom4, stairBottom4, stairBottom4,     cap::get},
                {          AIR,          AIR,          AIR,          AIR,          AIR}
            }}
    );

    static {
        CAPS.put(ModBlocks.SUNSTONE_BLOCK.get(), 2);
        MAIN.put(Blocks.PURPUR_BLOCK, 2);
        STAIRS.put(Blocks.PURPUR_BLOCK, (StairsBlock) Blocks.PURPUR_STAIRS);
        /*BlockTags.STAIRS.getAllElements().stream()
                .filter(block -> block instanceof StairsBlock)
                .map(block -> (StairsBlock)block)
                .map(stairsBlock -> stairsBlock)*/
    }

    int checkTimer = 0;

    private SpellIngredientList recipe;
    private ItemStack book;
    private int currentStage;
    boolean multiblockState;
    private Vec3d lecternOffset;
    private ResourceLocation camouflageRL;

    public CraftingAltarTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        main.set(Blocks.PURPUR_BLOCK.getDefaultState());
        cap.set(ModBlocks.SUNSTONE_BLOCK.lazyMap(Block::getDefaultState).get());
        invalidateMB();
    }

    public CraftingAltarTileEntity() {
        this(ModTileEntities.ALTAR_CORE.get());
    }

    public boolean checkMultiblock(World world){
        BlockPos pos = getPos();
        BlockPos basePos = pos.offset(Direction.DOWN, 4);

        // get and check cap block
        Block bottomCenter = world.getBlockState(basePos).getBlock();
        if (!CAPS.containsKey(bottomCenter))
            return false;
        this.cap.set(bottomCenter.getDefaultState());

        // get and check main block
        Block firstMain = world.getBlockState(basePos.north()).getBlock();
        if (!MAIN.containsKey(firstMain))
            return false;
        this.main.set(firstMain.getDefaultState());

        Direction direction = getStructureDirection(world, pos);
        if (direction == null) return false;
        return STRUCTURE.check(world, pos.down(4).offset(direction, 2).offset(direction.rotateYCCW(), 2), direction);
        /*
        // check base
        BlockPos cornerPos = basePos.add(-2,0,-2);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 2 && j == 2)
                    continue;
                if(main != world.getBlockState(cornerPos.add(i,0,j)).getBlock())
                    return false;
            }
        }

        // get and check axis
        Direction.Axis direction = getDrection(world);
        if (direction == null)
            return false;

        // check blocks beside Altar Core
        if (main != world.getBlockState(pos.offset(Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, direction))).getBlock() ||
            main != world.getBlockState(pos.offset(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, direction))).getBlock())
            return false;

        // check stairs 1
        BlockState stair = STAIRS.get(main).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.SHAPE, StairsShape.STRAIGHT);
        Direction d = Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, direction);
        if (!world.getBlockState(pos.offset(d.getOpposite(), 2)).equals(stair.with(StairsBlock.FACING, d)) ||
            !world.getBlockState(pos.offset(d, 2)).equals(stair.with(StairsBlock.FACING, d.getOpposite())))
            return false;

        // check stairs 2
        Direction d2 = d.rotateY();
        BlockPos stairsPos1 = pos.offset(d2).offset(d);
        BlockPos stairsPos2 = pos.offset(d2.getOpposite()).offset(d);
        for (int i = 0; i < 3; i++) {
            if (!world.getBlockState(stairsPos1.offset(d.getOpposite(), i)).equals(stair.with(StairsBlock.FACING, d2.getOpposite())) ||
                    !world.getBlockState(stairsPos2.offset(d.getOpposite(), i)).equals(stair.with(StairsBlock.FACING, d2)))
                return false;
        }

        // check arch stairs
        if (!world.getBlockState(pos.down().offset(d).offset(d.rotateY())).equals(stair.with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, d.getOpposite())) ||
                !world.getBlockState(pos.down().offset(d).offset(d.rotateYCCW())).equals(stair.with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, d.getOpposite())) ||
                !world.getBlockState(pos.down().offset(d.getOpposite()).offset(d.rotateY())).equals(stair.with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, d)) ||
                !world.getBlockState(pos.down().offset(d.getOpposite()).offset(d.rotateYCCW())).equals(stair.with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, d)))
            return false;*/
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
        //ArsMagicaLegacy.LOGGER.debug("{}", world);
        if (this.world == null){
            return;
        }
        checkTimer++;
        if (checkTimer < 5)
            return;
        checkTimer = 0;
        boolean check = checkMultiblock(this.world);
        if (check) {
            Direction direction = getStructureDirection(world, pos);
            //ArsMagicaLegacy.LOGGER.debug("{}", direction);
            if (direction != null){
                TileEntity te = world.getTileEntity(pos.offset(direction.getOpposite(), 2).offset(direction.rotateY(),2).down(3));
                //ArsMagicaLegacy.LOGGER.debug("{}", te);
                if (te instanceof LecternTileEntity){
                    //ArsMagicaLegacy.LOGGER.debug("{}", lecternOffset);
                    LecternTileEntity lectern = (LecternTileEntity) te;
                    if (!this.world.isRemote) {
                        if (book.isEmpty() || (!book.hasTag() || !book.getTag().contains("spell_combo") || (lectern.getBook().hasTag() && lectern.getBook().getTag().contains("spell_combo") && !book.getTag().get("spell_combo").equals(lectern.getBook().getTag().get("spell_combo"))))) {
                            this.book = lectern.getBook();
                            this.recipe = readRecipe(book);
                            sync();
                        } else if (lectern.getBook().isEmpty()) {
                            this.book = ItemStack.EMPTY;
                            this.recipe = null;
                            sync();
                        }
                    }
                    if (!multiblockState) {
                        this.multiblockState = true;
                        this.lecternOffset = new Vec3d(te.getPos().subtract(this.getPos()));
                        this.camouflageRL = world.getBlockState(getPos().offset(direction.rotateY())).getBlock().getRegistryName();
                        world.setBlockState(te.getPos().up(), ModBlocks.ALTAR_VIEW.map(Block::getDefaultState).orElse(Blocks.AIR.getDefaultState()));
                        ((CraftingAltarViewTileEntity)world.getTileEntity(te.getPos().up())).setAltarPos(pos);
                        ArsMagicaLegacy.LOGGER.debug(world.getBlockState(te.getPos().up()));
                    }
                } else {
                    if (multiblockState)
                        invalidateMB();
                    return;
                }
            } else {
                if (multiblockState)
                    invalidateMB();
                return;
            }
        } else {
            if (multiblockState)
                invalidateMB();
            return;
        }
        if (this.book != ItemStack.EMPTY && this.recipe != null){
            if (craft())
                sync();
        }
    }

    private void sync() {
        if (world != null && !world.isRemote)
            NetworkHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(getPos().getX(), getPos().getY(), getPos().getZ(), 96, world.dimension.getType())), new TEClientSyncPacket(this));
    }

    private void invalidateMB() {
        if (world != null) {
            BlockPos pos = new BlockPos(getPos().add(new BlockPos(lecternOffset)).up());
            ArsMagicaLegacy.LOGGER.debug(world.getTileEntity(pos));
            world.removeBlock(pos, false);
        }
        this.multiblockState = false;
        this.book = ItemStack.EMPTY;
        this.recipe = null;
        this.currentStage = 0;
        this.lecternOffset = Vec3d.ZERO;
        this.camouflageRL = ModBlocks.ALTAR_CORE.getId();
        sync();
    }

    private boolean craft() {
        if (world == null)
            return false;
        if (!world.isRemote && this.currentStage >= recipe.size()) {
            InventoryHelper.spawnItemStack(world, getPos().getX(), getPos().getY()-2, getPos().getZ(), createSpellStack());
            this.currentStage = 0;
            return true;
        }
        //ArsMagicaLegacy.LOGGER.debug("{}", currentStage);
        ISpellIngredient ingredient = recipe.get(this.currentStage);
        if (ingredient.consume(getWorld(), getPos())) {
            this.currentStage++;
            return true;
        }
        return false;
    }

    private ItemStack createSpellStack() {//TODO save to spell
        List<Pair<List<AbstractSpellPart>, CompoundNBT>> shapeGroups = new ArrayList<>(5);
        for (int i = 0; i < InscriptionTableTileEntity.MAX_STAGE_GROUPS; i++) {
            CompoundNBT tag = book.getOrCreateTag();
            if (tag.contains("shapeGroupCombo_"+i)){
                shapeGroups.add(
                        new Pair<>(tag.getList("shapeGroupCombo_"+i, Constants.NBT.TAG_STRING)
                                .stream()
                                .map(INBT::getString)
                                .map(ResourceLocation::tryCreate).filter(Objects::nonNull)
                                .map(ArsMagicaLegacyAPI.getSpellPartRegistry()::getValue).filter(Objects::nonNull)
                                .collect(Collectors.toList()),
                        new CompoundNBT())
                );
            } else {
                shapeGroups.add(new Pair<>(new ArrayList<>(), new CompoundNBT()));
            }
        }
        shapeGroups = ImmutableList.copyOf(shapeGroups);
        ItemStack stack = SpellUtils.createSpellStack(
                shapeGroups,
                book.getTag()
                        .getList("output_combo", Constants.NBT.TAG_STRING)
                        .stream()
                        .map(INBT::getString)
                        .map(ResourceLocation::tryCreate).filter(Objects::nonNull)
                        .map(ArsMagicaLegacyAPI.getSpellPartRegistry()::getValue).filter(Objects::nonNull)
                        .collect(Collectors.toList()),
                new CompoundNBT()
        );
        stack.getOrCreateTag().putString("suggestedName", this.book.getDisplayName().getFormattedText());
        return stack;
    }

    private SpellIngredientList readRecipe(ItemStack stack) {
        if (!stack.hasTag() || stack.getTag().get("spell_combo") == null)
            return null;
        ListNBT materials = stack.getTag().getList("spell_combo", Constants.NBT.TAG_COMPOUND);
        SpellIngredientList list = new SpellIngredientList();
        list.deserializeNBT(materials);
        return list;
    }

    ISpellIngredient getCurrentIngredient() {
        if (this.recipe == null || currentStage >= this.recipe.size())
            return null;
        return this.recipe.get(currentStage);
    }

    Vec3d getLecternOffset() {
        return lecternOffset;
    }

    ResourceLocation getCamouflageRL() {
        return camouflageRL;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putBoolean("has_recipe", recipe != null);
        compound.put("recipe", recipe != null ? recipe.serializeNBT() : new ListNBT());
        compound.putInt("craft_state", currentStage);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.getBoolean("has_recipe")){
            if (this.recipe == null)
                this.recipe = new SpellIngredientList();
            this.recipe.deserializeNBT(compound.getList("recipe", Constants.NBT.TAG_COMPOUND));
        } else {
            this.recipe = null;
        }
        this.currentStage = compound.getInt("craft_state");
    }
}
