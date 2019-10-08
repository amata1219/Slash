package amata1219.slash;

import static amata1219.slash.Interval.*;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.base.Supplier;

public interface Command<R> {
	
	public static void main(String[] $){
		
		Result(100).otherwise(Range(0, 10)::contains, () -> "");
		
	}
	
	public static <R> Maybe<R> Result(R result){
		return Maybe.unit(result);
	}
	
	public static <R> Error<R> Error(String error){
		return new Error<>(error);
	}
	
	@SuppressWarnings("unchecked")
	default <T> Command<T> flatBind(Function<R, Command<T>> mapper){
		return this instanceof Error ? (Command<T>) this : mapper.apply(((Result<R>) this).result);
	}
	
	default <T> Command<T> bind(Function<R, T> mapper){
		return flatBind(res -> Result(mapper.apply(((Result<R>) this).result)));
	}
	
	default void match(Consumer<String> error, Consumer<R> result){
		if(this instanceof Error) error.accept(((Error<R>) this).error);
		else result.accept(((Result<R>) this).result);
	}
	
	default Command<R> when(Predicate<R> predicate, Supplier<String> error){
		return this instanceof Error ? this : predicate.test(((Result<R>) this).result) ? Error(error.get()) : this;
	}
	
	default Command<R> otherwise(Predicate<R> predicate, Supplier<String> error){
		return when(predicate.negate(), error);
	}
	
	default Command<R> match(@SuppressWarnings("unchecked") Matcher<R>... cases){
		if(this instanceof Error || cases.length == 1) return this;
		for(Matcher<R> cs : cases) if(cs.match(((Result<R>) this).result)) return cs.evalExpr();
		return this;
	}
	
	class Error<R> implements Command<R> {
		
		public final String error;
		
		private Error(String error){
			this.error = error;
		}
		
	}
	
	class Result<R> implements Command<R> {
		
		public final R result;
		
		private Result(R result){
			this.result = result;
		}
		
	}
	
}
