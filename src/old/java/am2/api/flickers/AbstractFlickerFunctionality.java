package am2.api.flickers;

import am2.api.affinity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.registry.*;

public abstract class AbstractFlickerFunctionality extends IForgeRegistryEntry.Impl<AbstractFlickerFunctionality> {
	
	public abstract boolean RequiresPower();

	public abstract int PowerPerOperation();

	public abstract boolean DoOperation(World worldObj, IFlickerController<?> controller, boolean powered);

	public abstract boolean DoOperation(World worldObj, IFlickerController<?> controller, boolean powered, Affinity[] flickers);
	
	public abstract void RemoveOperator(World worldObj, IFlickerController<?> controller, boolean powered);

	public abstract int TimeBetweenOperation(boolean powered, Affinity[] flickers);

	public abstract void RemoveOperator(World worldObj, IFlickerController<?> controller, boolean powered, Affinity[] flickers);

	public abstract Object[] getRecipe();
	
	public abstract ResourceLocation getTexture();
	
	public abstract Affinity[] getMask();
}
