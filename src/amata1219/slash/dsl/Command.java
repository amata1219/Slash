package amata1219.slash.dsl;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import amata1219.slash.ArgumentList;

public interface Command extends CommandExecutor, Message {
	
	void onCommand(CommandSender sender, ArgumentList args);
	
	@Override
	default boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args){
		onCommand(sender, new ArgumentList(args));
		return true;
	}

}
