package amata1219.slash;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.base.Supplier;

public interface Command<R> {
	
	public static <R> Result<R> Result(R result){
		return new Result<>(result);
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
	
	default Command<R> whenR(Consumer<R> action){
		if(this instanceof Result) action.accept(((Result<R>) this).result);
		return this;
	}
	
	default Command<R> whenE(Consumer<String> action){
		if(this instanceof Error) action.accept(((Error<R>) this).error);
		return this;
	}
	
	default Command<R> when(Predicate<R> predicate, Supplier<String> error){
		return this instanceof Error ? this : predicate.test(((Result<R>) this).result) ? Error(error.get()) : this;
	}
	
	default Command<R> otherwise(Predicate<R> predicate, Supplier<String> error){
		return when(predicate.negate(), error);
	}
	
	@SuppressWarnings("unchecked")
	default Command<?> match(LabeledStatement<R, ?>... statements){
		if(this instanceof Error) return this;
		for(LabeledStatement<R, ?> statement : statements) if(statement.matcher.match(((Result<R>) this).result)) return statement.evaluate();
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
