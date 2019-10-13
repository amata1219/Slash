package amata1219.slash.dsl;

import java.util.function.Supplier;

import amata1219.slash.Text;

public interface Message extends Supplier<String> {
	
	default String get(){
		return message();
	}
	
	default Supplier<String> format(Object... objects){
		return () -> Text.of(get()).format(objects).color().toString();
	}
	
	String message();
	
}
