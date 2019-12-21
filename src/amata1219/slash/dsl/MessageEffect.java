package amata1219.slash.dsl;

import org.bukkit.command.CommandSender;

public interface MessageEffect {
	
	default void sendTo(CommandSender sender){
		Text.of(message()).sendTo(sender);
	}
	
	String message();
	
}
