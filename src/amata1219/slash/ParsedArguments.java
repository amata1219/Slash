package amata1219.slash;

import java.util.List;

public class ParsedArguments {
	
	public final List<Object> arguments;
	
	public ParsedArguments(List<Object> arguments){
		this.arguments = arguments;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(int index){
		return (T) arguments.get(index);
	}

}
