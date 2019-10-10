package amata1219.slash;

import java.util.function.Supplier;

public interface ErrorMessage extends Supplier<String> {
	
	default Supplier<String> format(Object... objects){
		return () -> Text.of(get()).format(objects).toString();
	}

}
