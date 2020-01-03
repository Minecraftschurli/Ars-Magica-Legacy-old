package am2.asm;

import am2.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.*;

import java.io.*;
import java.util.*;

@Name("ArsMagica2-Preloader")
@DependsOn("arsmagica2")
@SortingIndex(1001)
public class Preloader implements IFMLLoadingPlugin {

	public static boolean isDevEnvironment;
	public static boolean foundThaumcraft;

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {"am2.asm.Transformer"};
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		File loc = (File)data.get("mcLocation");

		LogHelper.trace("MC located at: " + loc.getAbsolutePath());
		isDevEnvironment = !(Boolean)data.get("runtimeDeobfuscationEnabled");

		File mcFolder = new File(loc.getAbsolutePath() + File.separatorChar + "mods");
		File[] subfiles = mcFolder.listFiles();
		for (File file : subfiles){
			String name = file.getName();
			if (name != null) {
				name = name.toLowerCase();
				if (name.endsWith(".jar") || name.endsWith(".zip")){
					if (name.contains("thaumcraft")){
						LogHelper.info("Core: Located Thaumcraft in " + file.getName());
						foundThaumcraft = true;
					}
//					else if (name.contains("optifine")){
//						LogHelper.info("Core: Located OptiFine in " + file.getName() + ". We'll to confirm that...");
//						foundOptiFine = true;
//					}else if (name.contains("dragonapi")){
//						LogHelper.info("Core: Located DragonAPI in " + file.getName());
//						foundDragonAPI = true;
//					}
				}
			}
		}
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
