package am2.commands;

import java.util.ArrayList;
import java.util.List;

import am2.gui.GuiHudCustomization;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class ConfigureAMUICommand extends CommandBase{

	private static boolean showGUI = false;

	public static void showIfQueued(){
		if (showGUI){
			Minecraft.getMinecraft().displayGuiScreen(new GuiHudCustomization());
			showGUI = false;
		}
	}

	@Override
	public String getCommandName(){
		return "amuicfg";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender){
		return "/amuicfg";
	}

	@Override
	public int getRequiredPermissionLevel(){
		return 0;
	}

	@Override
	public List<String> getCommandAliases(){
		ArrayList<String> aliases = new ArrayList<String>();
		aliases.add("AMUICFG");
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		showGUI = true;
	}

}
