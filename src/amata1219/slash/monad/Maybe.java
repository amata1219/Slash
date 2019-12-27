package amata1219.slash.monad;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Maybe<T> {
	
	static <T> Maybe<T> unit(T value){
		return value != null ? some(value) : none();
	}
	
	static <T> Maybe<T> some(T value){
		return new Some<>(value);
	}
	
	@SuppressWarnings("unchecked")
	static <T> Maybe<T> none(){
		return (Maybe<T>) None.none;
	}
	
	<U> Maybe<U> flatMap(Function<T, Maybe<U>> mapper);
	
	default <U> Maybe<U> map(Function<T, U> mapper){
		return flatMap(x -> some(mapper.apply(x)));
	}
	
	Maybe<T> onSome(Consumer<T> action);
	
	Maybe<T> onNone(Runnable action);
	
	public class Some<T> implements Maybe<T> {
		
		public final T value;
		
		private Some(T value){
			this.value = Objects.requireNonNull(value);
		}

		@Override
		public <U> Maybe<U> flatMap(Function<T, Maybe<U>> mapper) {
			return mapper.apply(value);
		}

		@Override
		public Maybe<T> onSome(Consumer<T> action) {
			action.accept(value);
			return this;
		}

		@Override
		public Maybe<T> onNone(Runnable action) {
			return this;
		}
		
	}
	
	public class None<T> implements Maybe<T> {
		
		private static None<?> none = new None<>();
		
		private None(){
			
		}

		@SuppressWarnings("unchecked")
		@Override
		public <U> Maybe<U> flatMap(Function<T, Maybe<U>> mapper) {
			return (Maybe<U>) this;
		}

		@Override
		public Maybe<T> onSome(Consumer<T> action) {
			return this;
		}

		@Override
		public Maybe<T> onNone(Runnable action) {
			action.run();
			return this;
		}
		
	}

}
