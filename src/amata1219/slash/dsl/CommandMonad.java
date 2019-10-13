package amata1219.slash.dsl;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import amata1219.slash.dsl.component.LabeledStatement;

public interface CommandMonad<R> {
	
	public static <R> Result<R> Result(R result){
		return new Result<>(result);
	}
	
	public static <R> Error<R> Error(String error){
		return new Error<>(error);
	}
	
	public static <R> Error<R> Error(Message error){
		return Error(error.get());
	}
	
	@SuppressWarnings("unchecked")
	default <T> CommandMonad<T> flatBind(Function<R, CommandMonad<T>> mapper){
		return this instanceof Error ? (CommandMonad<T>) this : mapper.apply(((Result<R>) this).result);
	}
	
	default <T> CommandMonad<T> bind(Function<R, T> mapper){
		return flatBind(res -> Result(mapper.apply(((Result<R>) this).result)));
	}
	
	default CommandMonad<R> whenR(Consumer<R> action){
		if(this instanceof Result) action.accept(((Result<R>) this).result);
		return this;
	}
	
	default CommandMonad<R> whenE(Consumer<String> action){
		if(this instanceof Error) action.accept(((Error<R>) this).error);
		return this;
	}
	
	default CommandMonad<R> when(Predicate<R> predicate, Function<R, Supplier<String>> error){
		if(this instanceof Error) return this;
		R result = ((Result<R>) this).result;
		return this instanceof Error ? this : predicate.test(result) ? Error(error.apply(result).get()) : this;
	}
	
	default CommandMonad<R> when(Predicate<R> predicate, Supplier<String> error){
		return when(predicate, r -> error);
	}
	
	default CommandMonad<R> otherwise(Predicate<R> predicate, Supplier<String> error){
		return when(predicate.negate(), error);
	}
	
	default CommandMonad<R> otherwise(Predicate<R> predicate, Function<R, Supplier<String>> error){
		return when(predicate.negate(), error);
	}
	
	@SuppressWarnings("unchecked")
	default CommandMonad<?> match(LabeledStatement<R, ?>... statements){
		if(this instanceof Error) return this;
		for(LabeledStatement<R, ?> statement : statements) if(statement.matcher.match(((Result<R>) this).result)) return statement.evaluate();
		return this;
	}
	
	class Error<R> implements CommandMonad<R> {
		
		public final String error;
		
		private Error(String error){
			this.error = error;
		}
		
	}
	
	class Result<R> implements CommandMonad<R> {
		
		public final R result;
		
		private Result(R result){
			this.result = result;
		}
		
	}
	
}
