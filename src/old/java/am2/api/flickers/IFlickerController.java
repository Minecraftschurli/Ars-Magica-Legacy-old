package am2.api.flickers;

import net.minecraft.tileentity.TileEntity;

public interface IFlickerController<T extends TileEntity>{
	public byte[] getMetadata(AbstractFlickerFunctionality operator);

	public void setMetadata(AbstractFlickerFunctionality operator, byte[] meta);

	public void removeMetadata(AbstractFlickerFunctionality operator);
}
