package amata1219.old.slash.dsl.component;

import java.util.function.Supplier;

import amata1219.old.slash.monad.Either;

public class LabeledStatement<T, F, S> {
	
	public final Matcher<T> matcher;
	private final Supplier<Either<F, S>> expression;
	
	public LabeledStatement(Matcher<T> matcher, Supplier<Either<F, S>> expression){
		this.matcher = matcher;
		this.expression = expression;
	}
	
	public Either<F, S> evaluate(){
		return expression.get();
	}

}
