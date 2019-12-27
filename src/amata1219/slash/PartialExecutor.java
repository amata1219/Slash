package amata1219.slash;

import java.util.Arrays;
import java.util.Queue;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;

public interface PartialExecutor<S extends CommandSender> extends CommandExecutor {
	
	@Override
	default boolean onCommand(CommandSender sender, Command command, String label, String[] arguments){
		execute(sender, Lists.newLinkedList(Arrays.asList(arguments)));
		return true;
	}
	
	void execute(CommandSender sender, Queue<String> arguments);

}
