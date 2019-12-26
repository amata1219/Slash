package amata1219.slash;

import static amata1219.slash.Either.*;

import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import amata1219.slash.Interval.End;

public class Parsers {
	
	public static void main(){
		limited(i32, Interval.of(End.open(1), End.closed(100)), "&c-数値は1以上100以下にして下さい。");
	}

	public static final Parser<String> identity = x -> success(x);
	
	public static final Parser<Boolean> bool = define(Boolean::valueOf, "&c-真偽値は true 又は false を入力して下さい。");
	
	public static final Parser<Integer> i32 = define(Integer::valueOf, "&c-数値は半角数字で入力して下さい。");
	
	public static final Parser<Long> i64 = define(Long::valueOf, "&c-数値は半角数字で入力して下さい。");
	
	@SuppressWarnings("deprecation")
	public static final Parser<OfflinePlayer> player = define(Bukkit::getOfflinePlayer, "&c-プレイヤー名を入力して下さい。")
			.then(OfflinePlayer::hasPlayedBefore, "&c-指定されたプレイヤーは存在しません。");
	
	public static final <N extends Number & Comparable<N>> Parser<N> limited(Parser<N> parser, Interval<N> interval, CharSequence error){
		return parser.then(interval::contains, error);
	}
	
	private static <T> Parser<T> define(Function<String, T> converter, CharSequence error){
		return x -> {
			try{
				return success(converter.apply(x));
			}catch(Exception e){
				return failure(error);
			}
		};
	}
	
}
