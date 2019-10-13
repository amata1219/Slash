package amata1219.slash.dsl;

import static amata1219.slash.dsl.CommandMonad.*;
import static amata1219.slash.dsl.component.Matcher.*;

import org.bukkit.command.CommandSender;

import amata1219.slash.ArgumentList;

public enum TestCommand implements Command {
	
	EXECUTOR(null),
	UNSPECIFIED_ARGUMENT_ERR("引数を指定して下さい。"),
	UNSPECIFIED_CHANNEL_NAME_ERR("チャンネル名を指定して下さい。"),
	MISSING_CHANNEL_ERR("指定されたチャンネルは見つかりません。"),
	JOIN_MSG("%sに参加しました。"),
	LEAVE_MSG("%sから退出しました。"),
	AFK_MSG("AFKモードに移行しました。");
	
	private final String m;
	
	private TestCommand(String m){
		this.m = m;
	}
	
	@Override
	public String message() {
		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCommand(CommandSender sender, ArgumentList args) {
		args.next(UNSPECIFIED_ARGUMENT_ERR).match(
			Case("join").label(() -> args.next(UNSPECIFIED_ARGUMENT_ERR).bind(ChannelSet::get).whenR(
				c -> {
					c.join(sender);
					return JOIN_MSG.format(c.name);
				}
			)),
			Else(() -> unit(ChannelSet.participating(sender), MISSING_CHANNEL_ERR).flatBind(
				c -> args.next(UNSPECIFIED_ARGUMENT_ERR).match(
					Case("leave").label(() -> {
						c.leave(sender);
						return Message(LEAVE_MSG.format(c.name));
					}),
					Case("afk").label(() -> {
						c.afk(sender);
						return Message(AFK_MSG.format(c.name));
					})
				)
			))
		);
	}
	
	public static class ChannelSet {
		
		public static Channel get(String name){
			return null;
		}
		
		public static Channel participating(CommandSender sender){
			return null;
		}
		
	}
	
	public static class Channel {
		
		public final String name = "";
		
		public void join(CommandSender sender){
			
		}
		
		public void leave(CommandSender sender){
			
		}
		
		public void afk(CommandSender sender){
			
		}
		
	}

}
