package amata1219.slash;

import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.base.Supplier;

import amata1219.slash.Result.Just;
import amata1219.slash.Result.Nothing;

public interface CommandMonad<R> {
	
	public static void main(String[] $){
	}
	
	/*
	 * default void match(Consumer<E> whenE, Consumer<R> whenR){
		if(this instanceof Error) whenE.accept(((Error<E, R>) this).error);
		else whenR.accept(((Result<E, R>) this).result);
	}
	
	default Either<E, R> when(boolean predicate, E error){
		return when(__ -> predicate, error);
	}
	
	default Either<E, R> when(Predicate<R> predicate, E error){
		return this instanceof Error ? this : predicate.test(((Result<E, R>) this).result) ? new Error<>(error) : this;
	}
	 */
	
	public static <R> Result<R> Result(R result){
		return Result.unit(result);
	}
	
	public static <R> Error<R> Error(String error){
		return new Error<>(error);
	}
	
	@SuppressWarnings("unchecked")
	default <T> CommandMonad<T> flatBind(Function<R, CommandMonad<T>> mapper){
		return this instanceof Error || this instanceof Nothing ? (CommandMonad<T>) this : mapper.apply(((Just<R>) this).value);
	}
	
	default <T> CommandMonad<T> bind(Function<R, T> mapper){
		return flatBind(res -> Result(mapper.apply(((Just<R>) this).value)));
	}
	
	default CommandMonad<R> when(Predicate<R> predicate, Supplier<String> error){
		return this instanceof Error || this instanceof Nothing ? this : predicate.test(((Just<R>) this).value) ? Error(error.get()) : this;
	}
	
	default CommandMonad<R> otherwise(Predicate<R> predicate, Supplier<String> error){
		return when(predicate.negate(), error);
	}
	
	class Error<R> implements CommandMonad<R> {
		
		public final String error;
		
		private Error(String error){
			this.error = error;
		}
		
	}
	
}
