package am2.items;

//class to identify items that are part of the Ars Magica mod, mainly used for rendering
public class ItemArsMagicaRotated extends ItemArsMagica{

	@Override
	public boolean isFull3D(){
		return true;
	}

	@Override
	public boolean shouldRotateAroundWhenRendering(){
		return true;
	}
}
