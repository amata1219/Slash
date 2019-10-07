package amata1219.slash;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import amata1219.slash.Maybe.Just;
import amata1219.slash.Maybe.Nothing;

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
	
	@SuppressWarnings("unchecked")
	default <R> Either<E, T> flatMap(Function<R, Either<E, T>> mapper){
		return this instanceof Error ? ((Error<E, T>) this) : mapper.apply(((Result<E, R>) this).result);
	}
	
	default <R> Either<E, T> map(Function<R, T> mapper){
		return flatMap(result -> new Result<E, T>(mapper.apply(result)));
	}
	 */
	
	public static <R> Result<R> Result(T result){
		return Result(Maybe.unit(result));
	}
	
	public static <R> Result<R> Result(Maybe<R> result){
		return new Result<>(result);
	}
	
	public static <R> Error<R> Error(String error){
		return new Error<>(error);
	}
	
	/*default CommandMonad<R> whenN(String error){
		return this instanceof Error ? this : ((Result<R>) this).result instanceof Just ? this : Error(error);
	}
	
	@SuppressWarnings("unchecked")
	default <T> CommandMonad<T> flatMap(Function<R, CommandMonad<T>> mapper){
		if(this instanceof Error) return (Error<T>) this;
		Maybe<R> maybe = ((Result<R>) this).result;
		return maybe instanceof Nothing ? (Error<T>) this : mapper.apply(((Just<R>) maybe).value);
	}
	
	@SuppressWarnings("unchecked")
	default <T> CommandMonad<T> map(Function<R, T> mapper){
		return flatMap(mapper.andThen(result -> new Result<T>(result)));
	}*/
	
	<T> CommandMonad<T> flatMap(Function<R, CommandMonad<T>> mapper);
	
	//<T> CommandMonad<T> map(Function<R, T> mapper);
	
	class Error<R> implements CommandMonad<R> {
		
		public final String error;
		
		public Error(String error){
			this.error = error;
		}
		
	}
	
	class Result<R> implements CommandMonad<R> {
		
		public final Maybe<R> result;
		
		public Result(Maybe<R> result){
			this.result = result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> CommandMonad<T> flatMap(Function<R, CommandMonad<T>> mapper) {
			return result instanceof Nothing ? (Result<T>) this : mapper.apply(((Just<R>) result).value);
		}
		
	}
	
}
