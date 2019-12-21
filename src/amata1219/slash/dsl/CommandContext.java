package amata1219.slash.dsl;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.bukkit.command.CommandSender;

public class CommandContext<S extends CommandSender> {
	
	public final S sender;
	//private final List<Parser<?>> parsers;
	private final List<Object> parsed = new ArrayList<>();
	private final Queue<String> unparsed;
	
	public CommandContext(S sender, Queue<String> args){
		this.sender = sender;
		this.parsers = parsers;
		this.unparsed = args;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T parsed(int index){
		return (T) parsed.get(index);
	}
	
	private void parse(Queue<String> )

}
