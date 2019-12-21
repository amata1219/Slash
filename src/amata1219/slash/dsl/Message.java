package amata1219.slash.dsl;

import org.bukkit.command.CommandSender;

public interface Message {
	
	default void sendTo(CommandSender sender){
		sender.sendMessage(message());
	}
	
	String message();
	
}
