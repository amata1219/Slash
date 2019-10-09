package amata1219.slash;

import java.util.function.Supplier;

public class LabeledStatement<T, R> {
	
	public final Matcher<T> matcher;
	private final Supplier<Command<R>> expression;
	
	public LabeledStatement(Matcher<T> matcher, Supplier<Command<R>> expression){
		this.matcher = matcher;
		this.expression = expression;
	}
	
	public Command<R> evaluate(){
		return expression.get();
	}

}
