package amata1219.slash;

import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Matcher<T> {
	
	private Supplier<Command<T>> expression;
	
	@SafeVarargs
	public static <T> Matcher<T> Case(T... literals){
		return new Literal<>(literals);
	}
	
	public static <T> Matcher<T> Case(Predicate<T> predicate){
		return new Condition<>(predicate);
	}
	
	public static <T> Matcher<T> Else(Supplier<Command<T>> expression){
		return new Default<>(expression);
	}

	public Matcher<T> expr(Supplier<Command<T>> expression){
		this.expression = expression;
		return this;
	}
	
	public Command<T> evalExpr(){
		return expression.get();
	}
	
	public abstract boolean match(T value);
	
	private static class Literal<T> extends Matcher<T> {
		
		private final T[] literals;
		
		@SafeVarargs
		private Literal(T... literals){
			this.literals = literals;
		}
		
		@Override
		public boolean match(T value){
			for(T literal : literals) if(literal == value) return true;
			return false;
		}
		
	}
	
	private static class Condition<T> extends Matcher<T> {
		
		private final Predicate<T> predicate;
		
		private Condition(Predicate<T> predicate){
			this.predicate = predicate;
		}
		
		@Override
		public boolean match(T value){
			return predicate.test(value);
		}
		
	}
	
	private static class Default<T> extends Matcher<T> {

		private Default(Supplier<Command<T>> expression){
			super.expression = expression;
		}
		
		@Override
		public boolean match(T value) {
			return true;
		}
		
	}

}
