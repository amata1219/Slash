package amata1219.slash;

import java.util.Queue;

import org.bukkit.command.CommandSender;

public class BranchedExecutor<S extends CommandSender> implements PartialExecutor<S> {
	
	@Override
	public void execute(CommandSender sender, Queue<String> arguments) {
	}

}
