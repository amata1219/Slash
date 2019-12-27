package amata1219.slash;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public interface PartialExecutor<S extends CommandSender> extends CommandExecutor {
	
	@Override
	default boolean onCommand(CommandSender sender, Command command, String label, String[] arguments){
		execute(sender, Arrays.asList(arguments));
		return true;
	}
	
	void execute(CommandSender sender, List<String> arguments);

}
