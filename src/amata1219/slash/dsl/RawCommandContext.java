package amata1219.slash.dsl;

import java.util.List;

import org.bukkit.command.CommandSender;

public class RawCommandContext {
	
	public final CommandSender sender;
	public final ExecutedCommand command;
	public final List<String> args;
	
	public RawCommandContext(CommandSender sender, ExecutedCommand command, List<String> args){
		this.sender = sender;
		this.command = command;
		this.args = args;
	}

}
