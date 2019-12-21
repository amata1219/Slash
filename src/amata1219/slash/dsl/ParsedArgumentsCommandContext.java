package amata1219.slash.dsl;

import org.bukkit.command.CommandSender;

public class ParsedArgumentsCommandContext<S extends CommandSender> {
	
	public final S sender;
	public final ExecutedCommand command;
	public final PartiallyParsedArguments arguments;
	
	public ParsedArgumentsCommandContext(S sender, ExecutedCommand command, PartiallyParsedArguments arguments){
		this.sender = sender;
		this.command = command;
		this.arguments = arguments;
	}

}
