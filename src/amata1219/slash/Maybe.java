package amata1219.slash;

import java.util.Objects;

public interface Maybe<R> extends Command<R> {

	public static <R> Maybe<R> unit(R value){
		return value != null ? new Just<>(value) : Nothing.instance();
	}

	public static class Just<R> implements Maybe<R> {

		public final R value;

		private Just(R value){
			this.value = Objects.requireNonNull(value);
		}

	}

	public static class Nothing<R> implements Maybe<R> {

		private static final Nothing<?> INSTANCE = new Nothing<>();

		@SuppressWarnings("unchecked")
		private static <R> Nothing<R> instance(){
			return (Nothing<R>) INSTANCE;
		}
		
		private Nothing(){
			
		}

	}

}
