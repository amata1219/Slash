package amata1219.slash;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Either<F, S> {
	
	public static <F, S> Either<F, S> success(S value){
		return new Success<>(value);
	}
	
	public static <F, S> Either<F, S> failure(F error){
		return new Failure<>(error);
	}
	
	<T> Either<F, T> flatMap(Function<S, Either<F, T>> mapper);

	default <T> Either<F, T> map(Function<S, T> mapper){
		return flatMap(value -> success(mapper.apply(value)));
	}
	
	Either<F, S> onSuccess(Consumer<S> action);
	
	Either<F, S> onFailure(Consumer<F> action);
	
	public class Success<F, S> implements Either<F, S> {
		
		public final S value;
		
		private Success(S value){
			this.value = Objects.requireNonNull(value);
		}

		@Override
		public <T> Either<F, T> flatMap(Function<S, Either<F, T>> mapper) {
			return mapper.apply(value);
		}

		@Override
		public Either<F, S> onSuccess(Consumer<S> action) {
			action.accept(value);
			return this;
		}

		@Override
		public Either<F, S> onFailure(Consumer<F> action) {
			return this;
		}
		
	}
	
	public class Failure<F, S> implements Either<F, S> {
		
		public final F error;
		
		private Failure(F error){
			this.error = Objects.requireNonNull(error);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> Either<F, T> flatMap(Function<S, Either<F, T>> mapper) {
			return (Either<F, T>) this;
		}

		@Override
		public Either<F, S> onSuccess(Consumer<S> action) {
			return this;
		}

		@Override
		public Either<F, S> onFailure(Consumer<F> action) {
			action.accept(error);
			return this;
		}
		
	}
	
}
