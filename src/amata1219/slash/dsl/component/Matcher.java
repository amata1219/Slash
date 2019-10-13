package amata1219.slash.dsl.component;

import java.util.function.Predicate;
import java.util.function.Supplier;

import amata1219.slash.dsl.CommandMonad;

public abstract class Matcher<T> {
	
	private final static Default<?> DEFAULT = new Default<>();
	
	@SafeVarargs
	public static <T> Matcher<T> Case(T... literals){
		return new Literal<>(literals);
	}
	
	public static <T, R> LabeledStatement<T, R> Case(Predicate<T> predicate, Supplier<CommandMonad<R>> expression){
		return new LabeledStatement<>(new Condition<>(predicate), expression);
	}
	
	@SuppressWarnings("unchecked")
	public static <T, R> LabeledStatement<T, R> Else(Supplier<CommandMonad<R>> expression){
		return (LabeledStatement<T, R>) DEFAULT.label(expression);
	}
	
	
	public <R> LabeledStatement<T, R> label(Supplier<CommandMonad<R>> expression){
		return new LabeledStatement<>(this, expression);
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

		private Default(){
			
		}
		
		@Override
		public boolean match(T value) {
			return true;
		}
		
	}

}
