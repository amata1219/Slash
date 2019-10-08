package amata1219.slash;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import amata1219.slash.Either.Error;
import amata1219.slash.Either.Result;

public class Slash extends JavaPlugin {
	
	private static Slash plugin;
	
	@Override
	public void onEnable(){
		plugin = this;
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public static Slash plugin(){
		return plugin;
	}
	
	public void execute(CommandSender sender, String[] args){
		/*
		 * 
		 * nextInt(() -> "err: a").when(Range(0, 10), () -> "err: i").flatBind(
		 * a -> next(() -> "err: b").sw1tch(
		 *         Case("hi", "h").expr(() -> sender.setOp(true)),
		 *         Case("bye").expr(() -> next(() -> "err: c").match(
		 *             err -> sender.sendMessage(err),
		 *             c -> execute(a, c)
		 *         )),
		 *         Else(() -> Error("err: else"))
		 *      )
		 * ));
		 * 
		 * nextInt(() -> "err: a").when(Range(0, 10), () -> "err: i").flatBind(
		 * a -> nextInt(() -> "err: b").match(
		 *         Case(1).expr(() -> sender.setOp(true)),
		 *         Case(Range(0, 10)::contains).expr(() -> next(() -> "err: c").match(
		 *             err -> sender.sendMessage(err),
		 *             c -> execute(a, c)
		 *         )),
		 *         Else(() -> Error("err: else"))
		 *      )
		 * ));
		 * 
		 * Result(next()): Result<Maybe<T>>
		 * whenN("err: a"): err: Error<String> / Result<Maybe<T>>
		 * flatBind(a -> Result(next()): Result<Maybe<U>>
		 * whenN("err: b"): err: Error<String> / Result<Maybe<U>>
		 * flatBind(b -> b.match(~)): Error<String>
		 * case("hi", ~): Either<String, Maybe<U>>
		 * case("bye", either(next)): Result<Maybe<V>>
		 * whenN("err: c"): err: Error<String> / Result<Maybe<V>>
		 * isJust(~): Result<Maybe<V>>
		 * execute(a, c): void
		 * else(() -> Error("err: else")): Error<String>
		 * ifNothing(~): void
		 * 
		 */
		
	}
	
}
